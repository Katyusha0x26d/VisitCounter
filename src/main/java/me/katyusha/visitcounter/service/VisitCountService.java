package me.katyusha.visitcounter.service;

import jakarta.annotation.Nullable;
import me.katyusha.visitcounter.entity.VisitCount;
import me.katyusha.visitcounter.mapper.VisitCountMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class VisitCountService {

    private final VisitCountMapper visitCountMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisScript<Long> atomicIncrExpireScript;
    private final RedissonClient redissonClient;

    private static final String REDIS_COUNT_PREFIX = "count:page:";
    private static final String REDIS_LOCK_PREFIX = "lock:page:";
    private static final long REDIS_COUNT_TTL = 60 * 60;
    private static final long SYNC_INTERVAL = 5 * 60;
    private static final long LOCK_WAIT_TIME = 3;
    private static final long LOCK_LEASE_TIME = 5;

    public VisitCountService(VisitCountMapper visitCountMapper, RedisTemplate<String, Object> redisTemplate, RedisScript<Long> atomicIncrExpireScript, RedissonClient redissonClient) {
        this.visitCountMapper = visitCountMapper;
        this.redisTemplate = redisTemplate;
        this.atomicIncrExpireScript = atomicIncrExpireScript;
        this.redissonClient = redissonClient;
    }

    private Long atomicIncrExpire(String pageKey, long expiration) {
        List<String> keys = Collections.singletonList(pageKey);
        Object[] args = {expiration};
        Long result = redisTemplate.execute(atomicIncrExpireScript, keys, args);
        return result == -1 ? null : result;
    }

    private Long handleFirstVisit(String pageKey) {
        String redisLockKey = REDIS_LOCK_PREFIX + pageKey;
        String redisCountKey = REDIS_COUNT_PREFIX + pageKey;
        RLock lock = redissonClient.getLock(redisLockKey);

        try {
            if (lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
                try {
                    Object cachedCount = redisTemplate.opsForValue().get(redisCountKey);
                    if (cachedCount != null) {
                        return (Long) cachedCount;
                    }

                    VisitCount visitCount = visitCountMapper.findByPageKey(pageKey);
                    Long count = Optional.ofNullable(visitCount).map(VisitCount::getCount).orElse(0L);
                    Long finalCount = count + 1;
                    redisTemplate.opsForValue().set(redisCountKey, finalCount, REDIS_COUNT_TTL, TimeUnit.SECONDS);
                    return finalCount;
                } finally {
                    lock.unlock();
                }
            } else {
                Thread.sleep(50);
                return handleFirstVisit(pageKey);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return 1L;
    }

    public Long incrementVisit(String pageKey) {
        String redisCountKey = REDIS_COUNT_PREFIX + pageKey;
        Long count = atomicIncrExpire(redisCountKey, REDIS_COUNT_TTL);
        if (count == null) {
            return handleFirstVisit(pageKey);
        }
        return count;
    }

    @Scheduled(fixedDelay = SYNC_INTERVAL * 1000)
    @Transactional
    public void syncToDatabase() {
        String redisAllKey = REDIS_COUNT_PREFIX + "*";
        Set<String> redisKeys = redisTemplate.keys(redisAllKey);
        List<VisitCount> updateList = new ArrayList<>();
        for (String redisKey : redisKeys) {
            Object redisValue = redisTemplate.opsForValue().get(redisKey);
            if (redisValue != null) {
                VisitCount visitCount = new VisitCount();
                visitCount.setPageKey(redisKey.substring(REDIS_COUNT_PREFIX.length()));
                visitCount.setCount((Long) redisValue);
                updateList.add(visitCount);
            }
        }
        visitCountMapper.batchUpdate(updateList);
    }
}

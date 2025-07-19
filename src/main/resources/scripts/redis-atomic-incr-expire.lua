local key = KEYS[1]
local expire_seconds = tonumber(ARGV[1])

if redis.call('EXISTS', key) == 0 then
    return -1
else
    local new_value = redis.call('INCR', key)
    redis.call('EXPIRE', key, expire_seconds)
    return new_value
end

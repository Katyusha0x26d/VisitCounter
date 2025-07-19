package me.katyusha.visitcounter.mapper;

import me.katyusha.visitcounter.entity.VisitCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VisitCountMapper {
    VisitCount findByPageKey(@Param("pageKey") String pageKey);
    int insert(@Param("visitCount") VisitCount visitCount);
    int insertZero(@Param("pageKey") String pageKey);
    int batchUpdate(List<VisitCount> updateList);
}
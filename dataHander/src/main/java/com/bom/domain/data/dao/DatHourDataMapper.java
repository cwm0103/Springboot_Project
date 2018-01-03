package com.bom.domain.data.dao;

import com.bom.domain.Mapper;
import com.bom.domain.data.model.DatHourData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface DatHourDataMapper extends com.bom.domain.Mapper<DatHourData> {
   public List<DatHourData> findHourData(@Param("projectId") String projectId, @Param("code") String code,@Param("timeStr") String timeStr);
}
package com.bom.domain.data.dao;

import com.bom.domain.Mapper;
import com.bom.domain.data.model.OptDataCol;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface OptDataColMapper extends com.bom.domain.Mapper<OptDataCol> {
     void  insertDataColBatch(@Param("dataColList") List<OptDataCol> dataColList);

     List<OptDataCol> selectDataColTop300();

     double GetEnergyDataSummary(@Param("code") String code,@Param("project_id") String project_id);

    double GetDayDiffValueFromDataCol(@Param("siteId")String siteId,@Param("code")String code,@Param("stime")String stime,@Param("etime")String etime);

     List<OptDataCol> findByMi(String timeStr) ;

     OptDataCol findByCodeAndMi(@Param("projectId") String projectId,@Param("code") String code,@Param("timeStr") String timeStr) ;

    List<OptDataCol> getModelByProjectIdAndTime(@Param("projectId")String projectId,@Param("sysTime")String sysTime);
}
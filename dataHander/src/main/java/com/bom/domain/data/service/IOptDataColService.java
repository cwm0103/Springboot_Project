package com.bom.domain.data.service;

import com.bom.domain.IService;
import com.bom.domain.data.model.OptDataCol;

import java.text.ParseException;
import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IOptDataColService extends com.bom.domain.IService<OptDataCol> {
    void findAll() ;

    void  insertDataColBatch(List<OptDataCol> dataColList);

    List<OptDataCol> selectDataColTop300();

    double GetEnergyDataSummary(String code,String project_id);

    double GetDayDiffValueFromDataCol(String siteId, String code,String stime ,String etime);


    List<OptDataCol> getModelByProjectIdAndTime(String projectId,String sysTime);

}
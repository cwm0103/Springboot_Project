package com.bom.dataservice.service;

import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.LineData;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-14
 * Time: 15:15
 */
public abstract class ABSDataService {
    public abstract List<DataModel> getData(String projectId, String codes,String date);
    public abstract List<LineData> getLineData(String projectId, String codes, String beginDate, String endDate,String downsample);
}

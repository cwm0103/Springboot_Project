package com.bom.dataservice.service;

import com.bom.dataservice.model.RealDataModel;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-14
 * Time: 14:17
 */
public interface IBigDataService {
    //获取全国数据
    Map<String,String> getNationwideData(String code);
    RealDataModel getRealData(String projectId, String codes);
}

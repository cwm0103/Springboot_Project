package com.bom.dataservice.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-14
 * Time: 15:11
 */
public class RealDataModel {
    public RealDataModel() {
    }

    List<BigDataModel> realDatas;

    public List<BigDataModel> getRealDatas() {
        return realDatas;
    }

    public void setRealDatas(List<BigDataModel> realDatas) {
        this.realDatas = realDatas;
    }
}

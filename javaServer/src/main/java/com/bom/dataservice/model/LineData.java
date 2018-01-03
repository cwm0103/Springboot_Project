package com.bom.dataservice.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-11
 * Time: 14:00
 */
public class LineData implements Serializable {

    public LineData() {
    }
    String code;
    List<Map<String,Object>> datas;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public  List<Map<String,Object>> getDatas() {
        return datas;
    }

    public void setDatas( List<Map<String,Object>> datas) {
        this.datas = datas;
    }
}

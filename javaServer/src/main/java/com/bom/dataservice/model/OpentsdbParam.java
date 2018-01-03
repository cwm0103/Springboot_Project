package com.bom.dataservice.model;

import com.alibaba.fastjson.JSONArray;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-22
 * Time: 14:02
 */
public class OpentsdbParam {
    private String start;
    private String end;
    private JSONArray queries;

    public OpentsdbParam() {
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public JSONArray getQueries() {
        return queries;
    }

    public void setQueries(JSONArray queries) {
        this.queries = queries;
    }
}


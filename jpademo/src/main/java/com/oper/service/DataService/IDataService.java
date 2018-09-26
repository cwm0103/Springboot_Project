package com.oper.service.DataService;

import com.alibaba.fastjson.JSONArray;
import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubDashboardData;
import com.oper.entity.vo.DataParmerVO;

import java.util.List;
import java.util.Map;

/**
 * @Auther: jindb
 * @Date: 2018/8/17 10:30
 * @Description:
 */
public interface IDataService {
    Map<String,Object> getData(PubDashboardConfig config, List<PubDashboardData> pubDashboardDataList, JSONArray jsonObject, DataParmerVO prame) throws Exception;
}

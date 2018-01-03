package com.bom.dataservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.InstantFuncDao;
import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.InstantFunc;
import com.bom.dataservice.model.LineData;
import com.bom.dataservice.model.TSDBResult;
import com.bom.dataservice.utils.DifferentDate;
import com.bom.dataservice.utils.FileUtil;
import com.bom.dataservice.utils.OpentsdbUtil;
import com.bom.dataservice.utils.RunString;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-14
 * Time: 15:29
 */
@Component("SS")
public class SSDataService extends ABSDataService {
    @Value("${opentsdb.query}")
    private String url;

    @Autowired
    private IBigDataService bigDataService;
    @Autowired
    private IRedisService redisService;
    @Autowired
    private InstantFuncDao instantFuncDao;


    @Override
    @TargetDataSource("ds1")
    public List<DataModel> getData(String projectId, String codes, String date) {
        List<DataModel> dataList = new LinkedList<>();
        String[] arrCodes = codes.split(",");
        List<InstantFunc> instantFuncList = instantFuncDao.getFuncList(projectId, codes);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> expCode = instantFuncList.stream().map(y -> y.getCal_code()).distinct().collect(Collectors.toList());
        List<String> noexpCode = Arrays.asList(arrCodes).stream().filter(x -> !expCode.contains(x)).collect(Collectors.toList());
        Date beginDate = null;
        Date endDate = null;
        try {
            if (StringUtils.isNotBlank(date)) {
                endDate = sdf.parse(date);
            } else {
                endDate = new Date();
            }
            beginDate = DifferentDate.addonetime(endDate, -1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (noexpCode != null && noexpCode.size() > 0) {
            String queryResult = OpentsdbUtil.OpenTSDBQuery(beginDate, endDate, String.join(",", noexpCode), "1h-last", projectId, "last", url);
            if(!queryResult.contains("error")) {
                List<TSDBResult> tsdbResults = OpentsdbUtil.paseTSDBResult(queryResult);
                for (TSDBResult result : tsdbResults) {
                    DataModel dataModel = new DataModel();
                    dataModel.setValue(result.getValue().doubleValue());
                    dataModel.setCode(result.getCode());
                    dataModel.setCol_time(result.getDate());
                    dataList.add(dataModel);
                }
            }
        }
        for (InstantFunc func : instantFuncList) {
            try {
                String queryResult="";
                String[] params=func.getList_var().split(",");
                if(("0").equals(projectId))
                {
                    queryResult = OpentsdbUtil.OpenTSDBQuery(beginDate, endDate, func.getList_var(), "1h-last", "last", url);
                }else{
                    queryResult = OpentsdbUtil.OpenTSDBQuery(beginDate, endDate, func.getList_var(), "1h-last", projectId, "last", url);
                }
                List<TSDBResult> tsdbResults = OpentsdbUtil.paseTSDBResult(queryResult);
                Map<String, String> parme = tsdbResults.stream().collect(Collectors.toMap(TSDBResult::getCode, r -> r.getValue().toString()));
                //判断参数对应值是否获取完整，否则对应参数值设为0
                if(params.length>parme.size()){
                    for (String para :params) {
                        if(!parme.containsKey(params)){
                            parme.put(para,"0");
                        }
                    }
                }
                Double calResult = RunString.calString(func.getFunction(), parme);
                DataModel dataModel = new DataModel();
                dataModel.setValue(calResult);
                dataModel.setCode(func.getCal_code());
                dataModel.setCol_time(sdf.format(endDate));
                dataList.add(dataModel);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    public List<DataModel> getData1(String projectId, String codes, String date) {
        List<DataModel> dataList = new LinkedList<>();
        Map<String, String> allCodesMap = new LinkedHashMap<>();
        String path = SSDataService.class.getResource("/code.json").getPath();
        String strJson = FileUtil.File2String(path).replace("\r", "").replace("\n", "").replace("\t", "");
        JSONObject configJson = JSON.parseObject(strJson);
        if (projectId.equals("0")) {
            for (String akey : configJson.keySet()) {
                JSONObject object1 = configJson.getJSONObject(akey);
                for (String skey : object1.keySet()) {
                    JSONObject object2 = object1.getJSONObject(skey);
                    for (String key : object2.keySet()) {
                        String keycode = "";
                        if (allCodesMap.containsKey(key)) {
                            if (StringUtils.isNotBlank(allCodesMap.get(key))) {
                                keycode = allCodesMap.get(key) + "," + object2.get(key);
                                allCodesMap.put(key, keycode);
                            }
                        } else {
                            allCodesMap.put(key, object2.getString(key));
                        }
                    }
                }
            }
            return calculateSum(allCodesMap);
        } else if (projectId.length() == 3) {
            JSONObject areaJson = configJson.getJSONObject(projectId);
            for (String skey : areaJson.keySet()) {
                JSONObject object2 = areaJson.getJSONObject(skey);
                for (String key : object2.keySet()) {
                    String keycode = "";
                    if (allCodesMap.containsKey(key)) {
                        keycode = allCodesMap.get(key) + "," + object2.get(key);
                        allCodesMap.put(key, keycode);
                    } else {
                        allCodesMap.put(key, object2.getString(key));
                    }
                }
            }
            return calculateSum(allCodesMap);
        } else {
            String[] arrCode = codes.split(",");
            for (String code : arrCode) {
                DataModel model = new DataModel();
                String key = getKey(projectId, code);
                String value = redisService.get(key);
                String time = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
                double curValue = 0;
                if (StringUtils.isNotBlank(value)) {
                    String[] values = value.split("\t");
                    time = values[1];
                    curValue = Double.parseDouble(values[0]);
                }
                model.setCode(code);
                model.setCol_time(time);
                model.setValue(curValue);
                dataList.add(model);
            }
        }
        return dataList;
    }

    private List<DataModel> calculateSum(Map<String, String> allCodesMap) {
        List<DataModel> dataList = new LinkedList<>();
        for (String key : allCodesMap.keySet()) {
            String keys = allCodesMap.get(key);
            double count = 0;
            String time = "";
            if (StringUtils.isNotBlank(keys)) {
                String[] keyList = keys.split(",");
                for (String value : keyList) {
                    String varValue = redisService.get(value);
                    if (StringUtils.isNotBlank(varValue)) {
                        Map<String, Object> mapPase = parseRedisValue(varValue);
                        time = mapPase.get("time").toString();
                        double curValue = (double) mapPase.get("value");
                        count += curValue;
                    }
                }
            }
            DataModel model = new DataModel();
            model.setCode(key);
            model.setCol_time(time);
            model.setValue(count);
            dataList.add(model);
        }
        return dataList;
    }

    private Map<String, Object> parseRedisValue(String value) {
        Map<String, Object> mapResult = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(value)) {
            String[] values = value.split("\t");
            mapResult.put("value", Double.parseDouble(values[0]));
            mapResult.put("time", values[1]);
        } else {
            mapResult.put("value", 0);
            mapResult.put("time", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        }
        return mapResult;
    }

    private String getKey(String projectId, String code) {
        String key = "pspace\\" + projectId + "\\" + code;
        return key;
    }

    @Override
    public List<LineData> getLineData(String projectId, String codes, String beginDate, String endDate, String downsample) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<LineData> lineDataList = new LinkedList<>();
        try {
            Date begin = sdf.parse(beginDate);
            Date end = sdf.parse(endDate);
            String queryResult = OpentsdbUtil.OpenTSDBQuery(begin, end, codes, null, projectId, "none", url);
            List<TSDBResult> tsdbResults = OpentsdbUtil.paseTSDBResult(queryResult);
            String[] arrCode = codes.split(",");
            for (String code : arrCode) {
                LineData lineData = new LineData();
                lineData.setCode(code);
                List<TSDBResult> codevalues = tsdbResults.stream().filter(x -> code.equals(x.getCode())).collect(Collectors.toList());
                List<Map<String, Object>> dataModels = new LinkedList<>();
                for (TSDBResult result : codevalues) {
                    Map<String, Object> mapData = new LinkedHashMap<>();
                    mapData.put("value", result.getValue());
                    mapData.put("col_time", result.getDate());
                    dataModels.add(mapData);
                }
                lineData.setDatas(dataModels);
                lineDataList.add(lineData);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lineDataList;
    }
}

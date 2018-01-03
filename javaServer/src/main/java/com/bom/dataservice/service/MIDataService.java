package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.InstantFuncDao;
import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.LineData;
import com.bom.dataservice.model.TSDBResult;
import com.bom.dataservice.utils.DifferentDate;
import com.bom.dataservice.utils.OpentsdbUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by enn on 2017/11/28.
 */
@Component("MI")
public class MIDataService extends ABSDataService {
    @Value("${opentsdb.query}")
    private String url;
    @Autowired
    private InstantFuncDao instantFuncDao;


    @Override
    @TargetDataSource("ds1")
    public List<DataModel> getData(String projectId, String codes, String date) {
        List<DataModel> dataList = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate=null;
        Date endDate=null;
        try{
            if (StringUtils.isNotBlank(date)) {
                endDate = sdf.parse(date);
            } else {
                endDate = new Date();
            }
            beginDate = DifferentDate.addonemin(endDate, -1);//一分钟之前。
            String queryResult = OpentsdbUtil.OpenTSDBQuery(beginDate, endDate, codes, "2m-last-zero", projectId, "none", url);
            List<TSDBResult> tsdbResults = OpentsdbUtil.paseTSDBResult(queryResult);
            for (TSDBResult result: tsdbResults ) {
                DataModel dataModel = new DataModel();
                dataModel.setValue(result.getValue().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                dataModel.setCode(result.getCode());
                dataModel.setCol_time(result.getDate());
                dataList.add(dataModel);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    @TargetDataSource("ds1")
    public List<LineData> getLineData(String projectId, String codes, String beginDate, String endDate, String downsample) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<LineData> lineDataList = new LinkedList<>();
        try {
            Date begin = sdf.parse(beginDate);
            Date end = sdf.parse(endDate);
            if(StringUtils.isBlank(downsample)){
                downsample="5m-last-null" ;
            }else{
                downsample=downsample+"-last-null" ;
            }
            String queryResult = OpentsdbUtil.OpenTSDBQuery(begin, end, codes, downsample, projectId, "last", url);
            List<TSDBResult> tsdbResults = OpentsdbUtil.paseTSDBResult(queryResult);
            String[] arrCode = codes.split(",");
            for (String code : arrCode) {
                LineData lineData = new LineData();
                lineData.setCode(code);
                List<TSDBResult> codevalues = tsdbResults.stream().filter(x -> code.equals(x.getCode())).collect(Collectors.toList());
                List<Map<String, Object>> dataModels = new LinkedList<>();
                for (TSDBResult result : codevalues) {
                    Map<String, Object> mapData = new LinkedHashMap<>();
                    mapData.put("value", result.getValue()==null?"":result.getValue().setScale(3,BigDecimal.ROUND_HALF_UP));
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

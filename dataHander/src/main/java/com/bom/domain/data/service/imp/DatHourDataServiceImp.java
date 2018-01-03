package com.bom.domain.data.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.dao.OptStationCodesMapper;
import com.bom.domain.data.dao.StationCodesMapper;
import com.bom.domain.data.model.*;
import com.bom.domain.data.service.IDatHourDataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.bom.utils.HTTPUtil;
import com.bom.utils.OpentsdbUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class DatHourDataServiceImp extends BaseService<DatHourData> implements IDatHourDataService {
    private static final Logger logger = LoggerFactory.getLogger(DatHourDataServiceImp.class);
    @Autowired
    private com.bom.domain.data.dao.DatHourDataMapper DatHourDataMapper;

    @Autowired
    private com.bom.domain.data.dao.OptDataColMapper OptDataColMapper;

    @Autowired
    private OptStationCodesMapper _optStationCodesMapper;
    @Value("${staIds}")
    private String staIds;

    @Value("${opentsdb.url}")
    private String url;

    @TargetDataSource(name = "datadb")
    public void createHourData() {
        String strbeginDate = DateTime.now().toString("yyyy-MM-dd HH:00:00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginDate = sdf.parse(strbeginDate);
            Date endDate = new Date();
            List<OptStationCodes> codes = _optStationCodesMapper.selectAll();
            Map<String, List<String>> stationCodeMap = new LinkedHashMap<>();
            String[] arrStaId=staIds.split(",");
            for (String staId: arrStaId) {
              List<String> codeList= codes.stream().filter(x->staId.equals(x.getProjectId())).map(y->y.getCode()).collect(Collectors.toList());
              stationCodeMap.put(staId,codeList);
            }
           /* for (OptStationCodes code : codes) {
                List<String> stationCodes = new LinkedList<>();
                if (!stationCodeMap.containsKey(code.getProjectId())) {
                    stationCodes.add(code.getCode());
                    stationCodeMap.put(code.getProjectId(), stationCodes);
                } else {
                    stationCodes = stationCodeMap.get(code.getProjectId());
                    stationCodes.add(code.getCode());
                    stationCodeMap.replace(code.getProjectId(), stationCodes);
                }
            }*/
            for (String key : stationCodeMap.keySet()) {
                String downsample = "1h-first-zero";
                String codelist = String.join(",", stationCodeMap.get(key));
                String content = OpentsdbUtil.OpenTSDBQuery(beginDate, endDate, codelist, downsample, key, url);
                List<TSDBResult> beginResultList = OpentsdbUtil.paseTSDBResult(content);
                downsample = "1h-last-zero";
                content = OpentsdbUtil.OpenTSDBQuery(beginDate, endDate, codelist, downsample, key, url);
                List<TSDBResult> endResultList = OpentsdbUtil.paseTSDBResult(content);
                for (TSDBResult beginResult : beginResultList) {
                    TSDBResult endReslut = endResultList.stream().filter(x -> beginResult.getCode().equals(x.getCode()) && beginResult.getDate().equals(x.getDate())).findFirst().get();
                    List<DatHourData> datHourDatas = DatHourDataMapper.findHourData(key, beginResult.getCode(), strbeginDate);
                    double curvalue = endReslut.getValue().subtract(beginResult.getValue()).doubleValue();
                    if( curvalue>=0 ) {
                        //if((beginResult.getCode().contains("EETA") && curvalue<88)||(beginResult.getCode().contains("GSBx_Nt") && curvalue<88.9)||(beginResult.getCode().contains("GTx_Nt") && curvalue<0.4)) {
                            if (datHourDatas.size() > 0) {
                                if(filterValue(beginResult.getCode(),curvalue)) {
                                    DatHourData hourData = datHourDatas.get(0);
                                    hourData.setCurValue(curvalue);
                                    hourData.setSysTime(DateTime.now().toDate());
                                    DatHourDataMapper.updateByPrimaryKey(hourData);
                                }
                            } else {
                                DatHourData hourData = new DatHourData();
                                hourData = new DatHourData();
                                hourData.setCurValue(curvalue);
                                hourData.setCode(beginResult.getCode());
                                hourData.setColTime(beginDate);
                                hourData.setSysTime(DateTime.now().toDate());
                                hourData.setProjectId(key);
                                DatHourDataMapper.insert(hourData);
                           // }
                        }
                    }else{
                        if (datHourDatas.size() > 0) {}else{
                            DatHourData hourData = new DatHourData();
                            hourData = new DatHourData();
                            hourData.setCurValue(curvalue);
                            hourData.setCode(beginResult.getCode());
                            hourData.setColTime(beginDate);
                            hourData.setSysTime(DateTime.now().toDate());
                            hourData.setProjectId(key);
                            DatHourDataMapper.insert(hourData);
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
    @TargetDataSource(name="datadb")
    public boolean filterValue(String code ,double curvalue){
        if(code.contains("EETA") || code.contains("EETA")||code.contains("EETA")){
            if((code.contains("EETA") && curvalue<88)||(code.contains("GSBx_Nt") && curvalue<88.9)||(code.contains("GTx_Nt") && curvalue<0.4)){
                return true;
            }else
            {
                return false;
            }
        }else{
            return true;
        }
    }

    @TargetDataSource(name = "datadb")
    public void createHourData1() {
        logger.info("开始计算小时数据..." + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        try {
            DateTime curDate = DateTime.now().minusMinutes(3);
            String strbeginDate = DateTime.now().toString("yyyy-MM-dd HH:00:00");
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime beginDate = DateTime.parse(strbeginDate, format);
            logger.info("時間" + strbeginDate);
            if (curDate.isAfter(beginDate)) {
                List<OptDataCol> endDataList = OptDataColMapper.findByMi(curDate.toString("yyyy-MM-dd HH:mm:00"));
                List<OptDataCol> beginDataList = OptDataColMapper.findByMi(beginDate.toString("yyyy-MM-dd HH:mm:00"));
                for (OptDataCol beginData : beginDataList) {
                    Optional<OptDataCol> optDataCol = endDataList.stream().filter(d -> d.getProjectId().equals(beginData.getProjectId()) && d.getCode().equals(beginData.getCode())).findFirst();
                    if (optDataCol.isPresent()) {
                        OptDataCol endData = optDataCol.get();
                        double curvalue = endData.getCurValue() - beginData.getCurValue();
                        List<DatHourData> datHourDatas = DatHourDataMapper.findHourData(beginData.getProjectId(), beginData.getCode(), strbeginDate);
                        if (datHourDatas.size() > 0) {
                            DatHourData hourData = datHourDatas.get(0);
                            if (hourData == null) {
                                hourData = new DatHourData();
                                hourData.setCurValue(curvalue);
                                hourData.setCode(beginData.getCode());
                                hourData.setColTime(beginDate.toDate());
                                hourData.setSysTime(DateTime.now().toDate());
                                hourData.setProjectId(beginData.getProjectId());
                                DatHourDataMapper.insert(hourData);
                            } else {
                                hourData.setCurValue(curvalue);
                                hourData.setSysTime(DateTime.now().toDate());
                                DatHourDataMapper.updateByPrimaryKey(hourData);
                            }
                        } else {
                            DatHourData hourData = new DatHourData();
                            hourData = new DatHourData();
                            hourData.setCurValue(curvalue);
                            hourData.setCode(beginData.getCode());
                            hourData.setColTime(beginDate.toDate());
                            hourData.setSysTime(DateTime.now().toDate());
                            hourData.setProjectId(beginData.getProjectId());
                            DatHourDataMapper.insert(hourData);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("结束计算小时数据..." + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    }


}
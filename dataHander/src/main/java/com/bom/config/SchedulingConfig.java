package com.bom.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.bom.domain.data.model.*;
import com.bom.domain.data.model.StationCodes;
import com.bom.domain.data.model.StationTop;
import com.bom.domain.data.service.*;
import com.bom.domain.data.service.IDatDayDataService;
import com.bom.domain.data.service.IDatMonthDataService;
import com.bom.domain.data.service.IOptDataColService;
import com.bom.domain.mix.service.IResultDayService;
import com.bom.domain.mix.service.IResultHourService;
import com.bom.domain.mix.service.IResultMonthService;
import com.bom.domain.mix.service.IResultYearService;
import com.bom.domain.spvdata.model.BasEquInfo;
import com.bom.domain.spvdata.model.PubStationInfo;
import com.bom.domain.spvdata.model.StationCodesHead;
import com.bom.domain.spvdata.service.IBasEquInfoService;
import com.bom.domain.spvdata.service.IDatHourDataSpvService;
import com.bom.domain.spvdata.service.IPubStationInfoService;
import com.bom.domain.spvdata.service.IStationCodesHeadService;
import com.bom.utils.DifferentDate;
import com.bom.utils.HttpClientHelper;
import com.bom.domain.data.service.IStationCodesService;
import com.bom.domain.data.service.IStationTopService;
import com.bom.utils.OpentsdbUtil;
import com.bom.utils.fileservice.ReadFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by enn on 2017/11/13.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    protected static Logger logger=  LoggerFactory.getLogger(SchedulingConfig.class);
    @Autowired
    private IOptDataColService _iOptDataColService;



    @Autowired
    private IBasEquInfoService iBasEquInfoService;

    //光伏数据接口url
    @Value("${PV.url}")
    private String pvurl;
//opentsdb查询接口地址
    @Value("${opentsdb.url}")
    private String url;

    @Autowired
    private IDatMonthDataService iDatMonthDataService;
    @Autowired
    private IStationTopService _iStationTopService;
    @Autowired
    private IStationCodesService _iStationCodesService;
    @Autowired
    private IDatDayDataService _iDatDayDataService;
    @Autowired
    private IDatHourDataService _iDatHourDataService;
    @Autowired
    private IDatYearDataService iDatYearDataService ;
    @Autowired
    private  IStationCodesTypeService _iStationCodesTypeService;
    @Autowired
    private IResultDayService iResultDayService ;
    @Autowired
    private IResultHourService iResultHourService;
    @Autowired
    private IResultMonthService iResultMonthService;
    @Autowired
    private IResultYearService iResultYearService ;

    @Autowired
    private IStationCodesHeadService iStationCodesHeadService;
    @Autowired
    private  IStationCodesService  iStationCodesService;

    @Autowired
    private IPubStationInfoService iPubStationInfoService;
    @Autowired
    private IDatHourDataSpvService iDatHourDataSpvService;




    @Scheduled(cron = "${corn.handlerMonth}")
    public void handlerMonth() {
        iDatMonthDataService.handlerMonth();
    }

    @Scheduled(cron="${corn.handlerMixHour}")
    public void handlerMixHour(){
        iResultHourService.calc();
    }
    @Scheduled(cron="${corn.handlerMixDay}")
    public void handlerMixDay(){
        iResultDayService.calc();
    }
    @Scheduled(cron="${corn.handlerMixMonth}")
    public void handlerMixMonth(){
        iResultMonthService.calc();
    }
    @Scheduled(cron="${corn.handlerMixYear}")
    public void handlerMixYear(){
        iResultYearService.calc();
    }


    //region 抓取服务

    /*
        /**
         * 从新智云服务器接口获取测点数据内容
         * <p>Title: getCloudDatas</p>
         * <p>Description: </p>
         * @param token
         * @param projectid
         * @param code
         * @return
         */
    public static String GetCloudDatas(String token, String projectid, String code) {
        String urlNameString = "http://dfuse.enn.cn/iot-integration/pspace/getRealDataByCodes";
        String result = "";
        try {
            // 建立post对象
            HttpPost post = new HttpPost(urlNameString);
            // 建立一个参数数组
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("token", token));
            params.add(new BasicNameValuePair("projectid", projectid));
            params.add(new BasicNameValuePair("code", code));
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            logger.info("抓取数据开始：url:"+urlNameString+"projectid:"+projectid+"code:"+code);
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(post);
            logger.info("抓取数据完成");
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // public boolean isFirstGetData = true;

    /**
     * 返回的json格式的数据转成测点数据的对象列表
     * <p>Title: parserDatas</p>
     * <p>Description: </p>
     *
     * @param result
     * @return
     * @throws Exception
     */
    public void ParserDatas(String result, String projectId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        List<OptDataCol> codeInfoList = new ArrayList<OptDataCol>();
        List<Map<String, String>> list = (List<Map<String, String>>) mapper.readValue(result, Map.class).get("realDatas");
       // List<OptDataCol> dataColListTop300 = _iOptDataColService.selectDataColTop300();
        for (Map<String, String> obj : list) {
           /* Optional<OptDataCol> existData = dataColListTop300.stream()
                    .filter(s -> s.getCode().equals(obj.get("name"))
                            && s.getColTime().equals(Timestamp.valueOf(dateFormat.format(Timestamp.valueOf(obj.get("time"))))))
                    .findFirst();*/

           /* if (existData == Optional.<OptDataCol>empty()) {
                if (!isFirstGetData) {
                    Calendar beforeTime = Calendar.getInstance();
                    beforeTime.add(Calendar.MINUTE, -3);// 3分钟之前的时间
                    Date beforeDate = beforeTime.getTime();

                    if (Timestamp.valueOf(obj.get("time")).before(beforeDate))
                        continue;
                }*/

            OptDataCol codeInfo = new OptDataCol();
            codeInfo.setProjectId(projectId);
            codeInfo.setCode(obj.get("name"));
            codeInfo.setCurValue(Double.parseDouble(obj.get("value")));
            //  codeInfo.setColTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(new Date())));
            codeInfo.setColTime(Timestamp.valueOf(dateFormat.format(Timestamp.valueOf(obj.get("time")))));
            codeInfo.setSysTime(Timestamp.valueOf(dateFormat.format(new Date())));
            // codeInfo.setSysTime(new Date());
            // if (isFirstGetData)
            //     codeInfo.setDataType("MI-");
            //  else
            codeInfo.setDataType("MI");
            codeInfoList.add(codeInfo);
        }
        // }
        //return codeInfos;
        // isFirstGetData = false;
        _iOptDataColService.insertDataColBatch(codeInfoList);
    }

    // 主函数入口
    //@Scheduled(cron = "${corn.RealDataByCodes}")
    public void RealDataByCodes() {
        logger.info("抓取数据开始...");
        // 读取测点名列表
        String filePath = System.getProperty("user.dir");
       // List<String> items = ReadFile.readAllCode(filePath + "\\abc.txt");
        // 处理code格式，可作为post参数
        //String string = items.toString();
        List<StationCodes> stationCodeList = _iStationCodesService.selectAll();
        String[] siteIdList = new String[]{"1016"};
        for (String siteId: siteIdList) {
            List<String> codeList = stationCodeList.stream().filter(x -> x.getProjectId().equals(siteId))
                    .map(x -> x.getCode()).distinct().collect(Collectors.toList());
            String substring=String.join(",",codeList);
            //final String substring = string.substring(1, string.length() - 1).replaceAll(" ", "");
            try {
                String projectId = siteId;
                String datas = GetCloudDatas("7a82f7e7c228c1a24b7d9f99", projectId, substring);
                List<OptDataCol> codeInfos = new ArrayList<OptDataCol>();
                this.ParserDatas(datas, projectId);
                System.out.println("等待下次插入...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("抓取数据结束...");
    }
    //endregion
    @Scheduled(cron="${corn.YearDataSummary}")
    public void createYearData(){
        iDatYearDataService.handlerYear();
    }
    //region 站能源数据汇总
     @Scheduled(cron = "${corn.SiteEnergyDataSummary}")
    public void SiteEnergyDataSummary() {
         List<StationTopCode> stationTopList = new ArrayList<>();
         List<Integer> stationIdList = new ArrayList<>();
         //光伏  站id: 2022, 2021, 2014, 2098, 2023,2024
         Map<Integer, String> mapStations = new HashedMap();
         mapStations.put(2022, "新胜供水分布式电站");
         mapStations.put(2021, "新绎七修酒店分布式电站");
         mapStations.put(2014, "艾力枫社N27别墅分布式电站");
         mapStations.put(2098, "七修居分布式电站");
         mapStations.put(2023, "康惠卓宇分布式光伏电站");
         mapStations.put(2024, "富士卓宇分布式光伏电站");
         //int [] guangFuIds={2022, 2021, 2014, 2098, 2023,2024};
         for (Map.Entry<Integer, String> mapStation : mapStations.entrySet()) {
             StationTopCode stationTopCode = new StationTopCode();
             stationTopCode.station_id = String.valueOf(mapStation.getKey());
             stationTopCode.station_name = mapStation.getValue();
             stationTopCode.soucetype = "guangfu";
             stationTopCode.energyType = 1;//类型:1:电，2：冷，3：热，4：蒸汽
             stationTopList.add(stationTopCode);
             stationIdList.add(mapStation.getKey());
         }
         // 数据库配置
         List<StationCodesType> stationCodesTypeList = _iStationCodesTypeService.selectAll();
         for (StationCodesType stationCodesType : stationCodesTypeList) {
             StationTopCode stationTopCode = new StationTopCode();
             stationTopCode.station_id = String.valueOf(stationCodesType.getStationId());
             stationTopCode.station_name = stationCodesType.getStationName();
             stationTopCode.soucetype = "mysqldb";
             stationTopCode.energyType = stationCodesType.getCodeType();//类型:1:电，2：冷，3：热，4：蒸汽
             stationTopCode.code = stationCodesType.getCode();
             stationTopList.add(stationTopCode);
             if (!stationIdList.contains(stationCodesType.getStationId()))
                 stationIdList.add(stationCodesType.getStationId());
         }

         List<StationTop> stationTopList1 = _iStationTopService.selectAll();

         List<Integer> typeIds = Arrays.asList(1, 2, 3, 4);
         stationIdList.forEach(s -> {

             typeIds.forEach(x -> {

                 Optional<StationTop> stationTop = stationTopList1.stream().filter(f -> f.getStationId().equals(s.toString()) && f.getType() == x.intValue()).findFirst();

                 if (stationTop.equals(Optional.empty())) {
                     String siteName = "";
                     Optional<StationTopCode> stationTopCode = stationTopList.stream().filter(j -> j.station_id.equals(s.toString())).findFirst();
                     if (!stationTopCode.equals(Optional.empty()))
                         siteName = stationTopCode.get().station_name;

                     StationTop stationTop1 = new StationTop();
                     stationTop1.setStationId(s.toString());
                     stationTop1.setStationName(siteName);
                     stationTop1.setTotalValue(0.00);
                     stationTop1.setType(x.intValue());
                     stationTop1.setCreateDate(new Date());
                     int result = _iStationTopService.save(stationTop1);
                 }

             });


         });


         for (StationTopCode stationItem : stationTopList) {
             String siteId = stationItem.station_id;
             String siteName = stationItem.station_name;

             double dianValue = 0;
             double lengValue = 0;
             double reValue = 0;
             double zhengqiValue = 0;

             if (stationItem.soucetype.equals("guangfu")) {
                 //dianValue = api_SpvGY(siteId,"cTotalYield");
                 dianValue = (Double) getStationPower(siteId, "35", "cTotalYield");
                 StationTop stationTop1 = _iStationTopService.getStationDataBySiteIdAndType(siteId, 1);
                 if (stationTop1 == null) {
                     StationTop stationTop = new StationTop();
                     stationTop.setStationId(siteId);
                     stationTop.setStationName(siteName);
                     stationTop.setTotalValue(dianValue);
                     stationTop.setType(1);
                     stationTop.setCreateDate(new Date());
                     int result = _iStationTopService.save(stationTop);
                 } else {
                     stationTop1.setTotalValue(dianValue);
                     stationTop1.setStationName(siteName);
                     stationTop1.setCreateDate(new Date());
                     _iStationTopService.updateAll(stationTop1);
                 }
             } else {
                 switch (stationItem.energyType) {
                     case 1:
                         dianValue = GetStationEnergyTotal(stationItem.code, stationItem.station_id);
                         StationTop stationTop1 = _iStationTopService.getStationDataBySiteIdAndType(siteId, 1);
                         if (stationTop1 == null) {
                             StationTop stationTop = new StationTop();
                             stationTop.setStationId(siteId);
                             stationTop.setStationName(siteName);
                             stationTop.setTotalValue(dianValue);
                             stationTop.setType(1);
                             stationTop.setCreateDate(new Date());
                             int result = _iStationTopService.save(stationTop);
                         } else {
                             stationTop1.setTotalValue(dianValue);
                             stationTop1.setStationName(siteName);
                             stationTop1.setCreateDate(new Date());
                             _iStationTopService.updateAll(stationTop1);
                         }

                         break;
                     case 2:
                         lengValue = GetStationEnergyTotal(stationItem.code, stationItem.station_id);
                         StationTop stationTop2 = _iStationTopService.getStationDataBySiteIdAndType(siteId, 2);
                         if (stationTop2 == null) {
                             StationTop stationTop = new StationTop();
                             stationTop.setStationId(siteId);
                             stationTop.setStationName(siteName);
                             stationTop.setTotalValue(lengValue);
                             stationTop.setType(2);
                             stationTop.setCreateDate(new Date());
                             int result = _iStationTopService.save(stationTop);
                         } else {
                             stationTop2.setTotalValue(lengValue);
                             stationTop2.setStationName(siteName);
                             stationTop2.setCreateDate(new Date());
                             _iStationTopService.updateAll(stationTop2);
                         }
                         break;
                     case 3:
                         reValue = GetStationEnergyTotal(stationItem.code, stationItem.station_id);
                         StationTop stationTop3 = _iStationTopService.getStationDataBySiteIdAndType(siteId, 3);
                         if (stationTop3 == null) {
                             StationTop stationTop = new StationTop();
                             stationTop.setStationId(siteId);
                             stationTop.setStationName(siteName);
                             stationTop.setTotalValue(reValue);
                             stationTop.setType(3);
                             stationTop.setCreateDate(new Date());
                             int result = _iStationTopService.save(stationTop);
                         } else {
                             stationTop3.setTotalValue(reValue);
                             stationTop3.setStationName(siteName);
                             stationTop3.setCreateDate(new Date());
                             _iStationTopService.updateAll(stationTop3);
                         }
                         break;
                     case 4:
                         zhengqiValue = GetStationEnergyTotal(stationItem.code, stationItem.station_id);
                         StationTop stationTop4 = _iStationTopService.getStationDataBySiteIdAndType(siteId, 4);
                         if (stationTop4 == null) {
                             StationTop stationTop = new StationTop();
                             stationTop.setStationId(siteId);
                             stationTop.setStationName(siteName);
                             stationTop.setTotalValue(zhengqiValue);
                             stationTop.setType(4);
                             stationTop.setCreateDate(new Date());
                             int result = _iStationTopService.save(stationTop);
                         } else {
                             stationTop4.setTotalValue(zhengqiValue);
                             stationTop4.setStationName(siteName);
                             stationTop4.setCreateDate(new Date());
                             _iStationTopService.updateAll(stationTop4);
                         }
                         break;
                 }
             }


         }
     }


    public  double GetStationEnergyTotal(String code,String stationId) {
        Calendar calendar = Calendar.getInstance();  //当前时间
        Date endDate = calendar.getTime(); //得到当前时间
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为1day前
        Date startDate = calendar.getTime();   //得到前一天的时间

        String downsample = "1d-first-none";
        downsample = null;



        if (code.equals("ES1_GSBx_FsInt")) {//彩纤->蒸汽
            String calcCode="ES_AXCX_FsInt";
            double totalValue = 0;
                String content = OpentsdbUtil.OpenTSDBQuery(startDate, endDate, calcCode, downsample, stationId, url);
                if (content == null||content.equals("[]")) {
                    totalValue += 0;
                } else {
                    TSDBResult resultList = OpentsdbUtil.paseTSDBResult(content).get(0);
                    totalValue = resultList.getValue().doubleValue();
                }

            return totalValue;
        }else if(code.equals("ES1_GTx_PInt")){//彩纤->电
            String calcCode="ES_AXCX_Eoutput";
            double totalValue = 0;
            String content = OpentsdbUtil.OpenTSDBQuery(startDate, endDate, calcCode, downsample, stationId, url);
            if (content == null||content.equals("[]")) {
                totalValue += 0;
            } else {
                TSDBResult resultList = OpentsdbUtil.paseTSDBResult(content).get(0);
                totalValue = resultList.getValue().doubleValue();
            }

            return totalValue;
        }
        else if(code.equals("ES2_GTx_PInt")){//君乐宝->电
            String calcCode="ES_JLB_Poutput";
            double totalValue = 0;
            String content = OpentsdbUtil.OpenTSDBQuery(startDate, endDate, calcCode, downsample, stationId, url);
            if (content == null||content.equals("[]")) {
                totalValue += 0;
            } else {
                TSDBResult resultList = OpentsdbUtil.paseTSDBResult(content).get(0);
                totalValue = resultList.getValue().doubleValue();
            }

            return totalValue;
        }
        else if(code.equals("ES2_GSBx_FsInt"))//君乐宝->蒸汽
        {
            double totalValue = 0;
            String[] calcCodes = {"ES_JLB_FsIntIP", "ES_JLB_FsIntLP"};
            for (String calcCode : calcCodes) {
                String content = OpentsdbUtil.OpenTSDBQuery(startDate, endDate, calcCode, downsample, stationId, url);
                if (content == null||content.equals("[]")) {
                    totalValue += 0;
                } else {
                    TSDBResult resultList = OpentsdbUtil.paseTSDBResult(content).get(0);
                    totalValue = resultList.getValue().doubleValue();
                }
            }
            return totalValue;
        }
        else if(code.equals("ES3_GSBx_FsInt")){//龙游->蒸汽
            String calcCode="ES_LYNC_FsInt";
            double totalValue = 0;
            String content = OpentsdbUtil.OpenTSDBQuery(startDate, endDate, calcCode, downsample, stationId, url);
            if (content == null||content.equals("[]")) {
                totalValue += 0;
            } else {
                TSDBResult resultList = OpentsdbUtil.paseTSDBResult(content).get(0);
                totalValue += resultList.getValue().doubleValue();
            }
            return totalValue;
        }
        else if(code.equals("ES4_GSBx_PInt")){//腾讯->电
            String calcCode="ES_Tencent_Eoutput";
            double totalValue = 0;
            String content = OpentsdbUtil.OpenTSDBQuery(startDate, endDate, calcCode, downsample, stationId, url);
            if (content == null||content.equals("[]")) {
                totalValue += 0;
            } else {
                TSDBResult resultList = OpentsdbUtil.paseTSDBResult(content).get(0);
                totalValue = resultList.getValue().doubleValue();
            }
            return totalValue;
        }
        else if(code.equals("ES4_GSBx_PWcoolingInt")){//腾讯->冷
            String calcCode="ES_Tencent_PWcoolingInt";
            double totalValue = 0;
            String content = OpentsdbUtil.OpenTSDBQuery(startDate, endDate, calcCode, downsample, stationId, url);
            if (content == null||content.equals("[]")) {
                totalValue += 0;
            } else {
                TSDBResult resultList = OpentsdbUtil.paseTSDBResult(content).get(0);
                totalValue = resultList.getValue().doubleValue();
            }
            return totalValue;
        }
        else {
            double totalValue = 0;
            String content = OpentsdbUtil.OpenTSDBQuery(startDate, endDate, code, downsample, stationId, url);
            if (content == null||content.equals("[]")) {
                totalValue += 0;
            } else {
                TSDBResult resultList = OpentsdbUtil.paseTSDBResult(content).get(0);
                totalValue = resultList.getValue().doubleValue();
            }
            return totalValue;
        }


    }



    //endregion


    //region 日级数据计算



    @Scheduled(cron="${corn.hourDataSummary}")
    public void createHourData(){
        logger.info("小时级计算开始...");
        _iDatHourDataService.createHourData();
        logger.info("小时级计算结束...");
    }


    @Scheduled(cron = "${corn.DayEnergyDataSummary}")
    public void DayEnergyDataSummary() {
        logger.info("日级计算开始...");
        _iDatDayDataService.createDayData();
        logger.info("日级计算结束...");
//        List<StationCodes> stationCodeList = _iStationCodesService.selectAll();
//        List<String> siteIdList = stationCodeList.stream().map(x -> x.getProjectId()).distinct().collect(Collectors.toList());
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00"); //设置时间格式
//        Calendar calendar = Calendar.getInstance();  //当前时间
//
//        calendar.add(Calendar.MINUTE, -5);
//        String startDate = sdf.format(calendar.getTime()); //得到5分钟之前的时间
//        // String startDate ="2017-11-17 10:03:00";
//        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
//        String endDate = sdf.format(calendar.getTime());   //得到前一天5分钟之前的时间
//        // String endDate ="2017-11-20 09:40:00";
//        //循环站点
//        for (String siteId : siteIdList) {
//            List<String> codeList = stationCodeList.stream().filter(x -> x.getProjectId().equals(siteId))
//                    .map(x -> x.getCode()).distinct().collect(Collectors.toList());
//
//            List<OptDataCol> optDataStartColList = _iOptDataColService.getModelByProjectIdAndTime(siteId, startDate);
//            List<OptDataCol> optDataEndColList = _iOptDataColService.getModelByProjectIdAndTime(siteId, endDate);
//            // 读取测点名列表
//            for (String code : codeList) {
//
//                // Optional<OptDataCol> optDataColStart = optDataStartColList.stream().filter(x -> x.getCode().equals("ELE_GY_GY_1AH_DSZ1277_YC11_PV_CX_1016")).findFirst();
//                Optional<OptDataCol> optDataColStart = optDataStartColList.stream().filter(x -> x.getCode().equals(code)).findFirst();
//
//                // Optional<OptDataCol> optDataColEnd = optDataEndColList.stream().filter(x -> x.getCode().equals("ELE_GY_GY_1AH_DSZ1277_YC11_PV_CX_1016")).findFirst();
//                Optional<OptDataCol> optDataColEnd = optDataEndColList.stream().filter(x -> x.getCode().equals(code)).findFirst();
//
//                double startValue = 0;
//                double endValue = 0;
//                if (optDataColStart != Optional.<OptDataCol>empty() && code.equals(optDataColStart.get().getCode())) {
//                    startValue = optDataColStart.get().getCurValue() == null ? 0 : optDataColStart.get().getCurValue();
//                }
//                if (optDataColEnd != Optional.<OptDataCol>empty() && code.equals(optDataColEnd.get().getCode())) {
//                    endValue = optDataColEnd.get().getCurValue() == null ? 0 : optDataColEnd.get().getCurValue();
//                }
//                double diffValue = (endValue - startValue) > 0 ? (endValue - startValue) : 0;
//
//                List<DatDayData> dayDataList = _iDatDayDataService.GetDayData(siteId, code, (new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date())));
//                if (dayDataList.size() == 0) {
//                    DatDayData datDayData = new DatDayData();
//                    datDayData.setProjectId(siteId);
//                    datDayData.setCode(code);
//                    datDayData.setCurValue(diffValue);
//                    datDayData.setColTime(Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date())));
//                    datDayData.setSysTime(new Date());
//                    _iDatDayDataService.save(datDayData);
//                } else {
//                    DatDayData datDayData = dayDataList.get(0);
//                    datDayData.setCurValue(diffValue);
//                    datDayData.setSysTime(new Date());
//
//                   _iDatDayDataService.updateByPrimaryKey(datDayData);
//                }
//            }
//        }
    }


    //endregion

    //region  光伏定时任务


    //小时定时数据执行
    @Scheduled(cron = "${corn.hour}") //每分钟执行一次
    public void hourTasks() throws JSONException, ParseException {
        System.out.println("每5分钟执行一次。开始……");
        //定时执行任务  //站id: 2022, 2021, 2014, 2098, 2023,2024
        //getHourYield("2022", 35, new Date());
        //getHourYield("2021", 35, new Date());
        //getHourYield("2014", 35, new Date());
        //getHourYield("2098", 35, new Date());
        getHourYield("2023", 35, new Date());
        getHourYield("2024", 35, new Date());
        System.out.println("每5分钟执行一次。结束。");
        logger.info("小时定时数据执行,每5分钟执行一次。结束。");
    }


    @Scheduled(fixedRate = 20000)//cron = "0/2 0 * * * ?"
    public void hourTaskTow() throws ParseException {

        //region 高压低压辐照值和即时功率
        //低压的及时功率
//        getFuzhaoAndJishigl_DY("2022","cDCPower","35");
//        //低压的辐照值
//        getFuzhaoAndJishigl_DY("2022","cRadiation_tilt","36");
//
//        //获取高压的及时功率
//        getFuzhaoAndJishigl_GY("2023","InstantPower");
//        //获取高压的辐照值
//        getFuzhaoAndJishigl_GY("2023","RadiationValue");
        //endregion

        //region 高压光伏电站
//        getDayYield("2023", "DailyYield", new Date());
//        getDayYield("2024", "DailyYield", new Date());
        //endregion
        //region 定时测试
     /*   getHourYield("2023", 35, new Date());
        getHourYield("2024", 35, new Date());*/
        //endregion

        //region 日测试
        getDayYield("2023", "DailyYield", new Date());
//        getDayYield("2024", "DailyYield", new Date());
        //endregion
        //region 月测试
       /* getMonthYield("2023", "MonthYield",  new Date());
        getMonthYield("2024","MonthYield", new Date());*/
        //endregion
        //region 年测试
       /* getYearYield("2023", "YearYield", new Date());
        getYearYield("2024", "YearYield", new Date());*/
        //endregion
    }

    //日定时数据执行
    @Scheduled(cron = "${corn.day}")
    public void dayTasks() throws JSONException, IllegalAccessException, ParseException {
        System.out.println("每10分钟执行一次。开始……");
        logger.info("日定时数据执行,每10分钟执行一次。开始……");
        //定时执行任务 //站id: 2022, 2021, 2014, 2098, 2023
//        getDayYield("2022", "DailyYield", new Date());
//        getDayYield("2021", "DailyYield", new Date());
//        getDayYield("2014", "DailyYield", new Date());
//        getDayYield("2098", "DailyYield", new Date());
        getDayYield("2023", "DailyYield", new Date());
        getDayYield("2024", "DailyYield", new Date());
        System.out.println("每10分钟执行一次。结束。");
        logger.info("日定时数据执行,每10分钟执行一次。结束。");
    }

    //月定时数据执行
    @Scheduled(cron = "${corn.month}")
    public void monthTasks() throws JSONException {
        System.out.println("每2小时执行一次。开始……");
        logger.info("月定时数据执行,每2小时执行一次。开始……");
        //定时执行任务 //站id: 2022, 2021, 2014, 2098, 2023
//        getMonthYield("2022", "MonthYield", new Date());
//        getMonthYield("2021", "MonthYield", new Date());
//        getMonthYield("2014", "MonthYield", new Date());
//        getMonthYield("2098", "MonthYield", new Date());
        getMonthYield("2023", "MonthYield",  new Date());
        getMonthYield("2024","MonthYield", new Date());

        System.out.println("每2小时执行一次。结束。");
        logger.info("月定时数据执行,每2小时执行一次。结束。");
    }

    //年定时数据执行
    @Scheduled(cron = "${corn.year}")
    public void yearTasks() throws JSONException, IllegalAccessException {
        System.out.println("每1天执行一次。开始……");
        logger.info("年定时数据执行,每1天执行一次。开始……");
        //定时执行任务 //站id: 2022, 2021, 2014, 2098, 2023
//        getYearYield("2022", "YearYield", new Date());
//        getYearYield("2021", "YearYield", new Date());
//        getYearYield("2014", "YearYield", new Date());
//        getYearYield("2098", "YearYield", new Date());
        getYearYield("2023", "YearYield", new Date());
        getYearYield("2024", "YearYield", new Date());
        System.out.println("每1天执行一次。结束。");
        logger.info("年定时数据执行,每1天执行一次。结束。");
    }

    /**
     * 获取小时任务
     *
     * @param station_id
     * @param equ_category_big
     * @param datetime
     * @throws JSONException
     * @throws IllegalAccessException
     */
    public void getHourYield(String station_id, Integer equ_category_big, Date datetime) throws JSONException, ParseException//@RequestBody Map<String, String> param
    {
        if(station_id.equals("2023")||station_id.equals("2024"))
        {
            double lastValue=0;
            String pattan = "yyyy-MM-dd HH:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat(pattan);
            //region 高压
            //通过Station_Id来获取station_codes_head
//            List<StationCodeshead> stationCodeshead= null;
            List<StationCodesHead> stationCodeshead= null;
            try {
//                stationCodeshead = stationCodesheadService.getStationCodeshead(station_id);
                stationCodeshead=iStationCodesHeadService.getStationCodeshead(station_id);
                String code_spvs= getStationCodes(station_id,"DailyYield");
                if(!code_spvs.equals("")) {

                    int len=code_spvs.split(",").length;
                    String[] arraycode=code_spvs.split(",");
                    if(len>50&&len<100)
                    {
                        String code1="";
                        String code2="";
                        for (int i=0;i<50;i++)
                        {
                            code1+=arraycode[i]+",";
                        }
                        for(int i=50;i<len;i++)
                        {
                            code2+=arraycode[i]+",";
                        }
                        String code_spvs1= code1.substring(0,code1.length()-1);
                        lastValue += openTsdbApi(code_spvs1, stationCodeshead.get(0).getStaid());
                        String code_spvs2= code2.substring(0,code2.length()-1);
                        lastValue += openTsdbApi(code_spvs2, stationCodeshead.get(0).getStaid());
                    }else if(len<200&&len>100)
                    {
                        String code1="";
                        String code2="";
                        String code3="";
                        for (int i=0;i<50;i++)
                        {
                            code1+=arraycode[i]+",";
                        }
                        for(int i=50;i<100;i++)
                        {
                            code2+=arraycode[i]+",";
                        }
                        for(int i=100;i<len;i++)
                        {
                            code3+=arraycode[i]+",";
                        }

                        String code_spvs1= code1.substring(0,code1.length()-1);
                        lastValue += openTsdbApi(code_spvs1, stationCodeshead.get(0).getStaid());
                        String code_spvs2= code2.substring(0,code2.length()-1);
                        lastValue += openTsdbApi(code_spvs2, stationCodeshead.get(0).getStaid());
                        String code_spvs3= code3.substring(0,code3.length()-1);
                        lastValue += openTsdbApi(code_spvs3, stationCodeshead.get(0).getStaid());
                    }
                    else
                    {
                        lastValue += openTsdbApi(code_spvs, stationCodeshead.get(0).getStaid());
                    }
                }

                ParsePosition pos = new ParsePosition(0);
                Date time = sdf.parse(sdf.format(datetime), pos);
                //System.out.println(lastValue);
                //保存到数据库小时值
//                hourdataSpvService.InsertOrUpdatedataSpv(station_id, "HourYield", time, lastValue);
                iDatHourDataSpvService.InsertOrUpdatedataSpv(station_id,"HourYield",time,lastValue);
                //保存及时功率和辐照值

                //获取高压的及时功率
                Object instantPower = getFuzhaoAndJishigl_GY(station_id, "InstantPower");
//                hourdataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"InstantPower",time,Double.parseDouble(instantPower.toString()));
                iDatHourDataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"InstantPower",time,Double.parseDouble(instantPower.toString()));
                //获取高压的辐照值
                Object radiationValue = getFuzhaoAndJishigl_GY(station_id, "RadiationValue");
                //hourdataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"RadiationValue",time,Double.parseDouble(radiationValue.toString()));
                iDatHourDataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"RadiationValue",time,Double.parseDouble(radiationValue.toString()));
                 //获取高压总辐射1日累计值
                Object totalRadiationOneDay = getFuzhaoAndJishigl_GY(station_id, "TotalRadiationOneDay");
//                hourdataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"TotalRadiationOneDay",time,Double.parseDouble(totalRadiationOneDay.toString()));
                iDatHourDataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"TotalRadiationOneDay",time,Double.parseDouble(totalRadiationOneDay.toString()));
            } catch (Exception e) {
                logger.info("小时定时数据执行，调用OpenTSDB接口服务为："+e.getMessage());
                e.printStackTrace();
            }



            //endregion
        }else
        {
            //region 低压
            double lastValue = 0;
            //获取生备
            List<BasEquInfo> listEquInfo = null;//Integer.parseInt(equ_category_big)
            try {

//                listEquInfo = equInfoService.getListEquInfo(station_id, equ_category_big);
                listEquInfo = iBasEquInfoService.getListEquInfo(station_id, equ_category_big);
                String deviceNoList = "";
                for (BasEquInfo item : listEquInfo) {
                    deviceNoList += "\"" + item.getDeviceNo() + "\",";
                }
                if (deviceNoList.length() > 0) {
                    deviceNoList = deviceNoList.substring(0, deviceNoList.length() - 1);
                }
                //准备参数
                String pattan = "yyyy-MM-dd HH:00:00";
                String pattern = "yyyy-MM-dd HH:59:59";
                SimpleDateFormat sdf = new SimpleDateFormat(pattan);
                SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);

                String JsonParam = "{" +
                        "\"startTime\":\"" + sdf.format(datetime) + "\"," +
                        "\"endTime\":\"" + sdf1.format(datetime) + "\"," +
                        "\"metricList\": [\"cDailyYield\"]," +
                        "\"deviceNoList\":[" + deviceNoList + "] " +
                        "}";
                JSONObject parse = JSONObject.parseObject(JsonParam);
                JSONObject jsonObject = HttpClientHelper.httpPostPV(pvurl, parse, false);
                JSONArray dataList = jsonObject.getJSONArray("dataList");
                List<Map<String, Object>> list = new LinkedList<>();
                if (dataList.size() > 0) {
                    for (int i = 0; i < dataList.size(); i++) {
                        JSONObject emp = dataList.getJSONObject(i);
                        Double first = Double.parseDouble((String) emp.get("lastValue"));
                        Double last = Double.parseDouble((String) emp.get("firstValue"));
                        if (first == 0) {
                            lastValue += 0;
                        } else {
                            lastValue += (Double.parseDouble((String) emp.get("lastValue")) - Double.parseDouble((String) emp.get("firstValue")));
                        }
                    }
                }
                //return new AjaxResult().success(lastValue);
                ParsePosition pos = new ParsePosition(0);
                Date time = sdf.parse(sdf.format(datetime), pos);
                //保存到数据库小时值
                iDatHourDataSpvService.InsertOrUpdatedataSpv(station_id,"HourYield",time,lastValue);
                //hourdataSpvService.InsertOrUpdatedataSpv(station_id, "HourYield", time, lastValue);
                //保存低压及时功率和辐照度
                //低压的及时功率
                Object cDCPower = getFuzhaoAndJishigl_DY(station_id, "cDCPower", "35");
                //hourdataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"cDCPower",time,Double.parseDouble(cDCPower.toString()));
                iDatHourDataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"cDCPower",time,Double.parseDouble(cDCPower.toString()));
                //低压的辐照值
                Object cRadiation_tilt = getFuzhaoAndJishigl_DY(station_id, "cRadiation_tilt", "36");
                //hourdataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"cRadiation_tilt",time,Double.parseDouble(cRadiation_tilt.toString()));
                iDatHourDataSpvService.InsertOrUpdatedataFuZhaoJSGYSpv(station_id,"cRadiation_tilt",time,Double.parseDouble(cRadiation_tilt.toString()));
            } catch (NumberFormatException e) {
                logger.info("小时定时数据执行，调用HBASE接口服务为："+e.getMessage());
                e.printStackTrace();
                //return new AjaxResult().failure();

            }
            //endregion
        }



    }

    /**
     * 获取日定时任务
     *
     * @param station_id
     * @param code
     * @param date
     */

    public void getDayYield(String station_id, String code, Date date) throws ParseException {

        //region old
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
//            dayDataSpvService.InsertOrUpdatedataSpv(station_id, code, sdf.format(date), sdf1.format(date));
//        } catch (Exception e) {
//            logger.info("日定时任务，调用HBASE接口服务为：" + e.getMessage());
//            e.printStackTrace();
//
//        }
        //endregion
        //region new

        if(station_id.equals("2023")||station_id.equals("2024"))
        {
            double lastValue=0;
            //region 高压
            //通过Station_Id来获取station_codes_head
            List<StationCodesHead> stationCodeshead= null;
            try {
//                stationCodeshead = stationCodesheadService.getStationCodeshead(station_id);
                stationCodeshead = iStationCodesHeadService.getStationCodeshead(station_id);
                String code_spvs= getStationCodes(station_id,"DailyYield");
                if(!code_spvs.equals("")) {

                    int len=code_spvs.split(",").length;
                    String[] arraycode=code_spvs.split(",");
                    if(len>50&&len<100)
                    {
                        String code1="";
                        String code2="";
                        for (int i=0;i<50;i++)
                        {
                            code1+=arraycode[i]+",";
                        }
                        for(int i=50;i<len;i++)
                        {
                            code2+=arraycode[i]+",";
                        }
                        String code_spvs1= code1.substring(0,code1.length()-1);
                        lastValue += openTsdbApi(code_spvs1, stationCodeshead.get(0).getStaid());
                        String code_spvs2= code2.substring(0,code2.length()-1);
                        lastValue += openTsdbApi(code_spvs2, stationCodeshead.get(0).getStaid());
                    }else if(len<200&&len>100)
                    {
                        String code1="";
                        String code2="";
                        String code3="";
                        for (int i=0;i<50;i++)
                        {
                            code1+=arraycode[i]+",";
                        }
                        for(int i=50;i<100;i++)
                        {
                            code2+=arraycode[i]+",";
                        }
                        for(int i=100;i<len;i++)
                        {
                            code3+=arraycode[i]+",";
                        }

                        String code_spvs1= code1.substring(0,code1.length()-1);
                        lastValue += openTsdbApi(code_spvs1, stationCodeshead.get(0).getStaid());
                        String code_spvs2= code2.substring(0,code2.length()-1);
                        lastValue += openTsdbApi(code_spvs2, stationCodeshead.get(0).getStaid());
                        String code_spvs3= code3.substring(0,code3.length()-1);
                        lastValue += openTsdbApi(code_spvs3, stationCodeshead.get(0).getStaid());
                    }
                    else
                    {
                        lastValue += openTsdbApi(code_spvs, stationCodeshead.get(0).getStaid());
                    }
                }

                //System.out.println(lastValue);
                //保存到数据库小时值
//                dayDataSpvService.InsertOrUpdatedataSpv(station_id, code,date,lastValue);
                _iDatDayDataService.InsertOrUpdatedataSpv(station_id,code,date,lastValue);
            } catch (Exception e) {
                logger.info("日定时任务，调用HBASE接口服务为：" + e.getMessage());
                e.printStackTrace();
            }



            //endregion
        }


        //endregion
    }

    /**
     * 获取月定时任务
     *
     * @param station_id
     * @param code
     * @param date
     */
    public void getMonthYield(String station_id, String code, Date date) {

        try {
            String begintime = DifferentDate.danYueOneDay();
            String endtime = DifferentDate.danYueLastDay();
//            monthDataSpvService.InsertOrUpdatedataSpv(station_id, code, begintime, endtime);
            iDatMonthDataService.InsertOrUpdatedataSpv(station_id,code,begintime,endtime);

        } catch (Exception e) {
            logger.info("月定时任务，调用HBASE接口服务为：" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 获取年定时任务
     *
     * @param station_id
     * @param code
     * @param date
     */
    public void getYearYield(String station_id, String code, Date date) {
        try {
            String begintime = DifferentDate.formatDate(DifferentDate.getCurrYearFirst());
            String endtime = DifferentDate.formatDate(DifferentDate.getCurrYearLast());
//           yearDataSpvService.InsertOrUpdateHourdataSpv(station_id, code, begintime, endtime);
            iDatYearDataService.InsertOrUpdateHourdataSpv(station_id,code,begintime,endtime);
        } catch (Exception e) {
            logger.info("年定时任务，调用HBASE接口服务为：" + e.getMessage());
            e.printStackTrace();
        }

    }

    //endregion



    //region  光伏数据接口


    /**
     * 获取所有光伏站的总电量
     * @return
     */
    //@RequestMapping("/abc")
    public Object  getStationAll()
    {
        Double total=0.0;
        try {

            List<PubStationInfo> list=iPubStationInfoService.getList();//stationInfoService.getList();
            if(list.size()>0)
            {
                for (PubStationInfo item:list) {
                    total +=(Double)stationData_Spv(item.getStationId(),"DailyYield");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }


    /**
     * 根据站点和类型参数获取当日总电量
     * @param stationId
     * @return
     */
    public Object stationData_Spv(String stationId,String param_Name) {
        //region old
//        double lastValue = 0;
//        //获取生备
//        List<EquInfo> listEquInfo = null;//Integer.parseInt(equ_category_big)
//        try {
//            listEquInfo = equInfoService.getListEquInfo(stationId, 35);
//            String deviceNoList = "";
//            for (EquInfo item : listEquInfo) {
//                deviceNoList += "\"" + item.getDevice_no() + "\",";
//            }
//            if (deviceNoList.length() > 0) {
//                deviceNoList = deviceNoList.substring(0, deviceNoList.length() - 1);
//            }
//            //准备参数
//            Date dtime = new Date();
//            String pattan = "yyyy-MM-dd 00:00:00";
//            String pattern = "yyyy-MM-dd 23:59:59";
//            SimpleDateFormat sdf = new SimpleDateFormat(pattan);
//            SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);
//
//            String JsonParam = "{" +
//                    "\"startTime\":\"" + sdf.format(dtime) + "\"," +
//                    "\"endTime\":\"" + sdf1.format(dtime) + "\"," +
//                    "\"metricList\": [\"cDailyYield\"]," +
//                    "\"deviceNoList\":[" + deviceNoList + "] " +
//                    "}";
//            JSONObject parse = JSONObject.parseObject(JsonParam);
//            JSONObject jsonObject = HttpClientHelper.httpPostPV(pvurl, parse, false);
//            JSONArray dataList = jsonObject.getJSONArray("dataList");
//            List<Map<String, Object>> list = new LinkedList<>();
//
//            if (dataList.size() > 0) {
//                for (int i = 0; i < dataList.size(); i++) {
//                    JSONObject emp = dataList.getJSONObject(i);
//                    lastValue += Double.parseDouble((String) emp.get("lastValue"));
//                }
//            }
//            return lastValue;
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return lastValue;
//        }
        //endregion

        //region  new
        Object obj=null;

        if(stationId.equals("2023")||stationId.equals("2024"))
        {

            try {
                obj = api_SpvGY(stationId,param_Name);
            } catch (Exception e) {
                e.printStackTrace();
                obj=0;
            }
        }else
        {
            try {
                obj = api_Spv(stationId, "cDailyYield", "35");

            } catch (Exception e) {
                e.printStackTrace();
               obj=0;
            }

        }
        return obj;
        //endregion
    }

    /**
     * 获取低压及时功率和辐照值
     * @param station_id
     * @param code
     * @param equ_category_big
     * @return
     */
    public  Object getFuzhaoAndJishigl_DY(String station_id,String code,String equ_category_big)
    {
      Object obj=  api_Spv(station_id,code,equ_category_big);

      return obj;
    }

    /**
     * 获取高压及时功率和辐照值
     * @param station_id
     * @param param_Name
     * @return
     */
    public Object getFuzhaoAndJishigl_GY(String station_id,String param_Name)
    {
       double obj= api_SpvGY(station_id,param_Name);
       return obj;
    }


    /**
     * 光伏低压API接口
     * @param station_id
     * @param code
     * @param equ_category_big
     * @return
     */
    public Object api_Spv(String station_id,String code,String equ_category_big)
    {
        double lastValue = 0;
        //获取生备
        List<BasEquInfo> listEquInfo = null;//Integer.parseInt(equ_category_big)
        try {
            listEquInfo = iBasEquInfoService.getListEquInfo(station_id, Integer.parseInt(equ_category_big));//equInfoService.getListEquInfo(station_id, Integer.parseInt(equ_category_big));
            String deviceNoList = "";
            for (BasEquInfo item : listEquInfo) {
                deviceNoList += "\"" + item.getDeviceNo() + "\",";
            }
            if (deviceNoList.length() > 0) {
                deviceNoList = deviceNoList.substring(0, deviceNoList.length() - 1);
            }
            //准备参数
            Date dtime = new Date();
            String pattan = "yyyy-MM-dd 00:00:00";
            String pattern = "yyyy-MM-dd 23:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat(pattan);
            SimpleDateFormat sdf1 = new SimpleDateFormat(pattern);

            String JsonParam = "{" +
                    "\"startTime\":\"" + sdf.format(dtime) + "\"," +
                    "\"endTime\":\"" + sdf1.format(dtime) + "\"," +
                    "\"metricList\": [\"" + code + "\"]," +
                    "\"deviceNoList\":[" + deviceNoList + "] " +
                    "}";
            JSONObject parse = JSONObject.parseObject(JsonParam);
            JSONObject jsonObject = HttpClientHelper.httpPostPV(pvurl, parse, false);
            JSONArray dataList = jsonObject.getJSONArray("dataList");
            List<Map<String, Object>> list = new LinkedList<>();

            if (dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    JSONObject emp = dataList.getJSONObject(i);
                    lastValue += Double.parseDouble((String) emp.get("lastValue"));
                }
            }
            return lastValue;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return lastValue;
        }
    }

    /**
     * 根据站点来获取高压当日发电量获取及时功率
     * @param station_id
     * @return
     */
    public double api_SpvGY(String station_id,String param_Name)
    {
        double lastValue=0;

        try {
//                List<StationCodeshead>  stationCodeshead = stationCodesheadService.getStationCodeshead(station_id);
            List<StationCodesHead> stationCodeshead=iStationCodesHeadService.getStationCodeshead(station_id);
                if(stationCodeshead!=null&&stationCodeshead.size()>0)
                {
                    //通过Station_Id来获取
                    //region spv
                   String code_spvs= getStationCodes(station_id,param_Name);
                   if(!code_spvs.equals(""))
                   {
                       int len=code_spvs.split(",").length;
                       String[] arraycode=code_spvs.split(",");
                       if(len<100&&len>50)
                       {
                           String code1="";
                           String code2="";
                           for (int i=0;i<50;i++)
                           {
                               code1+=arraycode[i]+",";
                           }
                           for(int i=50;i<len;i++)
                           {
                               code2+=arraycode[i]+",";
                           }
                           String code_spvs1= code1.substring(0,code1.length()-1);
                           lastValue += openTsdbApi(code_spvs1, stationCodeshead.get(0).getStaid());
                           String code_spvs2= code2.substring(0,code2.length()-1);
                           lastValue += openTsdbApi(code_spvs2, stationCodeshead.get(0).getStaid());
                       }else if(len<200&&len>100)
                       {
                           String code1="";
                           String code2="";
                           String code3="";
                           for (int i=0;i<50;i++)
                           {
                               code1+=arraycode[i]+",";
                           }
                           for(int i=50;i<100;i++)
                           {
                               code2+=arraycode[i]+",";
                           }
                           for(int i=100;i<len;i++)
                           {
                               code3+=arraycode[i]+",";
                           }

                           String code_spvs1= code1.substring(0,code1.length()-1);
                           lastValue += openTsdbApi(code_spvs1, stationCodeshead.get(0).getStaid());
                           String code_spvs2= code2.substring(0,code2.length()-1);
                           lastValue += openTsdbApi(code_spvs2, stationCodeshead.get(0).getStaid());
                           String code_spvs3= code3.substring(0,code3.length()-1);
                           lastValue += openTsdbApi(code_spvs3, stationCodeshead.get(0).getStaid());
                       }else
                       {
                           lastValue += openTsdbApi(code_spvs, stationCodeshead.get(0).getStaid());
                       }

                   }
            }
           return lastValue;

        } catch (Exception e) {
            logger.info("小时定时数据执行，调用OpenTSDB接口服务为："+e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据站ID和param_Name 来获取 是当日电量code还是及时功率code
     * @param station_id
     * @param param_Name
     * @return
     */
    public String getStationCodes(String station_id,String param_Name)
    {
       String codes="";
        //通过Station_Id来获取station_codes
//        List<com.bom.model.StationCodes> stationCodes = stationCodesService.getStationCodes(station_id,param_Name);
        List<StationCodes> stationCodes = iStationCodesService.getStationCodes(station_id,param_Name);
        if(stationCodes.size()>0&&stationCodes!=null)
        {
                for (StationCodes stationcode:stationCodes) {
                    codes+=  stationcode.getCode()+",";
                }
                if(codes.length()>0)
                {
                    codes=codes.substring(0,codes.length()-1);
                }
        }
        return codes;
    }



    /**
     * openTsdb 高压api接口
     * @param codes
     * @param staid
     * @return
     */
    private double openTsdbApi(String codes,String staid)
    {
        Date strendDate_spv =new Date(new Date().getTime()-600000);

        Date endDate_spv= new Date();
        String codes_spv=codes;
        String staid_spv=staid;
        String equipID_spv="IVS101";
        //String downsample_spv="1d-first-none";
        String downsample_spv=null;
        // downsample_spv="1n-last";
        String content_spv= OpentsdbUtil.OpenTSDBQuery_Spv(strendDate_spv,endDate_spv,codes_spv,equipID_spv,downsample_spv,staid_spv,url);
        //BigDecimal value=new BigDecimal(0);
        double value=0;

        List<TSDBResult> resultList_spv= null;
        try {
            resultList_spv = OpentsdbUtil.paseTSDBResult_Spv(content_spv);
            if(resultList_spv.size()>0&&resultList_spv!=null)
            {
                for (TSDBResult tsdbtesult:resultList_spv) {
                    value+= Double.parseDouble(tsdbtesult.getDatavalue().toString());
                }
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return  0;
        }
    }


    /**
     * 根据单个站的站id和code来获取总电量
     * @param station_id
     * @param equ_category_big
     * @param code
     * @return
     */
    private Object getStationPower(String station_id,String equ_category_big,String code)
    {
        //region new
        Object obj=null;
        String param_Name="DailyYield";
        if(code.equals("cDailyYield"))
        {
            param_Name="DailyYield";
        }else
        {
            param_Name="TotalYield";
        }
        if(station_id.equals("2023")||station_id.equals("2024"))
        {
            try {
                obj = api_SpvGY(station_id,param_Name);
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }else
        {
            try {
                obj = api_Spv(station_id, code, equ_category_big);
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }


        //endregion
    }

    //endregion

}

class  StationTopCode
{
    public String station_id;
    public  String station_name;
    public  int energyType;//类型:1:电，2：冷，3：热，4：蒸汽
    public  String soucetype;
    public  String code;
}




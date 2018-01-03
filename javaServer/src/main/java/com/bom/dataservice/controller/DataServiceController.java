package com.bom.dataservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bom.dataservice.model.*;
import com.bom.dataservice.service.*;
import com.bom.dataservice.utils.*;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-11
 * Time: 13:13
 */
@RestController
@RequestMapping("/ds")
public class DataServiceController {

    @Autowired
    IBigDataService bigDataService;

    @Autowired
    private StationTopService stationTopService;

    @Autowired
    private ApplicationContext context;
    @Autowired
    private EquInfoService equInfoService;

    @Autowired
    private StationInfoService stationInfoService;
    //光伏数据接口url
    @Value("${PV.url}")
    private String pvurl;
    @Autowired
    private IRedisService redisService;
@Value("${opentsdb.query}")
private String opentsdvurl;

    @ApiOperation(value = "获取点数据", notes = "根据codes获取点值")
    @ApiImplicitParams({
        @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"projectId\":\"1009\",\"type\":\"SS\",\"codes\":\"ES3_GSBx_Fs,GSB_GSB1_FsInt\",\"date\":\"2017-11-27 11:20:00\"}\n", required = true, paramType = "json"),
    })
    @PostMapping("/getdata")
    public Object getDatas(@RequestBody Map<String, String> param) {
        List<DataModel> dataModels = new LinkedList<DataModel>();
        try {
            String[] codess = param.get("codes").split(",");
            String type = param.get("type").toUpperCase();


            //type="FZ";
            String projectid = param.get("projectId");
            String date= param.get("date");
            ABSDataService dataService = DataServiceFactory.getInstance(type, context);
            dataModels = dataService.getData(projectid, param.get("codes"),date);
            return new AjaxResult().success(dataModels);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult().failure(e.toString());
        }

    }


    @ApiOperation(value = "获取曲线数据", notes = "根据codes，时间间隔获取曲线数据")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"projectId\":\"1009\",\"type\":\"MI\",\"codes\":\"GSB_GSB1_FsInt,GSB_GSB2_FsInt\",\"endDate\":\"2017-11-27 11:20:00\",\"beginDate\":\"2017-11-27 01:20:00\",\"downsample\":\"30m\"}", required = true, paramType = "json "),
    })
    @PostMapping("getldata")
    public Object getLineDatas(@RequestBody Map<String, String> param) {
        try {
            String beginDate = param.get("beginDate");
            String endDate = param.get("endDate");

            String projectid = param.get("projectId");
            String type = param.get("type").toUpperCase();
            String downsample=param.get("downsample") ;

            //type="FZ";
            ABSDataService dataService = DataServiceFactory.getInstance(type, context);
            List<LineData> listline = new LinkedList<LineData>();
            listline = dataService.getLineData(projectid, param.get("codes"), beginDate, endDate,downsample);
            return new AjaxResult().success(listline);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult().failure();
        }
    }


    @ApiOperation(value = "获取K线数据", notes = "根据codes，时间间隔获取K线数据")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"projectId\":\"123456\",\"type\":\"HH\",\"codes\":\"dddd,33344\",\"beginDate\":\"2017-10-11 13:00:00\",\"endDate\":\"2017-10-11 22:00:00\"}", required = true, paramType = "json"),
    })
    @PostMapping("getkldata")
    @ResponseBody()
    public Object getKLineDatas(@RequestBody Map<String, String> param) {
        try {
            String beginDate = param.get("beginDate");
            String endDate = param.get("endDate");
            String[] arrCode = param.get("codes").split(",");
            List<LineData> lineDatas = new LinkedList<>();
            List<String> dates = new ArrayList<>();
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime bdate = DateTime.parse(beginDate, format);
            DateTime edate = DateTime.parse(endDate, format).plusHours(1);
            dates.add(bdate.toString("yyyy-MM-dd HH:00:00"));
            while (bdate.isBefore(edate)) {
                bdate = bdate.plusHours(1);
                dates.add(bdate.toString("yyyy-MM-dd HH:00:00"));
            }

            for (String code : arrCode) {
                List<Map<String, Object>> dataModels = new LinkedList<>();
                LineData line = new LineData();
                line.setCode(code);
                for (String date : dates) {
                    Map<String, Object> mapData = new LinkedHashMap<>();
                    mapData.put("max", SimulationHelper.CreateRandomDouble(1, 100));
                    mapData.put("mix", SimulationHelper.CreateRandomDouble(1, 100));
                    mapData.put("begin", SimulationHelper.CreateRandomDouble(1, 100));
                    mapData.put("end", SimulationHelper.CreateRandomDouble(1, 100));
                    mapData.put("col_time", date);
                    dataModels.add(mapData);
                }
                line.setDatas(dataModels);
                lineDatas.add(line);
            }
            return new AjaxResult().success(lineDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult().failure();
        }
    }

    @ApiOperation(value = "获取实时数据", notes = "大数据接口数据")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "", required = true, paramType = "json"),
    })
    @PostMapping("getRealTimeData")
    @ResponseBody()
    /**
     * get 请求接口数据
     */
    public Object getRealTimeData() {
        String token = "7a82f7e7c228c1a24b7d9f99";
        String projectid = "1016";
        String code = "GL2_RSJFH_PV_CX_1016,GL2_steam_pressure_PV_CX_1016";


        //  String url = "http://10.37.47.73:8080/iot-integration/pspace/getRealDataByCodes?token=" + token + "&projectid=" + projectid + "&code=" + code + "";
        String url = "http://dfuse.enn.cn/iot-integration/pspace/getRealDataByCodes?token=" + token + "&projectid=" + projectid + "&code=" + code + "";

        try {
            JSONObject jsonObject = HTTPUtil.get(url);
            return new AjaxResult().success(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult().failure();
        }
    }

    @ApiOperation(value = "获取定时数据", notes = "根据type来获取数据 类型:1:电，2：冷，3：热，4：蒸汽")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"projectId\":\"123456\",\"type\":\"HH\",\"station_id\":\"2022\",\"equ_category_big\":\"35\",\"Date\":\"2017-11-18 13:00:00\"}", required = false, paramType = "json"),
    })
    @GetMapping("getStationTop")
    @ResponseBody()
    public Object getStation_top(int type) {
        try {
            List<StationTop> listStationTop = stationTopService.getListStationTop(type);
            List<StationTop> stationTopListReal = new ArrayList<>();

            List<StationInfo> stationInfoList = stationInfoService.getAllList();
            stationInfoList.forEach(x -> {
                if (!x.getStation_type().equals("0")) {
                    String stationId = x.getStation_id();
                    Optional<StationTop> stationTop = listStationTop.stream().filter(s -> s.getStation_id().equals(stationId)).findFirst();
                    if (!stationTop.equals(Optional.empty()))
                        stationTopListReal.add(stationTop.get());
                }
            });

            return new AjaxResult().success(stationTopListReal);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult().failure();
        }
    }




    @ApiOperation(value = "获取光伏逆变器电量", notes = "根据station_id，equ_category_big来获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"projectId\":\"123456\",\"type\":\"HH\",\"station_id\":\"2022\",\"equ_category_big\":\"35\",\"Date\":\"2017-11-18 13:00:00\"}", required = true, paramType = "json"),
    })
    @PostMapping("getDailyYield")
    @ResponseBody()
    public Object getDailyYield(@RequestBody Map<String, String> param) throws JSONException, IllegalAccessException//@RequestBody Map<String, String> param
    {
        //region old
//        String station_id = param.get("station_id");
//        String equ_category_big = param.get("equ_category_big");
//        String datetime = param.get("Date");
//        double lastValue = 0;
//        //获取生备
//        List<EquInfo> listEquInfo = null;//Integer.parseInt(equ_category_big)
//        try {
//            listEquInfo = equInfoService.getListEquInfo(station_id, Integer.parseInt(equ_category_big));
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
//            return new AjaxResult().success(lastValue);
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return new AjaxResult().failure();
//        }
        //endregion

        //region new
        String station_id = param.get("station_id");
        String equ_category_big = param.get("equ_category_big");
        String datetime = param.get("Date");
        Object obj=null;

        if(station_id.equals("2023")||station_id.equals("2024"))
        {
            try {
                obj = api_SpvGY(station_id,"DailyYield");
                return new AjaxResult().success(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return new AjaxResult().failure();
            }
        }else
        {
            try {
                obj = api_Spv(station_id, "cDailyYield", equ_category_big);
                return new AjaxResult().success(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return new AjaxResult().failure();
            }

        }
        //endregion
    }

    @ApiOperation(value = "获取光伏总电量，二氧化碳，节能，总收益", notes = "没有参数")
    @PostMapping("getSpvTotal")
    @ResponseBody()
    public Object getSpvTotal()
    {
        SpvTotal spvTotal=new SpvTotal();
        Object objg = null;
        try {
            objg = getGaoYaTotal();
            Object objd=getDiYaTotal();
            double tatolpower= Double.parseDouble(objg.toString())+Double.parseDouble(objd.toString()) ;
            spvTotal.setTotalPower(String.valueOf(tatolpower));
            double co= Double.parseDouble(objg.toString())*0.997+Double.parseDouble(objd.toString())*0.997 ;
            spvTotal.setCo2(String.valueOf(co));
            double energy= Double.parseDouble(objg.toString())*0.0404+Double.parseDouble(objd.toString())*0.0404 ;
            spvTotal.setEnergy(String.valueOf(energy));
            double income= Double.parseDouble(objg.toString())*0.98+Double.parseDouble(objd.toString())*0.75 ;
            spvTotal.setIncome(String.valueOf(income));
            return new AjaxResult().success(spvTotal);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult().failure();
        }
    }
    @ApiOperation(value = "获取光伏每个站的收入", notes = "根据station_id，equ_category_big来获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"projectId\":\"123456\",\"station_id\":\"2022\",\"equ_category_big\":\"35\"}", required = true, paramType = "json"),
    })
    @PostMapping("getStationIncome_Spv")
    @ResponseBody()
    public Object getStationIncome_Spv(@RequestBody Map<String, String> param)
    {
        String station_id = param.get("station_id");
        String equ_category_big = param.get("equ_category_big");
        Object stationPower = null;
        try {
            stationPower = getStationPower(station_id, equ_category_big, "cTotalYield");
            if(station_id.equals("2023")||station_id.equals("2024"))
            {
                double obj=   Double.parseDouble(stationPower.toString())*0.98;
                return new AjaxResult().success(obj);
            }else
            {
                double obj= Double.parseDouble(stationPower.toString())*0.75;
                return new AjaxResult().success(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult().failure();
        }
    }

    @ApiOperation(value = "获取光伏实时数据", notes = "根据station_id，equ_category_big，code来获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"projectId\":\"123456\",\"type\":\"HH\",\"station_id\":\"2022\",\"code\":\"cDailyYield\",\"equ_category_big\":\"36\",\"Date\":\"2017-11-18 13:00:00\"}", required = true, paramType = "json"),
    })
    @PostMapping("getNewestData_Spv")
    @ResponseBody()
    public Object getNewestData_Spv(@RequestBody Map<String, String> param) throws JSONException, IllegalAccessException//@RequestBody Map<String, String> param
    {
        //region old
//        String station_id = param.get("station_id");
//        String equ_category_big = param.get("equ_category_big");
//        String datetime = param.get("Date");
//        String code = param.get("code");
//        double lastValue = 0;
//        //获取生备
//        List<EquInfo> listEquInfo = null;//Integer.parseInt(equ_category_big)
//        try {
//            listEquInfo = equInfoService.getListEquInfo(station_id, Integer.parseInt(equ_category_big));
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
//                    "\"metricList\": [\"" + code + "\"]," +
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
//            return new AjaxResult().success(lastValue);
//
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            return new AjaxResult().failure();
//        }
        //endregion

        //region new
        String station_id = param.get("station_id");
        String equ_category_big = param.get("equ_category_big");
        String datetime = param.get("Date");
        String code = param.get("code");
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
                return new AjaxResult().success(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return new AjaxResult().failure();
            }
        }else
        {
            try {
                obj = api_Spv(station_id, code, equ_category_big);
                return new AjaxResult().success(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return new AjaxResult().failure();
            }
        }


        //endregion
    }

    @ApiOperation(value = "获取光伏高压站收入数据", notes = "根据station_id来获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"station_ids\":\"2023,2024\"}", required = true, paramType = "json"),
    })
    @PostMapping("getSpvTotalall")
    @ResponseBody()
    public Object getSpvTotalall(@RequestBody Map<String, String> param)
    {
        List<SpvTotalAll> spvlist=new LinkedList<SpvTotalAll>();
        String station_id = param.get("station_ids");

        try {
            if(!station_id.equals(""))
            {
                String[] split = station_id.split(",");
                if(split.length>0)
                {
                    for (String item:split ) {

                        SpvTotalAll sta=new SpvTotalAll();
                        Object yestodaytotal = null;

                            yestodaytotal = getYestodaytotal(item);
                            Object lastmonthtotal = getLastmonthtotal(item);
                            Object totalPower = getTotalPower(item);
                            Object hourCO2_cer = getTotalCOAndCoal(item, "HourCO2_CER");
                            Object hourCoal_cer = getTotalCOAndCoal(item, "HourCoal_CER");
                            double yestodayIncome = Double.parseDouble(yestodaytotal.toString()) * 0.98;
                            double lastmonthIncome = Double.parseDouble(lastmonthtotal.toString()) * 0.98;
                            double totalPowerIncome = Double.parseDouble(totalPower.toString()) * 0.98;
                            sta.setStation_id(item);

                            sta.setTotalPower(totalPower.toString());

                            sta.setLastmonthIncome(String.valueOf(lastmonthIncome));

                            sta.setTotalCO2(hourCO2_cer.toString());

                            sta.setTotalCoal(hourCoal_cer.toString());

                            sta.setYestodayIncome(String.valueOf(yestodayIncome));

                            sta.setTotalIncome(String.valueOf(totalPowerIncome));

                            spvlist.add(sta);
                    }
                }
            }
            System.out.println(spvlist);
            return new AjaxResult().success(spvlist);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new AjaxResult().failure();
        }
    }



    public Object getPower_Spv()
    {
        return  0;
    }






    /**
     * 光伏及时功率接口
     * @param station_id
     * @param paramName
     * @return
     */
    @Autowired EquparamhbaseService equparamhbaseService;
    @ApiOperation(value = "获取光伏即时功率数据", notes = "根据station_id，paramName来获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "param", value = "{\"token\":\"asdfasd\",\"projectId\":\"123456\",\"station_id\":\"2022\",\"ParamName_EN\":\"InstantPower\"}", required = true, paramType = "json"),
    })
    @PostMapping("getHBaseData_SPV")
    @ResponseBody()
    public Object getHBaseData_SPV(@RequestBody Map<String, String> param)
    {
        String station_id=param.get("station_id");
        String paramName_EN=param.get("ParamName_EN");
        //根据
        double obj=0;
        if(station_id.equals("2023")||station_id.equals("2024"))
        {
            try {
                Object  obj1 = api_SpvGY(station_id,paramName_EN);
                return new AjaxResult().success(obj1);
            } catch (Exception e) {
                e.printStackTrace();
                return new AjaxResult().failure();
            }
        }else
        {
            List<Equparamhbase> listEqhhase= null;
            try {
                listEqhhase = equparamhbaseService.getListEqHb(station_id,paramName_EN);
                if(listEqhhase.size()>0)
                {
                    for (Equparamhbase item:listEqhhase) {
                        Object  o = api_Spv(station_id, item.getParamFieldName_HBase(), String.valueOf(item.getEquipmentType()));
                        obj+=Double.parseDouble(o.toString()) ;
                    }
                }
                return new AjaxResult().success(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return new AjaxResult().failure();
            }
        }


    }


    /**
     * 光伏API接口
     * @param station_id
     * @param code
     * @param equ_category_big
     * @return
     */
    public Object api_Spv(String station_id,String code,String equ_category_big)
    {
        double lastValue = 0;
        //获取生备
        List<EquInfo> listEquInfo = null;//Integer.parseInt(equ_category_big)
        try {
            listEquInfo = equInfoService.getListEquInfo(station_id, Integer.parseInt(equ_category_big));
            String deviceNoList = "";
            for (EquInfo item : listEquInfo) {
                deviceNoList += "\"" + item.getDevice_no() + "\",";
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
                    lastValue = Double.parseDouble((String) emp.get("lastValue"));
                }
            }
            return lastValue;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return lastValue;
        }
    }


    @Autowired
    private  StationCodesheadService stationCodesheadService;
    @Autowired
    private StationCodesService stationCodesService;
    /**
     * 根据站点来获取高压当日发电量获取及时功率
     * @param station_id
     * @return
     */
    public double api_SpvGY(String station_id,String param_Name)
    {
        double lastValue=0;

        try {
            List<StationCodeshead>  stationCodeshead = stationCodesheadService.getStationCodeshead(station_id);
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
                        lastValue += openTsdbApi(code_spvs1, stationCodeshead.get(0).getStaId());
                        String code_spvs2= code2.substring(0,code2.length()-1);
                        lastValue += openTsdbApi(code_spvs2, stationCodeshead.get(0).getStaId());
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
                        lastValue += openTsdbApi(code_spvs1, stationCodeshead.get(0).getStaId());
                        String code_spvs2= code2.substring(0,code2.length()-1);
                        lastValue += openTsdbApi(code_spvs2, stationCodeshead.get(0).getStaId());
                        String code_spvs3= code3.substring(0,code3.length()-1);
                        lastValue += openTsdbApi(code_spvs3, stationCodeshead.get(0).getStaId());
                    }else
                    {
                        lastValue += openTsdbApi(code_spvs, stationCodeshead.get(0).getStaId());
                    }

                }
            }
            return lastValue;

        } catch (Exception e) {
            //logger.info("小时定时数据执行，调用OpenTSDB接口服务为："+e.getMessage());
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
        List<StationCodes> stationCodes = stationCodesService.getStationCodes(station_id,param_Name);
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
//        Date strendDate_spv =new Date(new Date().getTime()-600000);
//        Date strendDate_spv =new Date(new Date().getTime()-10000);

        String pattan = "yyyy-MM-dd 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(pattan);
        ParsePosition pos = new ParsePosition(0);
        Date strendDate_spv = sdf.parse(sdf.format(new Date()), pos);

        Date endDate_spv= new Date();

        String codes_spv=codes;
        String staid_spv=staid;
        String equipID_spv="IVS101";
        //String downsample_spv="1d-first-none";
        String downsample_spv=null;
        // downsample_spv="1n-last";
        String content_spv= OpentsdbUtil.OpenTSDBQuery_Spv(strendDate_spv,endDate_spv,codes_spv,equipID_spv,downsample_spv,staid_spv,opentsdvurl);
        //BigDecimal value=new BigDecimal(0);
        double value=0;

        List<TSDBResult> resultList_spv= null;
        try {
            resultList_spv = OpentsdbUtil.paseTSDBResult_Spv(content_spv);
            if(resultList_spv.size()>0&&resultList_spv!=null)
            {
                for (TSDBResult tsdbtesult:resultList_spv) {
                    value+= Double.parseDouble(tsdbtesult.getDatavalue());
                }
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return  0;
        }
    }



    /**
     * 所有光伏电站低压的总电量
     * @return
     */
    private   Object getDiYaTotal()
    {
        //2022, 2021, 2014, 2098, 2023,2024
        Object object1= getStationPower("2022","35","cTotalYield");
        Object object2= getStationPower("2021","35","cTotalYield");
        Object object3= getStationPower("2014","35","cTotalYield");
        Object object4= getStationPower("2098","35","cTotalYield");
        double obj= Double.parseDouble(object1.toString()) +Double.parseDouble(object2.toString())  +Double.parseDouble(object3.toString())+Double.parseDouble(object4.toString());
        return obj;
    }
    /**
     * 所有光伏电站高压的总电量
     * @return
     */
    private Object getGaoYaTotal()
    {
        Object object5= getStationPower("2023","35","cTotalYield");
        Object object6= getStationPower("2024","35","cTotalYield");
        double obj= Double.parseDouble(object5.toString())+Double.parseDouble(object6.toString());
        return obj;
    }

    /**
     * 获取所有电站的二氧化碳
     * @return
     */
    private Object getTotalCo()
    {
        try {
            Object objg = getGaoYaTotal();
            Object objd=getDiYaTotal();
            double co= Double.parseDouble(objg.toString())*0.997+Double.parseDouble(objd.toString())*0.997 ;
            return co;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }




    }
    /**
     * 获取所有电站的节能量
     * @return
     */
    private Object getTotalMei()
    {
        try {
            Object objg = getGaoYaTotal();
            Object objd=getDiYaTotal();
            double co= Double.parseDouble(objg.toString())*0.0404+Double.parseDouble(objd.toString())*0.0404 ;
            return co;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 获取所有电站的收益
     * @return
     */
    private Object getTotalMoney()
    {
        try {
            Object objg = getGaoYaTotal();
            Object objd=getDiYaTotal();
            double co= Double.parseDouble(objg.toString())*0.98+Double.parseDouble(objd.toString())*0.75 ;
            return co;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取所有电站的总电量
     * @return
     */
    private Object getTotalPower()
    {
        try {
            Object objg = getGaoYaTotal();
            Object objd=getDiYaTotal();
            double co= Double.parseDouble(objg.toString())+Double.parseDouble(objd.toString()) ;
            return co;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
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

    //region 光伏的新需求

    //昨日收入   上月收入    累计收入   累计发电量   累计二氧化碳减排量   累计节能量

    @Autowired
    private  DayDataSpvService dayDataSpvService;
    /**
     * 昨日发电量
     * @param stion_id
     * @return
     */
    private Object getYestodaytotal(String stion_id)
    {
        Date date=new Date();
        Date addonedate = DifferentDate.addonedate(date, -1);
        Object yestodaytotal=0.0;
        try {
            yestodaytotal = dayDataSpvService.getYestodaytotal(stion_id, addonedate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yestodaytotal;
    }

    @Autowired
    private MonthDataSpvService monthDataSpvService;
    /**
     * 上月发电量
     * @param stion_id
     * @return
     */
    private Object getLastmonthtotal(String stion_id)
    {
        Date dtime=new Date();
        Date addonemonth = DifferentDate.addonemonth(dtime, -1);
        Object lastmonthtotal=0;
        try {
            lastmonthtotal = monthDataSpvService.getLastmonthtotal(stion_id, addonemonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastmonthtotal;
    }

    @Autowired
    private YearDataSpvService yearDataSpvService;
    /**
     * 累计发电量
     * @param stion_id
     * @return
     */
    private  Object getTotalPower(String stion_id)
    {
        Object totalPower=0;
        try {
            totalPower = yearDataSpvService.getTotalPower(stion_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPower;
    }

    /**
     * 累计二氧化碳减排量   累计节能量
     * @param stion_id
     * @return
     */
    private Object getTotalCOAndCoal(String stion_id,String code)
    {
        Object totalCOAndCoal=0;
        try {
            totalCOAndCoal = yearDataSpvService.getTotalCOAndCoal(stion_id, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCOAndCoal;
    }

    //endregion

}

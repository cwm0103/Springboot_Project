package com.bom.domain.data.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.dao.OptStationCodesMapper;
import com.bom.domain.data.dao.StationCodesMapper;
import com.bom.domain.data.model.*;
import com.bom.domain.data.service.IDatDayDataService;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bom.domain.mix.model.ResultDay;
import com.bom.utils.DifferentDate;
import com.bom.utils.OpentsdbUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class DatDayDataServiceImp extends BaseService<DatDayData> implements IDatDayDataService {
    protected static Logger logger=  LoggerFactory.getLogger(DatDayDataServiceImp.class);
    @Autowired
    private com.bom.domain.data.dao.DatDayDataMapper DatDayDataMapper;
    @Autowired
    private com.bom.domain.data.dao.OptDataColMapper optDataColMapper;
    @Autowired
    private StationCodesMapper _stationCodesMapper;

    @Autowired
    private OptStationCodesMapper _optStationCodesMapper;

    @Autowired
    private com.bom.domain.mix.dao.ResultDayMapper resultDayMapper;
    @Value("${opentsdb.url}")
    private String url;

    @Override
    @TargetDataSource(name="datadb")
    public int save(@Param("entity") DatDayData entity)
    {
        return  DatDayDataMapper.insert(entity);
    }

    @TargetDataSource(name="datadb")
    public void  insertDayDataBatch(List<DatDayData> datDayDataList)
    {
          DatDayDataMapper.insertDayDataBatch(datDayDataList);
    }
    @TargetDataSource(name="datadb")
  public   List< DatDayData> GetDayData(String projectId,String code,String colTime)
    {
        return  DatDayDataMapper.GetDayData(projectId, code, colTime);
    }
    @TargetDataSource(name="datadb")
    public int updateByPrimaryKey(DatDayData datDayData)
    {
        return  DatDayDataMapper.updateByPrimaryKey(datDayData);
    }

    @TargetDataSource(name="datadb")
    public void createDayData(){
        String strbeginDate = DateTime.now().toString("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date beginDate = sdf.parse(strbeginDate);
            Date endDate = new Date();
            List<OptStationCodes> codes = _optStationCodesMapper.selectAll();
            Map<String, List<String>> stationCodeMap = new LinkedHashMap<>();
            for (OptStationCodes code : codes) {
                List<String> stationCodes = new LinkedList<>();
                if (!stationCodeMap.containsKey(code.getProjectId())) {
                    stationCodes.add(code.getCode());
                    stationCodeMap.put(code.getProjectId(), stationCodes);
                } else {
                    stationCodes = stationCodeMap.get(code.getProjectId());
                    stationCodes.add(code.getCode());
                    stationCodeMap.replace(code.getProjectId(), stationCodes);
                }
            }
            for (String key : stationCodeMap.keySet()) {
                String downsample = "1d-first";
                String codelist = String.join(",", stationCodeMap.get(key));
                String content = OpentsdbUtil.OpenTSDBQuery(beginDate, endDate, codelist, downsample, key, url);
                if (StringUtils.isBlank(content)) {
                    content = OpentsdbUtil.OpenTSDBQuery("1d-ago", Long.toString(endDate.getTime()), codelist, downsample, key, url);
                }
                if (StringUtils.isNotBlank(content)) {
                    List<TSDBResult> beginResultList = OpentsdbUtil.paseTSDBResult(content);
                    downsample = "1d-last";
                    content = OpentsdbUtil.OpenTSDBQuery(beginDate, endDate, codelist, downsample, key, url);
                    List<TSDBResult> endResultList = OpentsdbUtil.paseTSDBResult(content);
                    for (TSDBResult beginResult : beginResultList) {
                        TSDBResult endReslut = endResultList.stream().filter(x -> beginResult.getCode().equals(x.getCode()) && beginResult.getDate().equals(x.getDate())).findFirst().get();
                        List<DatDayData> datdayDatas = DatDayDataMapper.GetDayData(key, beginResult.getCode(), strbeginDate);
                        double curvalue = endReslut.getValue().subtract(beginResult.getValue()).doubleValue();
                        if (datdayDatas.size() > 0) {
                            DatDayData dayData = datdayDatas.get(0);
                            dayData.setCurValue(curvalue);
                            dayData.setSysTime(DateTime.now().toDate());
                            DatDayDataMapper.updateByPrimaryKey(dayData);
                        } else {
                            DatDayData dayData = new DatDayData();
                            dayData.setCurValue(curvalue);
                            dayData.setCode(beginResult.getCode());
                            dayData.setColTime(beginDate);
                            dayData.setSysTime(DateTime.now().toDate());
                            dayData.setProjectId(key);
                            DatDayDataMapper.insert(dayData);
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * 日数据组装
     * @param station_id
     * @param code
     * @param date
     * @param cur_value
     */
    @TargetDataSource(name="datadb")
    public void InsertOrUpdatedataSpv(String station_id, String code,Date date, double cur_value) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        ParsePosition pos=new ParsePosition(0);
        String beginDate=sdf.format(date);
        Date begintime= sdf.parse(beginDate,pos);
        try {

            DatDayData day=new DatDayData();
            day.setCode(code);
            day.setProjectId(station_id);
            day.setColTime(begintime);
            day.setSysTime(new Date());
            day.setCurValue(cur_value);


            Date col_time = null;
            Object obj= null;
            try {
//                obj = jdbcTemplate.queryForObject("select col_time from dat_day_data where project_id=? order by col_time desc LIMIT 1", new Object[]{station_id}, Date.class);
                obj=DatDayDataMapper.selectdaydataLast(station_id);
            } catch (DataAccessException e) {
                logger.info("日定时数据执行,查询数据库数据："+e.getMessage());
                e.printStackTrace();
            }

            if(obj!=null)
            {
                col_time=(Date)obj;
            }else
            {
                //插入一條數據
//                int update = jdbcTemplate.update("INSERT INTO dat_day_data VALUES(?, ?, ?, ?, ?, ?)", new Object[]{0, day.getProject_id(), day.getCode(), day.getCur_value(), day.getCol_time(), day.getSys_time()});
                int update=DatDayDataMapper.insertDatDayData(day);
                if (update > 0) {
                    InsertOrUpdateResultSpv(day, 1);
                    logger.info("日定时数据执行,插入成功");
                    System.out.println("插入日数据成功！");
                } else {
                    logger.info("日定时数据执行,插入失敗");
                    System.out.println("插入日数据失敗！");
                }

            }
            //查詢出來的數據跟當前傳過來的時間對比
            if (col_time != null) {
                Date newdate = DifferentDate.addonedate(col_time, 1);
                if (newdate.getTime() <= begintime.getTime()) {
                    //插入一條數據
//                    int update = jdbcTemplate.update("INSERT INTO dat_day_data VALUES(?, ?, ?, ?, ?, ?)", new Object[]{0, day.getProject_id(), day.getCode(), day.getCur_value(), day.getCol_time(), day.getSys_time()});
                    int update=DatDayDataMapper.insertDatDayData(day);
                    if (update > 0) {
                        InsertOrUpdateResultSpv(day, 1);
                        logger.info("日时定时数据执行,插入成功");
                        System.out.println("插入日数据成功！");
                    } else {
                        logger.info("日定时数据执行,插入失敗");
                        System.out.println("插入日数据失敗！");
                    }

                } else {
                    //修改一條數據
//                    int update = jdbcTemplate.update("UPDATE dat_day_data SET cur_value = ?,sys_time=? WHERE project_id = ? and col_time=?", new Object[]{cur_value, new Date(), station_id, beginDate});
                    int update =DatDayDataMapper.updateDatDayData(day);
                    if (update > 0) {
                        InsertOrUpdateResultSpv(day, 2);
                        logger.info("日定时数据执行,修改成功");
                        System.out.println("修改日数据成功！");
                    } else {
                        logger.info("日定时数据执行,修改失败");
                        System.out.println("修改日数据失败!");
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.info("日定时数据执行,数据执行失败："+e.getMessage());
            e.printStackTrace();
        }


    }


    /**
     * 插入或是更新二氧化碳、二氧化硫、 氮氧化物、煤数据
     * @param DatDayData
     * @param type
     */
    @TargetDataSource(name="datadb")
    private  void InsertOrUpdateResultSpv(DatDayData hour, Integer type)
    {
        if(type==1)//插入
        {
//            int insert1=jdbcTemplate.update("INSERT INTO result_day VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourCO2_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.997),0,""});
            ResultDay day=new ResultDay();
            day.setDataValue(hour.getCurValue()*0.997);
            day.setRecTime(hour.getColTime());
            day.setProjectId(hour.getProjectId());
            day.setCode("HourCO2_CER");
            int insert1=resultDayMapper.insertResultDay(day);
            logger.info("日定时数据执行插入二氧化碳,insert1:"+insert1);
            System.out.println("insert1:"+insert1);
//            int insert2=jdbcTemplate.update("INSERT INTO result_day VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourSO2_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.030),0,""});
            ResultDay   day2=new ResultDay();
            day2.setDataValue(hour.getCurValue()*0.030);
            day2.setRecTime(hour.getColTime());
            day2.setProjectId(hour.getProjectId());
            day2.setCode("HourSO2_CER");
            int insert2=resultDayMapper.insertResultDay(day2);
            logger.info("日定时数据执行二氧化硫,insert2:"+insert2);
            System.out.println("insert2:"+insert2);
//            int insert3=jdbcTemplate.update("INSERT INTO result_day VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourNOx_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.015),0,""});
            ResultDay   day3=new ResultDay();
            day3.setDataValue(hour.getCurValue()*0.015);
            day3.setRecTime(hour.getColTime());
            day3.setProjectId(hour.getProjectId());
            day3.setCode("HourNOx_CER");
            int insert3=resultDayMapper.insertResultDay(day3);
            logger.info("日定时数据执行氮氧化物,insert3:"+insert3);
            System.out.println("insert3:"+insert3);
//            int insert4=jdbcTemplate.update("INSERT INTO result_day VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourCoal_CER",hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.404),0,""});
            ResultDay   day4=new ResultDay();
            day4.setDataValue(hour.getCurValue()*0.404);
            day4.setRecTime(hour.getColTime());
            day4.setProjectId(hour.getProjectId());
            day4.setCode("HourCoal_CER");
            int insert4=resultDayMapper.insertResultDay(day4);
            logger.info("日定时数据执行煤,insert4:"+insert4);
            System.out.println("insert4:"+insert4);
        }else      //更新
        {
//            int update1=jdbcTemplate.update("UPDATE result_day SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.997), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourCO2_CER"});
            ResultDay   day=new ResultDay();
            day.setDataValue(hour.getCurValue()*0.997);

            day.setRecTime(hour.getColTime());
            day.setProjectId(hour.getProjectId());
            day.setCode("HourCO2_CER");
            int update1=resultDayMapper.updateResultDay(day);
            logger.info("日定时数据执行修改二氧化碳,update1:"+update1);
            System.out.println("update1:"+update1);
//            int update2=jdbcTemplate.update("UPDATE result_day SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.030), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourSO2_CER"});
            ResultDay   day2=new ResultDay();
            day2.setDataValue(hour.getCurValue()*0.030);
            day2.setRecTime(hour.getColTime());
            day2.setProjectId(hour.getProjectId());
            day2.setCode("HourSO2_CER");
            int update2=resultDayMapper.updateResultDay(day2);
            logger.info("日定时数据执行修改二氧化硫,update2:"+update2);
            System.out.println("update2:"+update2);
//            int update3=jdbcTemplate.update("UPDATE result_day SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.015), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourNOx_CER"});
            ResultDay   day3=new ResultDay();
            day3.setDataValue(hour.getCurValue()*0.015);
            day3.setRecTime(hour.getColTime());
            day3.setProjectId(hour.getProjectId());
            day3.setCode("HourNOx_CER");
            int update3=resultDayMapper.updateResultDay(day3);
            logger.info("日定时数据执行氮氧化物,update3:"+update3);
            System.out.println("update3:"+update3);
//            int update4=jdbcTemplate.update("UPDATE result_day SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.404), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourCoal_CER"});
            ResultDay   day4=new ResultDay();
            day4.setDataValue(hour.getCurValue()*0.404);
            day4.setRecTime(hour.getColTime());
            day4.setProjectId(hour.getProjectId());
            day4.setCode("HourCoal_CER");
            int update4=resultDayMapper.updateResultDay(day4);
            logger.info("日定时数据执行煤,update4:"+update4);
            System.out.println("update4:"+update4);
        }
    }
}
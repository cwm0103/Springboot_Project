package com.bom.domain.data.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.model.*;
import com.bom.domain.data.service.IDatMonthDataService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bom.domain.mix.model.ResultMonth;
import com.bom.utils.DealParamsUtil;
import com.bom.utils.DifferentDate;
import com.bom.utils.HttpClientHelper;
import com.bom.utils.OpentsdbUtil;
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
public class DatMonthDataServiceImp extends BaseService<DatMonthData> implements IDatMonthDataService {
    private static final Logger logger = LoggerFactory.getLogger(DatMonthDataServiceImp.class);
    @Autowired
    private com.bom.domain.data.dao.DatMonthDataMapper DatMonthDataMapper;
    @Autowired
    private com.bom.domain.data.dao.OptDataColMapper optDataColMapper ;
    @Autowired
    private com.bom.domain.data.dao.OptStationCodesMapper stationCodesMapper ;
    @Autowired
    private com.bom.domain.data.dao.DatDayDataMapper datDayDataMapper ;
    @Autowired
    private com.bom.domain.mix.dao.ResultMonthMapper resultMonthMapper;
    @Value("${opentsdb.url}")
    private String url;

    @Override
    @TargetDataSource(name="datadb")
    public void handlerMonth() {
        // 使用calendar类，得到当前时间
//        Calendar now = Calendar.getInstance();
//        // 设置时间(result_hour)
//        now.add(Calendar.MINUTE, -5);
        logger.info("月数据提取定时开始。。。");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-01 00:00:00") ;
        String firstDayOfMonth = sdf2.format(new Date()) ;
        String current = sdf.format(new Date()) ;
        try {
           System.out.println("当前时间："+current+"  月初日期："+firstDayOfMonth);
           List<OptStationCodes> codes = stationCodesMapper.selectAll();
           for (OptStationCodes code : codes) {
               System.out.println(code.getProjectId()+" "+code.getCode()) ;
               BigDecimal curvalue = datDayDataMapper.calSum(code.getProjectId(),code.getCode(),firstDayOfMonth,current) ;
               DatMonthData datMonthData = DatMonthDataMapper.findMonthStartData(code.getProjectId(), code.getCode(), firstDayOfMonth);
               if (datMonthData != null) {
                   datMonthData.setCurValue(curvalue==null?0:curvalue.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
                   datMonthData.setSysTime(DateTime.now().toDate());
                   DatMonthDataMapper.updateByPrimaryKey(datMonthData);
               } else {
                   DatMonthData monthData  = new DatMonthData();
                   monthData.setCurValue(curvalue==null?0:curvalue.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
                   monthData.setCode(code.getCode());
                   monthData.setColTime(sdf.parse(firstDayOfMonth));
                   monthData.setSysTime(DateTime.now().toDate());
                   monthData.setProjectId(code.getProjectId());
                   DatMonthDataMapper.insert(monthData);
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("出现异常。");
        }
        logger.info("月数据提取定时结束。。。");
    }

    /**
     *
     * @param station_id
     * @param code
     * @param beginDate
     * @param endDate
     */
    @Override
    @TargetDataSource(name="datadb")
    public void InsertOrUpdatedataSpv(String station_id, String code, String beginDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        ParsePosition pos=new ParsePosition(0);
        Date begintime= sdf.parse(beginDate,pos);

//        Double cur_value=jdbcTemplate.queryForObject("select sum(cur_value) from dat_day_data where project_id=? and col_time between ? and ?", new Object[]{station_id,beginDate,endDate}, Double.class);

        Object cur_value= null;
        try {
            cur_value = datDayDataMapper.selectdayTotal(station_id,beginDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(cur_value!=null)
            {

                DatMonthData month = new DatMonthData();
                month.setCode(code);
                month.setProjectId(station_id);
                month.setColTime(begintime);
                month.setSysTime(new Date());
                month.setCurValue(Double.parseDouble(cur_value.toString()));


                Date col_time = null;
                Object obj=null;
                try {
//                    obj = jdbcTemplate.queryForObject("select col_time from dat_month_data where project_id=? order by col_time desc LIMIT 1", new Object[]{station_id}, Date.class);
                    obj=DatMonthDataMapper.selectDatMonthDataLast(station_id);
                } catch (DataAccessException e) {
                    logger.info("月定时数据执行,查询数据库数据："+e.getMessage());
                    e.printStackTrace();
                }
                if(obj!=null)
                {
                    col_time=(Date)obj;
                }else
                {
                    //插入一條數據
//                    int update = jdbcTemplate.update("INSERT INTO dat_month_data VALUES(?, ?, ?, ?, ?, ?)", new Object[]{0, month.getProject_id(), month.getCode(), month.getCur_value(), month.getCol_time(), month.getSys_time()});
                    int update=DatMonthDataMapper.insertDataMonthData(month);
                    if (update > 0) {
                        InsertOrUpdateResultSpv(month, 1);
                        logger.info("月定时数据执行,插入成功");
                        System.out.println("插入月数据成功！");
                    } else {
                        logger.info("月定时数据执行,插入失敗");
                        System.out.println("插入月数据失敗！");
                    }
                }

                //查詢出來的數據跟當前傳過來的時間對比
                if (col_time != null) {
                    Date newdate = DifferentDate.addonemonth(col_time, 1);
                    if (newdate.getTime() <= begintime.getTime()) {
                        //插入一條數據
//                        int update = jdbcTemplate.update("INSERT INTO dat_month_data VALUES(?, ?, ?, ?, ?, ?)", new Object[]{0, month.getProject_id(), month.getCode(), month.getCur_value(), month.getCol_time(), month.getSys_time()});
                        int update=DatMonthDataMapper.insertDataMonthData(month);
                        if (update > 0) {
                            InsertOrUpdateResultSpv(month, 1);
                            logger.info("月定时数据执行,插入成功");
                            System.out.println("插入月数据成功！");
                        } else {
                            logger.info("月定时数据执行,插入失敗");
                            System.out.println("插入月数据失敗！");
                        }

                    } else {
                        //修改一條數據
//                        int update = jdbcTemplate.update("UPDATE dat_month_data SET cur_value = ?,sys_time=? WHERE project_id = ? and col_time=?", new Object[]{cur_value, new Date(), station_id, beginDate});
                        int update=DatMonthDataMapper.updateDataMonthData(month);
                        if (update > 0) {
                            InsertOrUpdateResultSpv(month, 2);
                            logger.info("月定时数据执行,修改成功");
                            System.out.println("修改月数据成功！");
                        } else {
                            logger.info("月定时数据执行,修改失败");
                            System.out.println("修改月数据失败!");
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.info("月定时数据执行,数据执行失败："+e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * 插入或是更新二氧化碳、二氧化硫、 氮氧化物、煤数据
     * @param DatMonthData
     * @param type
     */
    @TargetDataSource(name="datadb")
    private  void InsertOrUpdateResultSpv(DatMonthData hour, Integer type)
    {
        if(type==1)//插入
        {

//            int insert1=jdbcTemplate.update("INSERT INTO result_month VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourCO2_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.997),0,""});
            ResultMonth rm=new ResultMonth();
            rm.setProjectId(hour.getProjectId());
            rm.setRecTime(hour.getColTime());
            rm.setCode("HourCO2_CER");
            rm.setDataValue(hour.getCurValue()*0.997);
            rm.setState(0);
            rm.setRemarks("");
            int insert1=resultMonthMapper.insertResultMonth(rm);
            logger.info("月定时数据执行插入二氧化碳,insert1:"+insert1);
            System.out.println("insert1:"+insert1);
//            int insert2=jdbcTemplate.update("INSERT INTO result_month VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourSO2_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.030),0,""});
            ResultMonth rm2=new ResultMonth();
            rm2.setProjectId(hour.getProjectId());
            rm2.setRecTime(hour.getColTime());
            rm2.setCode("HourSO2_CER");
            rm2.setDataValue(hour.getCurValue()*0.030);
            rm2.setState(0);
            rm.setRemarks("");
            int insert2=resultMonthMapper.insertResultMonth(rm2);
            logger.info("月定时数据执行二氧化硫,insert2:"+insert2);
            System.out.println("insert2:"+insert2);
//            int insert3=jdbcTemplate.update("INSERT INTO result_month VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourNOx_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.015),0,""});

            ResultMonth rm3=new ResultMonth();
            rm3.setProjectId(hour.getProjectId());
            rm3.setRecTime(hour.getColTime());
            rm3.setCode("HourNOx_CER");
            rm3.setDataValue(hour.getCurValue()*0.015);
            rm3.setState(0);
            rm3.setRemarks("");
            int insert3=resultMonthMapper.insertResultMonth(rm3);
            logger.info("月定时数据执行氮氧化物,insert3:"+insert3);
            System.out.println("insert3:"+insert3);
//            int insert4=jdbcTemplate.update("INSERT INTO result_month VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourCoal_CER",hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.404),0,""});
            ResultMonth rm4=new ResultMonth();
            rm4.setProjectId(hour.getProjectId());
            rm4.setRecTime(hour.getColTime());
            rm4.setCode("HourCoal_CER");
            rm4.setDataValue(hour.getCurValue()*0.404);
            rm4.setState(0);
            rm4.setRemarks("");
            int insert4=resultMonthMapper.insertResultMonth(rm4);
            logger.info("月定时数据执行煤,insert4:"+insert4);
            System.out.println("insert4:"+insert4);
        }else      //更新
        {
//            int update1=jdbcTemplate.update("UPDATE result_month SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.997), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourCO2_CER"});
            ResultMonth rm=new ResultMonth();
            rm.setProjectId(hour.getProjectId());
            rm.setRecTime(hour.getColTime());
            rm.setCode("HourCO2_CER");
            rm.setDataValue(hour.getCurValue()*0.997);
            rm.setState(0);
            rm.setRemarks("");
            int update1=resultMonthMapper.updateResultMonth(rm);
            logger.info("月定时数据执行修改二氧化碳,update1:"+update1);
            System.out.println("update1:"+update1);
//            int update2=jdbcTemplate.update("UPDATE result_month SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.030), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourSO2_CER"});
            ResultMonth rm2=new ResultMonth();
            rm2.setProjectId(hour.getProjectId());
            rm2.setRecTime(hour.getColTime());
            rm2.setCode("HourSO2_CER");
            rm2.setDataValue(hour.getCurValue()*0.030);
            rm2.setState(0);
            rm2.setRemarks("");
            int update2=resultMonthMapper.updateResultMonth(rm2);
            logger.info("月定时数据执行修改二氧化硫,update2:"+update2);
            System.out.println("update2:"+update2);
//            int update3=jdbcTemplate.update("UPDATE result_month SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.015), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourNOx_CER"});
            ResultMonth rm3=new ResultMonth();
            rm3.setProjectId(hour.getProjectId());
            rm3.setRecTime(hour.getColTime());
            rm3.setCode("HourNOx_CER");
            rm3.setDataValue(hour.getCurValue()*0.015);
            rm3.setState(0);
            rm3.setRemarks("");
            int update3=resultMonthMapper.updateResultMonth(rm3);
            logger.info("月定时数据执行氮氧化物,update3:"+update3);
            System.out.println("update3:"+update3);
//            int update4=jdbcTemplate.update("UPDATE result_month SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.404), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourCoal_CER"});
            ResultMonth rm4=new ResultMonth();
            rm4.setProjectId(hour.getProjectId());
            rm4.setRecTime(hour.getColTime());
            rm4.setCode("HourCoal_CER");
            rm4.setDataValue(hour.getCurValue()*0.404);
            rm4.setState(0);
            rm4.setRemarks("");
            int update4=resultMonthMapper.updateResultMonth(rm4);
            logger.info("月定时数据执行煤,update4:"+update4);
            System.out.println("update4:"+update4);
        }
    }
}
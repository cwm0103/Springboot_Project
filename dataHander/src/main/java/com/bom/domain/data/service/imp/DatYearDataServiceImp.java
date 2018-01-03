package com.bom.domain.data.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.dao.DatMonthDataMapper;
import com.bom.domain.data.model.*;
import com.bom.domain.data.service.IDatYearDataService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bom.domain.mix.model.ResultYear;
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
public class DatYearDataServiceImp extends BaseService<DatYearData> implements IDatYearDataService {
    private static final Logger logger = LoggerFactory.getLogger(DatYearDataServiceImp.class);
    @Autowired
    private com.bom.domain.data.dao.DatYearDataMapper DatYearDataMapper;
    @Autowired
    private com.bom.domain.data.dao.DatMonthDataMapper datMonthDataMapper ;
    @Autowired
//    private com.bom.domain.data.dao.StationCodesMapper stationCodesMapper ;
    private com.bom.domain.data.dao.OptStationCodesMapper stationCodesMapper ;
    @Autowired
    private  com.bom.domain.mix.dao.ResultYearMapper resultYearMapper;
    @Value("${opentsdb.url}")
    private String url;

    @Override
    @TargetDataSource(name="datadb")
    public void handlerYear() {
        logger.info("年数据提取定时执行，每一小时提取一次，开始。") ;
//        // 使用calendar类，得到当前时间
//        Calendar now = Calendar.getInstance();
//        // 设置时间(result_hour)
//        now.add(Calendar.MINUTE, -5);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-01-01 00:00:00") ;
        String firstDayOfYear = sdf2.format(new Date()) ;
        String now = sdf.format(new Date()) ;
        try{
//            String beginDayOfYear = sdf.parse(firstDayOfYear).getTime()+"" ;
            System.out.println("当前时间："+now+"  年初日期："+firstDayOfYear);
            List<OptStationCodes> codes = stationCodesMapper.selectAll();
            System.out.println() ;
            for (OptStationCodes code : codes) {
                System.out.println(code.getProjectId()+"  "+code.getCode()) ;
                BigDecimal curvalue = datMonthDataMapper.calSum(code.getProjectId(),code.getCode(),firstDayOfYear,now) ;
                DatYearData datYearData = DatYearDataMapper.findYearStartData(code.getProjectId(), code.getCode(), firstDayOfYear);
                if (datYearData != null) {
                    datYearData.setCurValue(curvalue==null?0:curvalue.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
                    datYearData.setSysTime(DateTime.now().toDate());
                    DatYearDataMapper.updateByPrimaryKey(datYearData);
                } else {
                    DatYearData yearData  = new DatYearData();
                    yearData.setCurValue(curvalue==null?0:curvalue.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
                    yearData.setCode(code.getCode());
                    yearData.setColTime(sdf.parse(firstDayOfYear));
                    yearData.setSysTime(DateTime.now().toDate());
                    yearData.setProjectId(code.getProjectId());
                    DatYearDataMapper.insert(yearData);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("年数据提取定时执行，每一小时提取一次，完毕。") ;
    }

    @Override
    @TargetDataSource(name="datadb")
    public void InsertOrUpdateHourdataSpv(String station_id, String code, String beginDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        ParsePosition pos=new ParsePosition(0);
        Date begintime= sdf.parse(beginDate,pos);
//        Double cur_value=jdbcTemplate.queryForObject("select sum(cur_value) from dat_month_data where project_id=? and col_time between ? and ?", new Object[]{station_id,beginDate,endDate}, Double.class);

        Object cur_value= null;
        try {
            cur_value = datMonthDataMapper.selectMonthTotal(station_id,beginDate,endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(cur_value!=null)
            {
                DatYearData year = new DatYearData();
                year.setCode(code);
                year.setProjectId(station_id);
                year.setColTime(begintime);
                year.setSysTime(new Date());
                year.setCurValue(Double.parseDouble(cur_value.toString()));

                Date col_time = null;
                Object obj=null;
                try {
//                    obj = jdbcTemplate.queryForObject("select col_time from dat_year_data where project_id=? order by col_time desc LIMIT 1", new Object[]{station_id}, Date.class);
                    obj = DatYearDataMapper.selectDatYearDataLast(station_id);
                } catch (DataAccessException e) {
                    logger.info("年定时数据执行,查询数据库数据："+e.getMessage());
                    e.printStackTrace();
                }

                if(obj!=null)
                {
                    col_time=(Date)obj;
                }else
                {
                    //插入一條數據
//                    int update = jdbcTemplate.update("INSERT INTO dat_year_data VALUES(?, ?, ?, ?, ?, ?)", new Object[]{0, year.getProject_id(), year.getCode(), year.getCur_value(), year.getCol_time(), year.getSys_time()});
                    int update=DatYearDataMapper.insertDataYearData(year);
                    if (update > 0) {

                        InsertOrUpdateResultSpv(year, 1);
                        logger.info("年定时数据执行,插入成功");
                        System.out.println("插入月数据成功！");
                    } else {
                        logger.info("年定时数据执行,插入失敗");
                        System.out.println("插入月数据失敗！");
                    }
                }

                //查詢出來的數據跟當前傳過來的時間對比
                if (col_time != null) {
                    Date newdate = DifferentDate.addoneyear(col_time, 1);
                    if (newdate.getTime() <= begintime.getTime()) {
                        //插入一條數據
//                        int update = jdbcTemplate.update("INSERT INTO dat_year_data VALUES(?, ?, ?, ?, ?, ?)", new Object[]{0, year.getProject_id(), year.getCode(), year.getCur_value(), year.getCol_time(), year.getSys_time()});
                        int update=DatYearDataMapper.insertDataYearData(year);
                        if (update > 0) {

                            InsertOrUpdateResultSpv(year, 1);
                            logger.info("年定时数据执行,插入成功");
                            System.out.println("插入月数据成功！");
                        } else {
                            logger.info("年定时数据执行,插入失敗");
                            System.out.println("插入月数据失敗！");
                        }

                    } else {
                        //修改一條數據
//                        int update = jdbcTemplate.update("UPDATE dat_year_data SET cur_value = ?,sys_time=? WHERE project_id = ? and col_time=?", new Object[]{cur_value, new Date(), station_id, beginDate});
                        int update=DatYearDataMapper.updateDataYearData(year);
                        if (update > 0) {
                            InsertOrUpdateResultSpv(year, 2);
                            logger.info("年定时数据执行,修改成功");
                            System.out.println("修改月数据成功！");
                        } else {
                            logger.info("年定时数据执行,修改失败");
                            System.out.println("修改月数据失败!");
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.info("年定时数据执行,数据执行失败："+e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 插入或是更新二氧化碳、二氧化硫、 氮氧化物、煤数据
     * @param DatYearData
     * @param type
     */
    @TargetDataSource(name="datadb")
    private  void InsertOrUpdateResultSpv(DatYearData hour, Integer type)
    {
        if(type==1)//插入
        {
//            int insert1=jdbcTemplate.update("INSERT INTO result_year VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourCO2_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.997),0,""});
            ResultYear ry=new ResultYear();
            ry.setProjectId(hour.getProjectId());
            ry.setCode("HourCO2_CER");
            ry.setDataValue(hour.getCurValue()*0.997);
            ry.setRecTime(hour.getColTime());
            ry.setRemarks("");
            ry.setState(0);
            int insert1=resultYearMapper.insertResultYear(ry);
            logger.info("年定时数据执行插入二氧化碳,insert1:"+insert1);
            System.out.println("insert1:"+insert1);
//            int insert2=jdbcTemplate.update("INSERT INTO result_year VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourSO2_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.030),0,""});
            ResultYear ry2=new ResultYear();
            ry2.setProjectId(hour.getProjectId());
            ry2.setCode("HourSO2_CER");
            ry2.setDataValue(hour.getCurValue()*0.030);
            ry2.setRecTime(hour.getColTime());
            ry2.setRemarks("");
            ry2.setState(0);
            int insert2=resultYearMapper.insertResultYear(ry2);
            logger.info("年定时数据执行二氧化硫,insert2:"+insert2);
            System.out.println("insert2:"+insert2);
//            int insert3=jdbcTemplate.update("INSERT INTO result_year VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourNOx_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.015),0,""});
            ResultYear ry3=new ResultYear();
            ry3.setProjectId(hour.getProjectId());
            ry3.setCode("HourNOx_CER");
            ry3.setDataValue(hour.getCurValue()*0.015);
            ry3.setRecTime(hour.getColTime());
            ry3.setRemarks("");
            ry3.setState(0);
            int insert3=resultYearMapper.insertResultYear(ry3);
            logger.info("年定时数据执行氮氧化物,insert3:"+insert3);
            System.out.println("insert3:"+insert3);
//            int insert4=jdbcTemplate.update("INSERT INTO result_year VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourCoal_CER",hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.404),0,""});
            ResultYear ry4=new ResultYear();
            ry4.setProjectId(hour.getProjectId());
            ry4.setCode("HourCoal_CER");
            ry4.setDataValue(hour.getCurValue()*0.404);
            ry4.setRecTime(hour.getColTime());
            ry4.setRemarks("");
            ry4.setState(0);
            int insert4=resultYearMapper.insertResultYear(ry4);
            logger.info("年定时数据执行煤,insert4:"+insert4);
            System.out.println("insert4:"+insert4);
        }else      //更新
        {
//            int update1=jdbcTemplate.update("UPDATE result_year SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.997), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourCO2_CER"});
            ResultYear ry=new ResultYear();
            ry.setProjectId(hour.getProjectId());
            ry.setCode("HourCO2_CER");
            ry.setDataValue(hour.getCurValue()*0.997);
            ry.setRecTime(hour.getColTime());
            ry.setRemarks("");
            ry.setState(0);
            int update1=resultYearMapper.updateResultYear(ry);
            logger.info("年定时数据执行修改二氧化碳,update1:"+update1);
            System.out.println("update1:"+update1);
//            int update2=jdbcTemplate.update("UPDATE result_year SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.030), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourSO2_CER"});
            ResultYear ry2=new ResultYear();
            ry2.setProjectId(hour.getProjectId());
            ry2.setCode("HourSO2_CER");
            ry2.setDataValue(hour.getCurValue()*0.030);
            ry2.setRecTime(hour.getColTime());
            ry2.setRemarks("");
            ry2.setState(0);
            int update2=resultYearMapper.updateResultYear(ry2);
            logger.info("年定时数据执行修改二氧化硫,update2:"+update2);
            System.out.println("update2:"+update2);
//            int update3=jdbcTemplate.update("UPDATE result_year SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.015), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourNOx_CER"});
            ResultYear ry3=new ResultYear();
            ry3.setProjectId(hour.getProjectId());
            ry3.setCode("HourNOx_CER");
            ry3.setDataValue(hour.getCurValue()*0.015);
            ry3.setRecTime(hour.getColTime());
            ry3.setRemarks("");
            ry3.setState(0);
            int update3=resultYearMapper.updateResultYear(ry3);
            logger.info("年定时数据执行氮氧化物,update3:"+update3);
            System.out.println("update3:"+update3);
//            int update4=jdbcTemplate.update("UPDATE result_year SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.404), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourCoal_CER"});
            ResultYear ry4=new ResultYear();
            ry4.setProjectId(hour.getProjectId());
            ry4.setCode("HourCoal_CER");
            ry4.setDataValue(hour.getCurValue()*0.404);
            ry4.setRecTime(hour.getColTime());
            ry4.setRemarks("");
            ry4.setState(0);
            int update4=resultYearMapper.updateResultYear(ry4);
            logger.info("年定时数据执行煤,update4:"+update4);
            System.out.println("update4:"+update4);
        }
    }
}
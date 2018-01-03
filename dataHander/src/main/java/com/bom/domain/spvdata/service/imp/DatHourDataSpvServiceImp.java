package com.bom.domain.spvdata.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.spvdata.model.DatHourDataSpv;
import com.bom.domain.spvdata.model.ResultHourSpv;
import com.bom.domain.spvdata.service.IDatHourDataSpvService;

import java.util.Date;
import java.util.List;

import com.bom.utils.DifferentDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class DatHourDataSpvServiceImp extends BaseService<DatHourDataSpv> implements IDatHourDataSpvService {
    protected static Logger logger=  LoggerFactory.getLogger(DatHourDataSpv.class);
    @Autowired
    private com.bom.domain.spvdata.dao.DatHourDataSpvMapper DatHourDataSpvMapper;
    @Autowired
    private com.bom.domain.spvdata.dao.ResultHourSpvMapper  resultHourSpvMapper;
    @Override
    @TargetDataSource(name="datadb")
    public void InsertOrUpdatedataSpv(String station_id, String code, Date date, Double cur_value) {

        //查詢最後一條的日期
        //String sql="select col_time from dat_hour_data_spv where project_id=? order by col_time desc LIMIT 1";
        Date col_time=null;

        Object obj= null;
        try {
//            obj = jdbcTemplate.queryForObject("select col_time from dat_hour_data_spv where project_id=? and code=? order by col_time desc LIMIT 1",new Object[]{station_id,code}, Date.class);
            obj=DatHourDataSpvMapper.selectHourdataLast(station_id,code);
        } catch (DataAccessException e) {
            logger.info("小时定时数据执行,查询数据库数据："+e.getMessage());
            e.printStackTrace();
        }

        if(obj!=null)
        {
            col_time = (Date)obj;
        }else
        {
            //如果为空位插入
            DatHourDataSpv datHourDataSpv=new DatHourDataSpv();
            datHourDataSpv.setProjectId(station_id);
            datHourDataSpv.setCode(code);
            datHourDataSpv.setColTime(date);
            datHourDataSpv.setCurValue(cur_value);
            datHourDataSpv.setSysTime(new Date());
            //插入一條數據
//            int update=jdbcTemplate.update("INSERT INTO dat_hour_data_spv VALUES(?, ?, ?, ?, ?, ?)", new Object[] {0, hour.getProject_id(),hour.getCode(), hour.getCur_value(),hour.getCol_time(),hour.getSys_time()});
            int update=DatHourDataSpvMapper.insertHourdata(datHourDataSpv);
            if(update>0)
            {
                InsertOrUpdateResultSpv(datHourDataSpv,1);
                logger.info("小时定时数据执行,插入成功");
                System.out.println("插入成功！");
            }else
            {
                logger.info("小时定时数据执行,插入失敗");
                System.out.println("插入失敗！");
            }


        }
        //Date col_time = (Date) jdbcTemplate.queryForObject("select col_time from dat_hour_data_spv where project_id=? order by col_time desc LIMIT 1",new Object[]{station_id}, Date.class);
        //查詢出來的數據跟當前傳過來的時間對比

        try {
            if(col_time!=null)
            {
                DatHourDataSpv datHourDataSpv=new DatHourDataSpv();
                datHourDataSpv.setProjectId(station_id);
                datHourDataSpv.setCode(code);
                datHourDataSpv.setColTime(date);
                datHourDataSpv.setCurValue(cur_value);
                datHourDataSpv.setSysTime(new Date());


                Date newdate=  DifferentDate.addonetime(col_time,1);
                if(newdate.getTime() <= date.getTime())
                {
                    //插入一條數據
//                    int update=jdbcTemplate.update("INSERT INTO dat_hour_data_spv VALUES(?, ?, ?, ?, ?, ?)", new Object[] {0, hour.getProject_id(),hour.getCode(), hour.getCur_value(),hour.getCol_time(),hour.getSys_time()});
                    int update=DatHourDataSpvMapper.insertHourdata(datHourDataSpv);
                    if(update>0)
                    {
                        InsertOrUpdateResultSpv(datHourDataSpv,1);
                        logger.info("小时定时数据执行,插入成功");
                        System.out.println("插入成功！");
                    }else
                    {
                        logger.info("小时定时数据执行,插入失敗");
                        System.out.println("插入失敗！");
                    }

                }else
                {
                    //修改一條數據
                    //int update=jdbcTemplate.update("UPDATE dat_hour_data_spv SET cur_value = ?,sys_time=? WHERE project_id = ? and col_time=? and code=?", new Object[] {cur_value, new Date(),station_id,date,code});
                    int update=DatHourDataSpvMapper.updateHourdata(datHourDataSpv);
                    if(update>0)
                    {
                        InsertOrUpdateResultSpv(datHourDataSpv,2);
                        logger.info("小时定时数据执行,修改成功");
                        System.out.println("修改成功！");
                    }else
                    {
                        logger.info("小时定时数据执行,修改失败");
                        System.out.println("修改失败!");
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.info("小时定时数据执行,数据执行失败："+e.getMessage());
            e.printStackTrace();
        }

//        HourdataSpv spv= (HourdataSpv)jdbcTemplate.query(sql,new RowMapper<HourdataSpv>(){
//            @Override
//            public HourdataSpv mapRow(ResultSet rs, int rowNum) throws SQLException {
//                HourdataSpv hourdataSpv =new HourdataSpv();
//                hourdataSpv.setId(rs.getInt("id"));
//                hourdataSpv.setCode(rs.getString("code"));
//                hourdataSpv.setCol_time(rs.getDate("col_time"));
//                hourdataSpv.setCur_value(rs.getDouble("cur_value"));
//                hourdataSpv.setSys_time(rs.getDate("sys_time"));
//                hourdataSpv.setProject_id(rs.getString("project_id"));
//                return hourdataSpv;
//            }
//        });




    }

    @Override
    @TargetDataSource(name="datadb")
    public void InsertOrUpdatedataFuZhaoJSGYSpv(String station_id, String param_Name, Date date, Double cur_value) {
        //查詢最後一條的日期
        Date col_time=null;
        Object obj= null;
        try {
            //obj = jdbcTemplate.queryForObject("select col_time from dat_hour_data_spv where project_id=? and code=? order by col_time desc LIMIT 1",new Object[]{station_id,code}, Date.class);
            obj=DatHourDataSpvMapper.selectHourdataLast(station_id,param_Name);
        } catch (DataAccessException e) {
            logger.info("小时定时数据执行,查询数据库数据："+e.getMessage());
            e.printStackTrace();
        }

        if(obj!=null)
        {
            col_time = (Date)obj;
        }else
        {
            //如果为空位插入

            DatHourDataSpv datHourDataSpv=new DatHourDataSpv();
            datHourDataSpv.setProjectId(station_id);
            datHourDataSpv.setCode(param_Name);
            datHourDataSpv.setColTime(date);
            datHourDataSpv.setCurValue(cur_value);
            datHourDataSpv.setSysTime(new Date());
            //插入一條數據
//            int update=jdbcTemplate.update("INSERT INTO dat_hour_data_spv VALUES(?, ?, ?, ?, ?, ?)", new Object[] {0, hour.getProject_id(),hour.getCode(), hour.getCur_value(),hour.getCol_time(),hour.getSys_time()});
            int update=DatHourDataSpvMapper.insertHourdata(datHourDataSpv);
            if(update>0)
            {
                //InsertOrUpdateResultSpv(hour,1);
                logger.info("小时定时数据执行,插入成功:"+param_Name);
                System.out.println("插入成功！");
            }else
            {
                logger.info("小时定时数据执行,插入失敗:"+param_Name);
                System.out.println("插入失敗！");
            }


        }
        //查詢出來的數據跟當前傳過來的時間對比

        try {
            if(col_time!=null)
            {
                DatHourDataSpv datHourDataSpv=new DatHourDataSpv();
                datHourDataSpv.setProjectId(station_id);
                datHourDataSpv.setCode(param_Name);
                datHourDataSpv.setColTime(date);
                datHourDataSpv.setCurValue(cur_value);
                datHourDataSpv.setSysTime(new Date());

                Date newdate=  DifferentDate.addonetime(col_time,1);
                if(newdate.getTime() <= date.getTime())
                {
                    //插入一條數據
//                    int update=jdbcTemplate.update("INSERT INTO dat_hour_data_spv VALUES(?, ?, ?, ?, ?, ?)", new Object[] {0, hour.getProject_id(),hour.getCode(), hour.getCur_value(),hour.getCol_time(),hour.getSys_time()});
                    int update=DatHourDataSpvMapper.insertHourdata(datHourDataSpv);
                    if(update>0)
                    {
                        //InsertOrUpdateResultSpv(hour,1);
                        logger.info("小时定时数据执行,插入成功:"+param_Name);
                        System.out.println("插入成功！");
                    }else
                    {
                        logger.info("小时定时数据执行,插入失敗");
                        System.out.println("插入失敗！");
                    }

                }else
                {
                    //修改一條數據
                    //int update=jdbcTemplate.update("UPDATE dat_hour_data_spv SET cur_value = ?,sys_time=? WHERE project_id = ? and col_time=? and code=?", new Object[] {cur_value, new Date(),station_id,date,code});
                    int update=DatHourDataSpvMapper.updateHourdata(datHourDataSpv);
                    if(update>0)
                    {
                        //InsertOrUpdateResultSpv(hour,2);
                        logger.info("小时定时数据执行,修改成功："+param_Name);
                        System.out.println("修改成功！");
                    }else
                    {
                        logger.info("小时定时数据执行,修改失败");
                        System.out.println("修改失败!");
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.info("小时定时数据执行,数据执行失败："+e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * 插入或是更新二氧化碳、二氧化硫、 氮氧化物、煤数据
     * @param hour
     * @param type
     */
    @TargetDataSource(name="datadb")
    private  void InsertOrUpdateResultSpv(DatHourDataSpv hour,Integer type)
    {
        if(type==1)//插入
        {
//            int insert1=jdbcTemplate.update("INSERT INTO result_hour_spv VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourCO2_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.997),0,""});
            ResultHourSpv rhs=new ResultHourSpv();
            rhs.setCode("HourCO2_CER");
            rhs.setSysTime(new Date());
            rhs.setDataValue(hour.getCurValue()*0.997);
            rhs.setProjectId(hour.getProjectId());
            rhs.setRecTime(hour.getColTime());
            rhs.setRemarks("");
            rhs.setState(0);
            int insert1=resultHourSpvMapper.insertResultHour(rhs);
            logger.info("小时定时数据执行插入二氧化碳,insert1:"+insert1);
            System.out.println("insert1:"+insert1);
//            int insert2=jdbcTemplate.update("INSERT INTO result_hour_spv VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourSO2_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.030),0,""});

            ResultHourSpv rhs2=new ResultHourSpv();
            rhs2.setCode("HourSO2_CER");
            rhs2.setSysTime(new Date());
            rhs2.setDataValue(hour.getCurValue()*0.030);
            rhs2.setProjectId(hour.getProjectId());
            rhs2.setRecTime(hour.getColTime());
            rhs2.setRemarks("");
            rhs2.setState(0);
            int insert2=resultHourSpvMapper.insertResultHour(rhs2);
            logger.info("小时定时数据执行二氧化硫,insert2:"+insert2);
            System.out.println("insert2:"+insert2);
            //int insert3=jdbcTemplate.update("INSERT INTO result_hour_spv VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourNOx_CER", hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.015),0,""});
            ResultHourSpv rhs3=new ResultHourSpv();
            rhs3.setCode("HourNOx_CER");
            rhs3.setSysTime(new Date());
            rhs3.setDataValue(hour.getCurValue()*0.015);
            rhs3.setProjectId(hour.getProjectId());
            rhs3.setRecTime(hour.getColTime());
            rhs3.setRemarks("");
            rhs3.setState(0);
            int insert3=resultHourSpvMapper.insertResultHour(rhs3);
            logger.info("小时定时数据执行氮氧化物,insert3:"+insert3);
            System.out.println("insert3:"+insert3);
            //int insert4=jdbcTemplate.update("INSERT INTO result_hour_spv VALUES(?, ?, ?, ?, ?, ?,?,?)", new Object[] {0, hour.getProject_id(),"HourCoal_CER",hour.getCol_time(),hour.getSys_time(),(hour.getCur_value()*0.404),0,""});

            ResultHourSpv rhs4=new ResultHourSpv();
            rhs4.setCode("HourCoal_CER");
            rhs4.setSysTime(new Date());
            rhs4.setDataValue(hour.getCurValue()*0.404);
            rhs4.setProjectId(hour.getProjectId());
            rhs4.setRecTime(hour.getColTime());
            rhs4.setRemarks("");
            rhs4.setState(0);
            int insert4=resultHourSpvMapper.insertResultHour(rhs4);
            logger.info("小时定时数据执行煤,insert4:"+insert4);
            System.out.println("insert4:"+insert4);
        }else      //更新
        {
//            int update1=jdbcTemplate.update("UPDATE result_hour_spv SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.997), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourCO2_CER"});
            ResultHourSpv rhs=new ResultHourSpv();
            rhs.setCode("HourCO2_CER");
            rhs.setSysTime(new Date());
            rhs.setDataValue(hour.getCurValue()*0.997);
            rhs.setProjectId(hour.getProjectId());
            rhs.setRecTime(hour.getColTime());
            rhs.setRemarks("");
            rhs.setState(0);
            int update1=resultHourSpvMapper.updateResultHour(rhs);
            logger.info("小时定时数据执行修改二氧化碳,update1:"+update1);
            System.out.println("update1:"+update1);
            //int update2=jdbcTemplate.update("UPDATE result_hour_spv SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.030), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourSO2_CER"});

            ResultHourSpv rhs2=new ResultHourSpv();
            rhs2.setCode("HourSO2_CER");
            rhs2.setSysTime(new Date());
            rhs2.setDataValue(hour.getCurValue()*0.030);
            rhs2.setProjectId(hour.getProjectId());
            rhs2.setRecTime(hour.getColTime());
            rhs2.setRemarks("");
            rhs2.setState(0);
            int update2=resultHourSpvMapper.updateResultHour(rhs2);
            logger.info("小时定时数据执行修改二氧化硫,update2:"+update2);
            System.out.println("update2:"+update2);
            //int update3=jdbcTemplate.update("UPDATE result_hour_spv SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.015), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourNOx_CER"});

            ResultHourSpv rhs3=new ResultHourSpv();
            rhs3.setCode("HourNOx_CER");
            rhs3.setSysTime(new Date());
            rhs3.setDataValue(hour.getCurValue()*0.015);
            rhs3.setProjectId(hour.getProjectId());
            rhs3.setRecTime(hour.getColTime());
            rhs3.setRemarks("");
            rhs3.setState(0);
            int update3=resultHourSpvMapper.updateResultHour(rhs3);
            logger.info("小时定时数据执行氮氧化物,update3:"+update3);
            System.out.println("update3:"+update3);
            //int update4=jdbcTemplate.update("UPDATE result_hour_spv SET data_value = ?,sys_time=?,rec_time=? WHERE project_id = ? and rec_time=? and code=?", new Object[] {(hour.getCur_value()*0.404), new Date(),hour.getCol_time(),hour.getProject_id(),hour.getCol_time(),"HourCoal_CER"});
            ResultHourSpv rhs4=new ResultHourSpv();
            rhs4.setCode("HourCoal_CER");
            rhs4.setSysTime(new Date());
            rhs4.setDataValue(hour.getCurValue()*0.404);
            rhs4.setProjectId(hour.getProjectId());
            rhs4.setRecTime(hour.getColTime());
            rhs4.setRemarks("");
            rhs4.setState(0);
            int update4=resultHourSpvMapper.updateResultHour(rhs4);
            logger.info("小时定时数据执行煤,update4:"+update4);
            System.out.println("update4:"+update4);
        }
    }
}
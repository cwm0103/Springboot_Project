package com.bom.dataservice.dao;

import com.bom.dataservice.model.DayDataSpv;
import com.bom.dataservice.model.HourdataSpv;
import com.bom.dataservice.utils.DifferentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Service
public class DayDataSpvDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 日数据组装
     * @param station_id
     * @param code
     * @param beginDate
     * @param endDate
     */
    public void InsertOrUpdatedataSpv(String station_id, String code, Date beginDate,Date endDate) {

        Double cur_value=jdbcTemplate.queryForObject("select sum(cur_value) from dat_hour_data_spv where project_id=? and col_time between ? and ?", new Object[]{station_id,beginDate,endDate}, java.lang.Double.class);


        try {
            if(cur_value!=null)
            {
                Date col_time = (Date) jdbcTemplate.queryForObject("select col_time from dat_day_data where project_id=? order by col_time desc LIMIT 1", new Object[]{station_id}, Date.class);
                //查詢出來的數據跟當前傳過來的時間對比
                if (col_time != null) {
                    Date newdate = DifferentDate.addonedate(col_time, 1);
                    if (newdate.getTime() <= beginDate.getTime()) {
                        DayDataSpv day = new DayDataSpv();
                        day.setCode(code);
                        day.setProject_id(station_id);
                        day.setCol_time(beginDate);
                        day.setSys_time(new Date());
                        day.setCur_value(cur_value);

                        //插入一條數據
                        int update = jdbcTemplate.update("INSERT INTO dat_day_data VALUES(?, ?, ?, ?, ?, ?)", new Object[]{0, day.getProject_id(), day.getCode(), day.getCur_value(), day.getCol_time(), day.getSys_time()});
                        if (update > 0) {
                            System.out.println("插入日数据成功！");
                        } else {
                            System.out.println("插入日数据失敗！");
                        }

                    } else {
                        //修改一條數據
                        int update = jdbcTemplate.update("UPDATE dat_day_data SET cur_value = ?,sys_time=? WHERE project_id = ? and col_time=?", new Object[]{cur_value, new Date(), station_id, beginDate});
                        if (update > 0) {
                            System.out.println("修改日数据成功！");
                        } else {
                            System.out.println("修改日数据失败!");
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }


    }


    /**
     * 根据站id和日期来获取某天的电量
     * @param stion_id
     * @param date
     * @return
     */
    public Object getYestodaytotal(String stion_id,String  date)
    {
        Double cur_value=0.0;
        try {
             cur_value = (Double) jdbcTemplate.queryForObject("select cur_value from dat_day_data where project_id=? and col_time=?", new Object[]{stion_id,date}, Double.class);
        } catch (DataAccessException e) {
            e.printStackTrace();

        }
        return cur_value;
    }
}

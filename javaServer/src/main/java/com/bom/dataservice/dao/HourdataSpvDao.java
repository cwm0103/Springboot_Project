package com.bom.dataservice.dao;

import com.bom.dataservice.model.EquInfo;
import com.bom.dataservice.model.HourdataSpv;
import com.bom.dataservice.utils.DifferentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Service
public class HourdataSpvDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根拒時間來判斷當前時間是否是插入還是修改當前電量值
     * @param station_id
     * @param code
     * @param date
     */
    public void InsertOrUpdatedataSpv(String station_id,String code,Date date,Double cur_value)
    {
        //查詢最後一條的日期
//        String sql="select col_time from dat_hour_data_spv where project_id=? order by col_time desc LIMIT 1";
        Date col_time = (Date) jdbcTemplate.queryForObject("select col_time from dat_hour_data_spv where project_id=? order by col_time desc LIMIT 1",new Object[]{station_id}, java.util.Date.class);
        //查詢出來的數據跟當前傳過來的時間對比

        try {
            if(col_time!=null)
            {
               Date newdate=  DifferentDate.addonetime(col_time,1);
                if(newdate.getTime() <= date.getTime())
                {
                    HourdataSpv hour=new HourdataSpv();
                    hour.setCode(code);
                    hour.setProject_id(station_id);
                    hour.setCol_time(date);
                    hour.setSys_time(new Date());
                    hour.setCur_value(cur_value);

                    //插入一條數據
                    int update=jdbcTemplate.update("INSERT INTO dat_hour_data_spv VALUES(?, ?, ?, ?, ?, ?)", new Object[] {0, hour.getProject_id(),hour.getCode(), hour.getCur_value(),hour.getCol_time(),hour.getSys_time()});

                    if(update>0)
                    {
                        System.out.println("插入成功！");
                    }else
                    {
                        System.out.println("插入失敗！");
                    }

                }else
                {
                    //修改一條數據
                    int update=jdbcTemplate.update("UPDATE dat_hour_data_spv SET cur_value = ?,sys_time=? WHERE project_id = ? and col_time=?", new Object[] {cur_value, new Date(),station_id,date});
                    if(update>0)
                    {
                        System.out.println("修改成功！");
                    }else
                    {
                        System.out.println("修改失败!");
                    }
                }
            }
        } catch (DataAccessException e) {
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

}

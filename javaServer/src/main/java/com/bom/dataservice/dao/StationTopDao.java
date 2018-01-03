package com.bom.dataservice.dao;

import com.bom.dataservice.model.StationTop;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * Created by chenwangming on 2017/11/16.
 */
@Service
public class StationTopDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<StationTop> getListStationTop(int type)
    {
        String sql=String.format("select * from station_top where type=%1$d order by total_value desc",type);

        return (List<StationTop>)jdbcTemplate.query(sql,new RowMapper<StationTop>(){
            @Override
            public StationTop mapRow(ResultSet rs, int rowNum) throws SQLException {
                StationTop stationTop =new StationTop();
                stationTop.setId(rs.getInt("id"));
                stationTop.setStation_id(rs.getString("station_id"));
                stationTop.setCreate_date(rs.getDate("create_date"));
                stationTop.setType(rs.getInt("type"));
                stationTop.setStation_name(rs.getString("station_name"));
                stationTop.setTotal_value(rs.getDouble("total_value"));
                return stationTop;
            }
        });
    }
}

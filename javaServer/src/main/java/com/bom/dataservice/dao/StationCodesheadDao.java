package com.bom.dataservice.dao;

import com.bom.dataservice.model.StationCodeshead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/27.
 */
@Service
public class StationCodesheadDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<StationCodeshead> getStationCodeshead(String station_Id)
    {
        String sql=String.format("select * from station_codes_head where project_id=%1$s",station_Id);
        return (List<StationCodeshead>)jdbcTemplate.query(sql,new RowMapper<StationCodeshead>(){
            @Override
            public StationCodeshead mapRow(ResultSet rs, int rowNum) throws SQLException {
                StationCodeshead stationCodeshead =new StationCodeshead();
                stationCodeshead.setId(rs.getInt("id"));
                stationCodeshead.setStaId(rs.getString("staId"));
                stationCodeshead.setDomain(rs.getString("domain"));
                stationCodeshead.setProject_id(rs.getString("project_id"));
                return stationCodeshead;
            }
        });
    }
}

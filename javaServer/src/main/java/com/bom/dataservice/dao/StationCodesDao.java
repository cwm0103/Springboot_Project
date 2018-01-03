package com.bom.dataservice.dao;

import com.bom.dataservice.model.StationCodes;
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
public class StationCodesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<StationCodes> getStationCodes(String station_Id,String param_Name)
    {
        String sql=String.format("select * from station_codes where project_id=%1$s and param_name='%2$s'",station_Id,param_Name);

        return (List<StationCodes>)jdbcTemplate.query(sql,new RowMapper<StationCodes>(){
            @Override
            public StationCodes mapRow(ResultSet rs, int rowNum) throws SQLException {
                StationCodes stationCodes =new StationCodes();
                stationCodes.setId(rs.getInt("id"));
                stationCodes.setProject_id(rs.getString("project_id"));
                stationCodes.setCode(rs.getString("code"));
                stationCodes.setCode_des(rs.getString("code_des"));
                stationCodes.setCreate_date(rs.getDate("create_date"));
                stationCodes.setParam_name(rs.getString("param_name"));
                stationCodes.setParam_name_des(rs.getString("param_name_des"));
                return stationCodes;
            }
        });
    }
}

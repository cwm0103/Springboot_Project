package com.bom.dataservice.dao;

import com.bom.dataservice.model.StationInfo;
import com.bom.dataservice.model.StationTop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/21.
 */
@Service
public class StationInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<StationInfo> getList()
    {
        String sql="select * from pub_station_info where station_type=2";

        return (List<StationInfo>)jdbcTemplate.query(sql,new RowMapper<StationInfo>(){
            @Override
            public StationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                StationInfo stationInfo =new StationInfo();
                stationInfo.setId(rs.getInt("id"));
                stationInfo.setStation_id(rs.getString("station_id"));
                stationInfo.setStation_lat(rs.getBigDecimal("station_lat"));
                stationInfo.setStation_lng(rs.getBigDecimal("station_lng"));
                stationInfo.setStation_name(rs.getString("station_name"));
                stationInfo.setStation_name(rs.getString("station_name_en"));
                stationInfo.setStation_esp(rs.getString("station_esp"));
                stationInfo.setLocation(rs.getString("location"));
                stationInfo.setAddr(rs.getString("addr"));
                stationInfo.setFile_id(rs.getString("file_id"));
                stationInfo.setConnect_time(rs.getString("connect_time"));
                stationInfo.setStation_state(rs.getInt("station_state"));
                stationInfo.setDoustation_level(rs.getDouble("station_level"));
                stationInfo.setStation_type(rs.getString("station_type"));
                return stationInfo;
            }
        });
    }

    public List<StationInfo> getAllList()
    {
        String sql="select * from pub_station_info ";

        return (List<StationInfo>)jdbcTemplate.query(sql,new RowMapper<StationInfo>(){
            @Override
            public StationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                StationInfo stationInfo =new StationInfo();
                stationInfo.setId(rs.getInt("id"));
                stationInfo.setStation_id(rs.getString("station_id"));
                stationInfo.setStation_lat(rs.getBigDecimal("station_lat"));
                stationInfo.setStation_lng(rs.getBigDecimal("station_lng"));
                stationInfo.setStation_name(rs.getString("station_name"));
                stationInfo.setStation_name(rs.getString("station_name_en"));
                stationInfo.setStation_esp(rs.getString("station_esp"));
                stationInfo.setLocation(rs.getString("location"));
                stationInfo.setAddr(rs.getString("addr"));
                stationInfo.setFile_id(rs.getString("file_id"));
                stationInfo.setConnect_time(rs.getString("connect_time"));
                stationInfo.setStation_state(rs.getInt("station_state"));
                stationInfo.setDoustation_level(rs.getDouble("station_level"));
                stationInfo.setStation_type(rs.getString("station_type"));
                return stationInfo;
            }
        });
    }

}

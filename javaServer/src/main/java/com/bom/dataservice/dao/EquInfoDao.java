package com.bom.dataservice.dao;

import com.bom.dataservice.model.EquInfo;
import com.bom.dataservice.model.StationTop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/16.
 */
@Service
public class EquInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 根据站ID和类别获取设备
     * @param station_id
     * @param equ_category_big
     * @return
     */
    public List<EquInfo> getListEquInfo(String station_id,Integer equ_category_big)
    {
        String sql=String.format("select * from bas_equ_info where station_id=%1$s  and equ_category_big=%2$d",station_id,equ_category_big);

        return (List<EquInfo>)jdbcTemplate.query(sql,new RowMapper<EquInfo>(){
            @Override
            public EquInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                EquInfo equinfo =new EquInfo();
                equinfo.setStation_id(rs.getString("station_id"));
                equinfo.setAmount(rs.getInt("amount"));
                equinfo.setBelongsystem(rs.getInt("belongsystem"));
                equinfo.setCompany_code(rs.getString("company_code"));
                equinfo.setDevice_no(rs.getString("device_no"));
                equinfo.setEqu_category_big(rs.getInt("equ_category_big"));
                equinfo.setEqu_category_small(rs.getInt("equ_category_small"));
                equinfo.setEqu_id(rs.getInt("equ_id"));
                equinfo.setEqu_name(rs.getString("equ_name"));
                equinfo.setFiles_id(rs.getString("files_id"));
                equinfo.setGateway_id(rs.getInt("gateway_id"));
                equinfo.setKks_code(rs.getString("kks_code"));

                equinfo.setEqu_no(rs.getString("equ_no"));
                equinfo.setManufacture_date(rs.getDate("manufacture_date"));
                equinfo.setProject_code(rs.getString("project_code"));
                equinfo.setProtect_level(rs.getString("protect_level"));
                equinfo.setSp_id(rs.getInt("sp_id"));
                equinfo.setSpecification(rs.getString("specification"));
                equinfo.setUnit(rs.getString("unit"));
                return equinfo;
            }
        });
    }

    /**
     * 根据站ids 来获取所有的设备
     * @param station_ids
     * @return
     */
   public  List<EquInfo> getListEquInfo(String station_ids)
   {
       String sql=String.format("select * from bas_equ_info where station_id in (%1$s)",station_ids);

       return (List<EquInfo>)jdbcTemplate.query(sql,new RowMapper<EquInfo>(){
           @Override
           public EquInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
               EquInfo equinfo =new EquInfo();
               equinfo.setStation_id(rs.getString("station_id"));
               equinfo.setAmount(rs.getInt("amount"));
               equinfo.setBelongsystem(rs.getInt("belongsystem"));
               equinfo.setCompany_code(rs.getString("company_code"));
               equinfo.setDevice_no(rs.getString("device_no"));
               equinfo.setEqu_category_big(rs.getInt("equ_category_big"));
               equinfo.setEqu_category_small(rs.getInt("equ_category_small"));
               equinfo.setEqu_id(rs.getInt("equ_id"));
               equinfo.setEqu_name(rs.getString("equ_name"));
               equinfo.setFiles_id(rs.getString("files_id"));
               equinfo.setGateway_id(rs.getInt("gateway_id"));
               equinfo.setKks_code(rs.getString("kks_code"));

               equinfo.setEqu_no(rs.getString("equ_no"));
               equinfo.setManufacture_date(rs.getDate("manufacture_date"));
               equinfo.setProject_code(rs.getString("project_code"));
               equinfo.setProtect_level(rs.getString("protect_level"));
               equinfo.setSp_id(rs.getInt("sp_id"));
               equinfo.setSpecification(rs.getString("specification"));
               equinfo.setUnit(rs.getString("unit"));
               return equinfo;
           }
       });
   }
}

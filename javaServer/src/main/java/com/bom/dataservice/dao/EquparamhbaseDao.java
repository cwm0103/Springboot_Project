package com.bom.dataservice.dao;

import com.bom.dataservice.model.EquInfo;
import com.bom.dataservice.model.Equparamhbase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/24.
 */
@Service
public class EquparamhbaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Equparamhbase> getListEqHb(String station_Id, String paramName_EN)
    {
        String sql=String.format("select * from bas_equ_param_hbase where station_id='%1$s'  and ParamName_EN='%2$s'",station_Id,paramName_EN);

        return (List<Equparamhbase>)jdbcTemplate.query(sql,new RowMapper<Equparamhbase>(){
            @Override
            public Equparamhbase mapRow(ResultSet rs, int rowNum) throws SQLException {
                Equparamhbase equinfo =new Equparamhbase();
                equinfo.setID(rs.getInt("ID"));
                equinfo.setStation_Id(rs.getString("Station_Id"));
                equinfo.setEquipmentID(rs.getInt("EquipmentID"));
                equinfo.setEquipmentType(rs.getInt("EquipmentType"));
                equinfo.setParamName(rs.getString("ParamName"));
                equinfo.setParamName_EN(rs.getString("ParamName_EN"));
                equinfo.setParamFieldName_HBase(rs.getString("ParamFieldName_HBase"));
                return equinfo;
            }
        });
    }
}

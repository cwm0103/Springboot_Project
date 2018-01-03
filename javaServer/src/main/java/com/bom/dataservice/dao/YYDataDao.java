package com.bom.dataservice.dao;

import com.bom.dataservice.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by enn on 2017/11/19.
 */
@Repository
public class YYDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DataModel> getData(String projectId,String code,String year){
         System.out.println("...projectId:"+projectId+"  code:"+code+" year:"+year) ;
         return (List<DataModel>) jdbcTemplate.query("select code,cur_value,col_time from dat_year_data where project_id=? and code=? and col_time=?" +
                 "  union select code,data_value as cur_value ,rec_time as col_time from result_year where project_id=? and code=? and rec_time=?",
                  new Object[]{projectId,code,year,projectId,code,year},new RowMapper<DataModel>(){

             @Override
             public DataModel mapRow(ResultSet rs, int i) throws SQLException {
                 DataModel dataModel = new DataModel() ;
                 dataModel.setCode(rs.getString("code"));
                 dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("col_time")));
                 dataModel.setValue(rs.getDouble("cur_value"));
                 return dataModel;
             }
         }) ;
    }
}

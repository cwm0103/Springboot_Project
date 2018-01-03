package com.bom.dataservice.dao;

import com.bom.dataservice.model.DataModel;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class DayDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DataModel> getData(String projectId, String code, String day){

        String sql="SELECT code,cur_value,col_time FROM dat_day_data where  project_id='"+projectId+"' and code='"+code+"' and col_time='"+day+"'\n" +
                "union \n" +
                "SELECT code,data_value as cur_value ,rec_time as col_time FROM result_day where project_id='"+projectId+"' and code='"+code+"' and rec_time='"+day+"'\n";
       // System.out.println("...projectId:"+projectId+"  code:"+code+" month:"+month) ;
        return (List<DataModel>) jdbcTemplate.query(sql,new Object[]{},new RowMapper<DataModel>(){
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

    public List<DataModel> getData(String projectId, String code){

        String sql="SELECT code,cur_value,col_time FROM dat_day_data where  project_id='"+projectId+"' and code='"+code+"' \n" +
                "union \n" +
                "SELECT code,data_value as cur_value ,rec_time as col_time FROM result_day where project_id='"+projectId+"' and code='"+code+"' order by col_time  \n";
        System.out.println("...日期没传。projectId:"+projectId+"  code:"+code) ;
        return (List<DataModel>) jdbcTemplate.query(sql,new Object[]{},new RowMapper<DataModel>(){
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

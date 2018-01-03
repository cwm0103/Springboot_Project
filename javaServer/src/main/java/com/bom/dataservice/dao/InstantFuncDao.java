package com.bom.dataservice.dao;

import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.InstantFunc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-25
 * Time: 16:05
 */
@Service
public class InstantFuncDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<InstantFunc> getFuncList(String projectId, String codes){
        List<InstantFunc> funcList=new LinkedList<>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        String sql="SELECT * FROM opt_instant_func  WHERE  staid= :projectId ";
        paramMap.put("projectId", projectId);
        if (StringUtils.isNotBlank(codes)){
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql += " AND cal_code in ( :codes)";
        }
        return (List<InstantFunc>) jdbc.query(sql, paramMap, new RowMapper<InstantFunc>() {
            @Override
            public InstantFunc mapRow(ResultSet rs, int index) throws SQLException {
                InstantFunc dataModel = new InstantFunc();
                dataModel.setCal_code(rs.getString("cal_code"));
                dataModel.setCal_code_name(rs.getString("cal_code_name"));
                dataModel.setFunction(rs.getString("function"));
                dataModel.setList_var(rs.getString("list_var"));
                dataModel.setStaid(rs.getString("staid"));
                dataModel.setFunc_desc(rs.getString("func_desc"));
                dataModel.setId(rs.getInt("id"));
                dataModel.setCreate_date(rs.getTimestamp("create_date"));
                //dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("col_time")));
                //dataModel.setValue(rs.getDouble("cur_value"));
                return dataModel;
            }
        });
        //return funcList;
    }
}

package com.bom.dataservice.dao;

import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.MonthDataSpv;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-20
 * Time: 15:09
 */
@Component
public class HourDataDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DataModel> getData(String projectId, String codes,Date date) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        paramMap.put("project_id", projectId);
        String sql = "SELECT * FROM dat_hour_data  WHERE project_id = :project_id  ";

        if (StringUtils.isNotBlank(codes)) {
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql +=  " AND code in ( :codes) ";
        }
        if(date!=null){
            paramMap.put("date", date);
            sql += " AND col_time = :date ";
        }
        sql+=" ORDER BY col_time ";
        return (List<DataModel>) jdbc.query(sql, paramMap, new RowMapper<DataModel>() {
            @Override
            public DataModel mapRow(ResultSet rs, int index) throws SQLException {
                DataModel dataModel = new DataModel();
                dataModel.setCode(rs.getString("code"));
                dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("col_time")));
                dataModel.setValue(rs.getDouble("cur_value"));
                return dataModel;
            }
        });


    }
    public List<DataModel> getSpvResultData(String projectId, String codes,Date date) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        paramMap.put("project_id", projectId);
        String sql = "SELECT * FROM result_hour_spv  WHERE project_id = :project_id  ";
        if (StringUtils.isNotBlank(codes)) {
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql += " AND code in ( :codes) ";
        }
        if(date!=null){
            paramMap.put("date", date);
            sql += " AND rec_time = :date ";
        }
        sql+=" ORDER BY rec_time DESC  ";
        return (List<DataModel>) jdbc.query(sql, paramMap, new RowMapper<DataModel>() {
            @Override
            public DataModel mapRow(ResultSet rs, int index) throws SQLException {
                DataModel dataModel = new DataModel();
                dataModel.setCode(rs.getString("code"));
                dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("rec_time")));
                dataModel.setValue(rs.getDouble("data_value"));
                return dataModel;
            }
        });
    }
    public List<DataModel> getSpvData(String projectId, String codes,Date date) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        paramMap.put("project_id", projectId);
        String sql = "SELECT * FROM dat_hour_data_spv  WHERE project_id = :project_id  ";
        if (StringUtils.isNotBlank(codes)) {
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql += " AND code in ( :codes) ";
        }
        if(date!=null){
            paramMap.put("date", date);
            sql += " AND col_time = :date ";
        }
        sql+=" ORDER BY col_time DESC  ";
        return (List<DataModel>) jdbc.query(sql, paramMap, new RowMapper<DataModel>() {
            @Override
            public DataModel mapRow(ResultSet rs, int index) throws SQLException {
                DataModel dataModel = new DataModel();
                dataModel.setCode(rs.getString("code"));
                dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("col_time")));
                dataModel.setValue(rs.getDouble("cur_value"));
                return dataModel;
            }
        });
    }
    public List<DataModel> getResultData(String projectId, String codes,Date date) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        paramMap.put("project_id", projectId);
        String sql = "SELECT * FROM result_hour  WHERE project_id = :project_id  ";
        if (StringUtils.isNotBlank(codes)) {
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql += " AND code in ( :codes) ";
        }
        if(date!=null){
            paramMap.put("date", date);
            sql += " AND rec_time = :date " ;
        }
        sql+=" ORDER BY rec_time DESC  ";
        return (List<DataModel>) jdbc.query(sql, paramMap, new RowMapper<DataModel>() {
            @Override
            public DataModel mapRow(ResultSet rs, int index) throws SQLException {
                DataModel dataModel = new DataModel();
                dataModel.setCode(rs.getString("code"));
                dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("rec_time")));
                dataModel.setValue(rs.getDouble("data_value"));
                return dataModel;
            }
        });
    }
    public List<DataModel> getLineData(String projectId, String codes, String beginDate, String endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        paramMap.put("project_id", projectId);
        String sql = "SELECT * FROM dat_hour_data  WHERE project_id = :project_id  ";
        SimpleDateFormat dateFormat=  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(codes)) {
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql += " AND code in ( :codes) ";
        }
        if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
            try {
                paramMap.put("beginDate", dateFormat.parse(beginDate) );
                paramMap.put("endDate", dateFormat.parse(endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            sql += " AND col_time BETWEEN  :beginDate AND  :endDate";
        }

        return (List<DataModel>) jdbc.query(sql, paramMap, new RowMapper<DataModel>() {
            @Override
            public DataModel mapRow(ResultSet rs, int index) throws SQLException {
                DataModel dataModel = new DataModel();
                dataModel.setCode(rs.getString("code"));
                dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("col_time")));
                dataModel.setValue(rs.getDouble("cur_value"));
                return dataModel;
            }
        });

    }

    public List<DataModel> getSpvLineData(String projectId, String codes, String beginDate, String endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        paramMap.put("project_id", projectId);
        String sql = "SELECT * FROM dat_hour_data_spv  WHERE project_id = :project_id  ";
        SimpleDateFormat dateFormat=  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(codes)) {
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql += " AND code in ( :codes) ";
        }
        if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
            try {
                paramMap.put("beginDate", dateFormat.parse(beginDate) );
                paramMap.put("endDate", dateFormat.parse(endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            sql += " AND col_time BETWEEN  :beginDate AND  :endDate;";
        }

        return (List<DataModel>) jdbc.query(sql, paramMap, new RowMapper<DataModel>() {
            @Override
            public DataModel mapRow(ResultSet rs, int index) throws SQLException {
                DataModel dataModel = new DataModel();
                dataModel.setCode(rs.getString("code"));
                dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("col_time")));
                dataModel.setValue(rs.getDouble("cur_value"));
                return dataModel;
            }
        });

    }
    public List<DataModel> getSpvResultLineData(String projectId, String codes, String beginDate, String endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        paramMap.put("project_id", projectId);
        String sql = "SELECT * FROM result_hour_spv  WHERE project_id = :project_id  ";
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(codes)) {
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql += " AND code in ( :codes) ";
        }
        if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
            try {
                paramMap.put("beginDate", dateFormat.parse(beginDate) );
                paramMap.put("endDate", dateFormat.parse(endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            sql += " AND rec_time BETWEEN  :beginDate AND  :endDate;";
        }

        return (List<DataModel>) jdbc.query(sql, paramMap, new RowMapper<DataModel>() {
            @Override
            public DataModel mapRow(ResultSet rs, int index) throws SQLException {
                DataModel dataModel = new DataModel();
                dataModel.setCode(rs.getString("code"));
                dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("rec_time")));
                dataModel.setValue(rs.getDouble("data_value"));
                return dataModel;
            }
        });

    }

    public List<DataModel> getResultLineData(String projectId, String codes, String beginDate, String endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        paramMap.put("project_id", projectId);
        String sql = "SELECT * FROM result_hour  WHERE project_id = :project_id  ";
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(codes)) {
            List<String> codeList = Arrays.asList(codes.split(","));
            paramMap.put("codes", codeList);
            sql += " AND code in ( :codes) ";
        }
        if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
            try {
                paramMap.put("beginDate", dateFormat.parse(beginDate) );
                paramMap.put("endDate", dateFormat.parse(endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            sql += " AND rec_time BETWEEN  :beginDate AND  :endDate;";
        }

        return (List<DataModel>) jdbc.query(sql, paramMap, new RowMapper<DataModel>() {
            @Override
            public DataModel mapRow(ResultSet rs, int index) throws SQLException {
                DataModel dataModel = new DataModel();
                dataModel.setCode(rs.getString("code"));
                dataModel.setCol_time(new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(rs.getTimestamp("rec_time")));
                dataModel.setValue(rs.getDouble("data_value"));
                return dataModel;
            }
        });

    }


}

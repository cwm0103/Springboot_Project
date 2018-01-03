package com.bom.dataservice.dao;

import com.bom.dataservice.model.YearDataSpv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Service
public class YearDataSpvDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据站id和日期来获取某月的电量
     * @param stion_id
     * @return
     */
    public Object getTotalPower(String stion_id)
    {
        Double cur_value=0.0;
        try {
            cur_value = (Double) jdbcTemplate.queryForObject("select sum(cur_value) from dat_year_data where project_id=?", new Object[]{stion_id}, Double.class);
        } catch (DataAccessException e) {
            e.printStackTrace();

        }
        return cur_value;
    }


    /**
     * 累计二氧化碳减排量   累计节能量
     * @param stion_id
     * @return
     */
    public Object getTotalCOAndCoal(String stion_id,String code)
    {
        Double cur_value=0.0;
        try {
            cur_value = (Double) jdbcTemplate.queryForObject("select sum(data_value) from result_year where project_id=? and code=?", new Object[]{stion_id,code}, Double.class);
        } catch (DataAccessException e) {
            e.printStackTrace();

        }
        return cur_value;
    }

}

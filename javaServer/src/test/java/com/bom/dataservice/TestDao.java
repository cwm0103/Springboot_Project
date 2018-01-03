package com.bom.dataservice;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chenwangming on 2017/11/14.
 */

@Service
public class TestDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *不指定数据源使用默认数据源
     *@return
     */
    public List<UserRole> getList(){
        String sql ="select * from pub_user_role";
        return (List<UserRole>)jdbcTemplate.query(sql,new RowMapper<UserRole>(){
            @Override
            public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserRole userRole =new UserRole();
                userRole.setId(rs.getInt("id"));
                userRole.setRole_id(rs.getInt("role_id"));
                userRole.setUser_id(rs.getInt("user_id"));
                return userRole;
            }
        });
    }

    /**
     *指定数据源
     *在对应的service进行指定;
     *@return
     *@author 陈王明
     *@create  2017年11月14日
     */
    public List<UserRole> getListByDs1(){
          /*
           *这张表示复制的主库到ds1的，在ds中并没有此表.
           *需要自己自己进行复制，不然会报错：Table 'test1.demo1'doesn't exist
           */
        String sql ="select * from pub_user_role";
        return(List<UserRole>)jdbcTemplate.query(sql,new RowMapper<UserRole>(){

            @Override
            public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserRole userRole =new UserRole();
                userRole.setId(rs.getInt("id"));
                userRole.setRole_id(rs.getInt("role_id"));
                userRole.setUser_id(rs.getInt("user_id"));
                return userRole;
            }
        });
    }

}

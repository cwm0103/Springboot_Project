package com.sbm.domain;

import java.util.List;

/**
 * Created by chenwangming on 2018/1/3.
 */
public interface IService<T> {
    T selectByKey(Object key);

    int save(T entity);

    int delete(Object key);

    int updateAll(T entity);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pub_user_info
     *
     * @mbggenerated
     */
    List<T> selectAll();
//    int updateNotNull(T entity);
//
//    List<T> selectByExample(Object example);
}
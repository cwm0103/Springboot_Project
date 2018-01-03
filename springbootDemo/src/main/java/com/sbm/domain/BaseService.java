package com.sbm.domain;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by chenwangming on 2018/1/3.
 */
public abstract class BaseService<T> implements IService<T>  {
    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    public int save(T entity) {
        return mapper.insert(entity);
    }

    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pub_user_info
     *
     * @mbggenerated
     */
    public List<T> selectAll(){
        return mapper.selectAll();
    }

//    public int updateNotNull(T entity) {
//        return mapper.updateByPrimaryKeySelective(entity);
//    }
//
//    public List<T> selectByExample(Object example) {
//        return mapper.selectByExample(example);
//    }
}

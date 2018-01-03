package com.func.dao;

import com.func.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by chenwangming on 2017/8/9.
 */
@Mapper
public interface DemoDao {

    /**
     * 获取所有用户
     * @return
     */
    public List<DemoEntity> getAll();
}

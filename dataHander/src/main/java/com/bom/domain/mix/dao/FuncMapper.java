package com.bom.domain.mix.dao;

import com.bom.domain.Mapper;
import com.bom.domain.mix.model.Func;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface FuncMapper extends com.bom.domain.Mapper<Func> {
    Func findByCode(@Param("projectId")String projectId, @Param("calCode") String calCode) ;
}
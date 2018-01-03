package com.sbm.domain.MessageDemo.dao;

import com.sbm.domain.Mapper;
import com.sbm.domain.MessageDemo.model.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
@Repository
public interface MessageMapper extends com.sbm.domain.Mapper<Message> {
    public List<Message> list();
}
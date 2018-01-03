package com.sbm.domain.MessageDemo.service;

import com.sbm.domain.IService;
import com.sbm.domain.MessageDemo.model.Message;

import java.util.List;

/**
 * 由MyBatis Generator工具自动生成
 */
public interface IMessageService extends com.sbm.domain.IService<Message> {
    public static final String SERVICE_NAME = "IMessageService";
    public List<Message> list();
}
package com.sbm.domain.MessageDemo.service.imp;

import com.sbm.domain.BaseService;
import com.sbm.domain.MessageDemo.model.Message;
import com.sbm.domain.MessageDemo.service.IMessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
@Transactional(rollbackFor={Exception.class})
public class MessageServiceImp extends BaseService<Message> implements IMessageService {
    @Autowired
    private com.sbm.domain.MessageDemo.dao.MessageMapper MessageMapper;
    @Override
    public List<Message> list() {
        return MessageMapper.list();
    }
}
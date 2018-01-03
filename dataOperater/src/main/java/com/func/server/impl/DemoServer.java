package com.func.server.impl;

import com.func.dao.DemoDao;
import com.func.entity.DemoEntity;
import com.func.server.iserver.IDemoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/8/9.
 */
@Service
public class DemoServer implements IDemoServer {
    @Autowired
    private DemoDao demoDao;

    @Override
    public List<DemoEntity> getAll() {
        return demoDao.getAll();
    }
}

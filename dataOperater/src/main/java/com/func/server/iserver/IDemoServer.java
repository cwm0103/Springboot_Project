package com.func.server.iserver;

import com.func.entity.DemoEntity;

import java.util.List;

/**
 * Created by chenwangming on 2017/8/9.
 */
public interface IDemoServer {
    /**
     * 获取所有用户
     * @return
     */
    public List<DemoEntity> getAll();
}

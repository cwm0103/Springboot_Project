package com.func.server.impl;

import com.func.dao.SdRecoverDao;
import com.func.entity.SdRecover;
import com.func.server.iserver.ISdRecoverServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/8/16.
 */
@Service
public class SdRecoverServer implements ISdRecoverServer {
    @Autowired
    private SdRecoverDao sdRecoverDao;

    @Override
    public List<SdRecover> getSdRecoverList() {
        return sdRecoverDao.getSdRecoverList();
    }

    /**
     * 批量保存到数据库
     * @param sdlist
     * @return
     */
    @Override
    public int saveData(List<SdRecover> sdlist) {
        return sdRecoverDao.saveData(sdlist);
    }
}

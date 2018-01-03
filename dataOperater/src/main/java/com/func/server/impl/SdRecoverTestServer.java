package com.func.server.impl;

import com.func.dao.SdRecoverTestDao;
import com.func.entity.SdRecoverTest;
import com.func.server.iserver.ISdRecoverTestServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenwangming on 2017/8/18.
 */
@Service
public class SdRecoverTestServer implements ISdRecoverTestServer {

    @Autowired
    private SdRecoverTestDao sdRecoverTestDao;
    /**
     * 获取所有的数据
     * @return
     */
    @Override
    public List<SdRecoverTest> getSdRecoverTestList() {

        return sdRecoverTestDao.getSdRecoverTestList();
    }

    /**
     * 获取io 点
     * @return
     */
    @Override
    public List<String> getIOCodeList() {
        List<SdRecoverTest> ioCodeList = sdRecoverTestDao.getIOCodeList();
        List<String> list=new ArrayList<String>();
        if(ioCodeList.size()>0)
        {
            for (SdRecoverTest sdrt:ioCodeList) {
                list.add(sdrt.getSd_io());
            }

        }
        return list;
    }
    /**
     * 通过Io来查询点数据
     * @param sd_io
     * @return
     */
    @Override
    public List<SdRecoverTest> getSdRecoverTestListByIo(String sd_io)
    {
        return sdRecoverTestDao.getSdRecoverTestListByIo(sd_io);
    }

    /**
     * 通过io 点来获取分组日期
     * @param sd_io
     * @return
     */
    @Override
    public List<Date> getIoDataTimeByIo(String sd_io) {
        return sdRecoverTestDao.getIoDataTimeByIo(sd_io);
    }
    /**
     * 批量保存到数据库
     * @param sdlist
     * @return
     */
    @Override
    public int saveBatchData(List<SdRecoverTest> sdlist) {

        return sdRecoverTestDao.saveBatchData(sdlist);
    }
}

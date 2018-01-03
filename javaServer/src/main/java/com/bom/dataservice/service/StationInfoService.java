package com.bom.dataservice.service;

import com.bom.dataservice.dao.StationInfoDao;
import com.bom.dataservice.model.StationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/11/21.
 */
@Service
public class StationInfoService {
    @Autowired
    private StationInfoDao stationInfoDao;

    public List<StationInfo> getList()
    {
        return stationInfoDao.getList();
    }

    public List<StationInfo> getAllList()
    {
        return  stationInfoDao.getAllList();
    }
}

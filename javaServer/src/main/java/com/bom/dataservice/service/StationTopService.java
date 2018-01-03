package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.StationTopDao;
import com.bom.dataservice.model.StationTop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/11/16.
 */
@Service
public class StationTopService {

    @Autowired
    private StationTopDao stationTopDao;

    @TargetDataSource("ds1")
    public List<StationTop> getListStationTop(int type)
    {
        return stationTopDao.getListStationTop(type);
    }

}

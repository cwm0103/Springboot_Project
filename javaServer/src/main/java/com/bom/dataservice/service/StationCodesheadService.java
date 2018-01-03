package com.bom.dataservice.service;

import com.bom.dataservice.dao.StationCodesheadDao;
import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.model.StationCodeshead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/11/27.
 */
@Service
public class StationCodesheadService {
    @Autowired
    private StationCodesheadDao stationCodesheadDao;

    @TargetDataSource("ds1")
    public List<StationCodeshead> getStationCodeshead(String station_Id)
    {
        return stationCodesheadDao.getStationCodeshead(station_Id);
    }
}

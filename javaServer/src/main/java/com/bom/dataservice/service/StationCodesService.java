package com.bom.dataservice.service;

import com.bom.dataservice.dao.StationCodesDao;
import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.model.StationCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/11/27.
 */
@Service
public class StationCodesService {
    @Autowired
    private StationCodesDao stationCodesDao;
    @TargetDataSource("ds1")
    public List<StationCodes> getStationCodes(String station_Id,String param_Name)
    {
        return stationCodesDao.getStationCodes(station_Id,param_Name);
    }
}

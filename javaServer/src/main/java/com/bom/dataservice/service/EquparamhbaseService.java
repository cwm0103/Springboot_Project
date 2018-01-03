package com.bom.dataservice.service;

import com.bom.dataservice.dao.EquparamhbaseDao;
import com.bom.dataservice.model.Equparamhbase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/11/24.
 */
@Service
public class EquparamhbaseService {

    @Autowired
    private EquparamhbaseDao equparamhbaseDao;

    public List<Equparamhbase> getListEqHb(String station_Id, String paramName_EN)
    {
        return equparamhbaseDao.getListEqHb(station_Id, paramName_EN);
    }
}

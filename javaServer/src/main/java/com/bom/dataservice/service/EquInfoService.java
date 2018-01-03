package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.EquInfoDao;
import com.bom.dataservice.model.EquInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/11/16.
 */
@Service
public class EquInfoService {
    @Autowired
    private EquInfoDao equInfoDao;

    /**
     * 根据 station_id 和 equ_category_big 来获取设备数据
     * @param station_id
     * @param equ_category_big
     * @return
     */
    public List<EquInfo> getListEquInfo(String station_id, Integer equ_category_big)
    {
        return equInfoDao.getListEquInfo(station_id,equ_category_big);
    }

    /**
     * 根据设备 station_ids来获取数据
     * @param station_ids
     * @return
     */
    public List<EquInfo> getListEquInfo(String station_ids)
    {
        return equInfoDao.getListEquInfo(station_ids);
    }
}

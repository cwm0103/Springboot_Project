package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.HourdataSpvDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Service
public class HourdataSpvService {

    @Autowired
    private HourdataSpvDao hourdataSpvDao;
    /**
     * 根拒時間來判斷當前時間是否是插入還是修改當前電量值
     * @param station_id
     * @param code
     * @param date
     */
    @TargetDataSource("ds1")
    public void InsertOrUpdatedataSpv(String station_id, String code, Date date, Double cur_value)
    {
         hourdataSpvDao.InsertOrUpdatedataSpv(station_id,code,date,cur_value);
    }
}

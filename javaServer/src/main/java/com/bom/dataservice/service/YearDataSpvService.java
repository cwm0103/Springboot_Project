package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.HourdataSpvDao;
import com.bom.dataservice.dao.YearDataSpvDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Service
public class YearDataSpvService {

    @Autowired
    private YearDataSpvDao yearDataSpvDao;

    /**
     * 根拒時間來判斷當前時間是否是插入還是修改當前電量值
     * @param station_id
     * @param code
     * @param date
     */
    @TargetDataSource("ds1")
    public void InsertOrUpdateHourdataSpv(String station_id, String code, Date date, Double cur_value)
    {
       //  hourdataSpvDao.InsertOrUpdateHourdataSpv(station_id,code,date,cur_value);
    }
    /**
     * 根据站id和日期来获取某月的电量
     * @param stion_id
     * @return
     */
    @TargetDataSource("ds1")
    public Object getTotalPower(String stion_id)
    {
        Object totalPower=0;
        try {
            totalPower = yearDataSpvDao.getTotalPower(stion_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPower;
    }
    /**
     * 累计二氧化碳减排量   累计节能量
     * @param stion_id
     * @return
     */
    @TargetDataSource("ds1")
    public Object getTotalCOAndCoal(String stion_id,String code)
    {
        Object totalCOAndCoal=0;
        try {
            totalCOAndCoal = yearDataSpvDao.getTotalCOAndCoal(stion_id, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCOAndCoal;
    }
}

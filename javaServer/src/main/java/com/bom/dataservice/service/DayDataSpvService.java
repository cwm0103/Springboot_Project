package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.DayDataSpvDao;
import com.bom.dataservice.dao.HourdataSpvDao;
import com.bom.dataservice.model.DayDataSpv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Service
public class DayDataSpvService {

    @Autowired
    private DayDataSpvDao dayDataSpvDao;

   /**
     * 日数据组装
     * @param station_id
     * @param code
     * @param beginDate
     * @param endDate
     */
    @TargetDataSource("ds1")
    public void InsertOrUpdatedataSpv(String station_id, String code, Date beginDate,Date endDate) {
        dayDataSpvDao.InsertOrUpdatedataSpv(station_id,code,beginDate,endDate);
    }

    @TargetDataSource("ds1")
    public Object getYestodaytotal(String stion_id,Date date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String datetime = sdf.format(date);
        Object yestodaytotal=0.0;
        try {
            yestodaytotal = dayDataSpvDao.getYestodaytotal(stion_id, datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yestodaytotal;
    }
}

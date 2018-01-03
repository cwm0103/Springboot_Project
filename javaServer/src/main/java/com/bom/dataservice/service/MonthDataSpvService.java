package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.HourdataSpvDao;
import com.bom.dataservice.dao.MonthDataSpvDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenwangming on 2017/11/18.
 */
@Service
public class MonthDataSpvService {

    @Autowired
    private MonthDataSpvDao monthDataSpvDao;
    /**
     * 月数据组装
     * @param station_id
     * @param code
     * @param beginDate
     * @param endDate
     */
    @TargetDataSource("ds1")
    public void InsertOrUpdatedataSpv(String station_id, String code, Date beginDate, Date endDate)
    {
        monthDataSpvDao.InsertOrUpdatedataSpv(station_id,code,beginDate,endDate);
    }
    /**
     * 根据站id和日期来获取某月的电量
     * @param stion_id
     * @param date
     * @return
     */
    @TargetDataSource("ds1")
    public Object getLastmonthtotal(String stion_id,Date  date)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-01 00:00:00");
        String datetime = sdf.format(date);
        Object lastmonthtotal=0;
        try {
            lastmonthtotal = monthDataSpvDao.getLastmonthtotal(stion_id, datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastmonthtotal;
    }
}

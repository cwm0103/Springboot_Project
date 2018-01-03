package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.HourDataDao;
import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.LineData;
import com.bom.dataservice.utils.DifferentDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-14
 * Time: 15:31
 */
@Component("HH")
public class HHDataService extends ABSDataService {
    @Autowired
    HourDataDao hourDataDao;

    @TargetDataSource("ds1")
    @Override
    public List<DataModel> getData(String projectId,String codes,String date) {
        Date date1=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        if (StringUtils.isNotBlank(date)){
            try {
                date1=sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            String strDate=sdf.format(new Date());
            try {
                date1=sdf.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        List<DataModel> hourData1=hourDataDao.getData(projectId,codes,date1);
        List<DataModel> hourData2=hourDataDao.getSpvData(projectId,codes,date1);
        List<DataModel> hourData3=hourDataDao.getSpvResultData(projectId,codes,date1);
        List<DataModel> hourData4=hourDataDao.getResultData(projectId,codes,date1);
        hourData1.addAll(hourData2);
        hourData1.addAll(hourData3);
        hourData1.addAll(hourData4);

        return hourData1;
    }
    @TargetDataSource("ds1")
    @Override
    public List<LineData> getLineData(String projectId, String codes, String beginDate, String endDate,String downsample) {
        List<LineData> listline = new LinkedList<LineData>();
        List<DataModel> result=  hourDataDao.getLineData(projectId,codes,beginDate,endDate);
        List<DataModel> result1=  hourDataDao.getSpvResultLineData(projectId,codes,beginDate,endDate);
        List<DataModel> result2=  hourDataDao.getSpvLineData(projectId,codes,beginDate,endDate);
        List<DataModel> result3=  hourDataDao.getResultLineData(projectId,codes,beginDate,endDate);
        result.addAll(result1);
        result.addAll(result2);
        result.addAll(result3);
        String[] codess = codes.split(",");
        List<String> dateList= DifferentDate.DateList(beginDate,endDate,"yyyy-MM-dd HH:00:00");
        for (String code: codess) {
            List<Map<String, Object>> dataModels = new LinkedList<>();
            LineData line = new LineData();
            line.setCode(code);
            for (String time : dateList) {
                Map<String, Object> mapData = new LinkedHashMap<>();
               Optional<DataModel> optionalData = result.stream().filter(x->x.getCode().equals(code) && x.getCol_time().equals(time)).findFirst();
                if(optionalData.isPresent()){
                    DataModel dataModel=optionalData.get();
                    mapData.put("value",dataModel.getValue());
                    mapData.put("col_time", time);
                    dataModels.add(mapData);
                }else{
                    mapData.put("value", "");
                    mapData.put("col_time", time);
                    dataModels.add(mapData);
                }
            }
            line.setDatas(dataModels);
            listline.add(line);
        }
        return listline;
    }
}

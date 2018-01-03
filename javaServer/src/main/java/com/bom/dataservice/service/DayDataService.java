package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.DayDataDao;
import com.bom.dataservice.dao.MMDataDao;
import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.LineData;
import com.bom.dataservice.utils.DifferentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("DD")
public class DayDataService extends ABSDataService {
    @Autowired
    private DayDataDao _dayDataDao ;

    @Override
    @TargetDataSource("ds1")
    public List<DataModel> getData(String projectId, String codes,String date) {
        List<DataModel> dataModels = new ArrayList<>() ;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00") ;
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
            String day = "" ;
            if(date == null||"".equals(date)){

            }else{
                day = sdf.format(sdf1.parse(date)) ;
            }
            if(codes!=null){
                String[] codeArray = codes.split(",") ;
                for(String code : codeArray){
                    List<DataModel> dataModels1 = new ArrayList<>() ;
                    if("".equals(day)){
                        dataModels1 = _dayDataDao.getData(projectId,code);
                    }else{
                        dataModels1 = _dayDataDao.getData(projectId,code,day);
                    }
                    if(dataModels1!=null&&dataModels1.size()>0){
                        DataModel dataModel = dataModels1.get(0) ;
                        dataModels.add(dataModel) ;
                    } ;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return dataModels;
    }

    @Override
    @TargetDataSource("ds1")
    public List<LineData> getLineData(String projectId, String codes, String beginDate, String endDate,String downsample) {
        List<LineData> listline = new LinkedList<LineData>();
        try {
            //计算开始时间和结束时间

            List<Date> ListData = DifferentDate.getListDate(Timestamp.valueOf(beginDate),Timestamp.valueOf(endDate));
            String[] codess = codes.split(",");
            for (String item : codess) {
                List<Map<String, Object>> dataModels = new LinkedList<>();
                LineData line = new LineData();
                line.setCode(item);
                for (Date time : ListData) {
                    Map<String, Object> mapData = new LinkedHashMap<>();
                    String querytime=new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(time);
                    List<DataModel> dataModel = _dayDataDao.getData(projectId,item,querytime+" 00:00:00");
                    if(dataModel!=null&&dataModel.size()>0){
                        mapData.put("value",dataModel.get(0).getValue());
                        mapData.put("col_time", querytime);
                        dataModels.add(mapData);
                    }else{
                        mapData.put("value", "");
                        mapData.put("col_time", querytime);
                        dataModels.add(mapData);
                    }
                }
                line.setDatas(dataModels);
                listline.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listline;
    }


}

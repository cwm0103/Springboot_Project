package com.bom.dataservice.service;

import com.bom.dataservice.config.TargetDataSource;
import com.bom.dataservice.dao.YYDataDao;
import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.LineData;
import com.bom.dataservice.utils.DifferentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by enn on 2017/11/21.
 */
@Component("YY")
public class YYDataService extends ABSDataService {
    @Autowired
    private YYDataDao yyDataDao ;

    @Override
    @TargetDataSource("ds1")
    public List<DataModel> getData(String projectId, String codes,String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-01-01 00:00:00") ;
        String year = sdf.format(new Date()) ;
        List<DataModel> dataModels = new ArrayList<>() ;
        if(codes!=null){
            String[] codeArray = codes.split(",") ;
            for(String code : codeArray){
                List<DataModel> dataModels1 = yyDataDao.getData(projectId,code,year);
                if(dataModels1!=null&&dataModels1.size()>0){
                    DataModel dataModel = dataModels1.get(0) ;
                    dataModel.setCol_time(year.substring(0,4));
                    dataModels.add(dataModel) ;
                } ;
            }
        }
        return dataModels;
    }

    @Override
    @TargetDataSource("ds1")
    public List<LineData> getLineData(String projectId, String codes, String beginDate, String endDate,String downsample) {
        List<LineData> listline = new LinkedList<LineData>();
        try {
            //计算开始时间和结束时间
            List<String> ListData = DifferentDate.yearList(beginDate, endDate);
            String[] codess = codes.split(",");
            for (String item : codess) {
                List<Map<String, Object>> dataModels = new LinkedList<>();
                LineData line = new LineData();
                line.setCode(item);
                for (String time : ListData) {
                    Map<String, Object> mapData = new LinkedHashMap<>();
                    List<DataModel> dataModel = yyDataDao.getData(projectId,item,time+"-01-01 00:00:00");
                    if(dataModel!=null&&dataModel.size()>0){
                        mapData.put("value",dataModel.get(0).getValue());
                        mapData.put("col_time", time+"-01-01 00:00:00");
                        dataModels.add(mapData);
                    }else{
                        mapData.put("value", "");
                        mapData.put("col_time", time+"-01-01 00:00:00");
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

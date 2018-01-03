package com.bom.dataservice.service;

import com.bom.dataservice.model.AjaxResult;
import com.bom.dataservice.model.DataModel;
import com.bom.dataservice.model.LineData;
import com.bom.dataservice.utils.DifferentDate;
import com.bom.dataservice.utils.SimulationHelper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-15
 * Time: 10:57
 */
@Component("FZ")
public class FZDataService extends ABSDataService{

    @Override
    public List<DataModel> getData(String projectId, String codes,String date) {
        List<DataModel> dataModels = new LinkedList<DataModel>();
        try {

            String[] codess = codes.split(",");
                for (String item : codess) {
                    DataModel dm = new DataModel();
                    dm.setCode(item);
                    dm.setValue(SimulationHelper.CreateRandomDouble(1, 100));
                    dm.setCol_time( DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                    dataModels.add(dm);
                }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return  dataModels;
    }

    @Override
    public List<LineData> getLineData(String projectId, String codes, String beginDate, String endDate,String downsample) {
        List<LineData> listline = new LinkedList<LineData>();
        try {
            //计算开始时间和结束时间
            List<String> ListData = DifferentDate.DateList(beginDate, endDate,"yyyy-MM-dd HH:mm:ss");
            String[] codess = codes.split(",");
            for (String item : codess) {
                List<Map<String, Object>> dataModels = new LinkedList<>();
                LineData line = new LineData();
                line.setCode(item);
                for (String time : ListData) {
                    Map<String, Object> mapData = new LinkedHashMap<>();
                    mapData.put("value", SimulationHelper.CreateRandomDouble(1, 100));
                    mapData.put("col_time", time);
                    dataModels.add(mapData);
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

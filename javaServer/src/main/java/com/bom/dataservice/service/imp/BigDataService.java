package com.bom.dataservice.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.bom.dataservice.model.AjaxResult;
import com.bom.dataservice.model.RealDataModel;
import com.bom.dataservice.service.IBigDataService;
import com.bom.dataservice.utils.HTTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-14
 * Time: 14:18
 */
@Service
public class BigDataService implements IBigDataService {
    private static final Logger logger = LoggerFactory.getLogger(BigDataService.class);
    @Value("${bigdata.url.real}")
    private  String REALURL;
    @Value("${token}")
    private   String TOKEN;
    @Override
    public Map<String, String> getNationwideData(String code) {
        code="GL2_RSJFH_PV_CX_1016,GL2_steam_pressure_PV_CX_1016";
        String projectid="1016";
        String url=REALURL+"?token="+TOKEN+"&projectid="+projectid+"&code="+code;

        try {
            JSONObject jsonObject = HTTPUtil.get(url);

        } catch (Exception e) {
            logger.error("获取数据异常",e);
            e.printStackTrace();
        }
        return null;
    }

    public RealDataModel getRealData(String projectId,String codes){
        RealDataModel model=new RealDataModel();
        String url=REALURL+"?token="+TOKEN+"&projectid="+projectId+"&code="+codes;
        try {
            model = HTTPUtil.get(url,RealDataModel.class);
        } catch (Exception e) {
            logger.error("获取数据异常",e);
            e.printStackTrace();
        }
        return model;
    };
}

package com.bom.domain.mix.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.model.DatDayData;
import com.bom.domain.data.model.DatHourData;
import com.bom.domain.mix.model.Func;
import com.bom.domain.mix.model.MixHour;
import com.bom.domain.mix.model.Reference;
import com.bom.domain.mix.model.ResultHour;
import com.bom.domain.mix.service.IResultHourService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.bom.utils.RunString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class ResultHourServiceImp extends BaseService<ResultHour> implements IResultHourService {
    @Autowired
    private com.bom.domain.mix.dao.ResultHourMapper resultHourMapper;
    @Autowired
    private com.bom.domain.mix.dao.FuncMapper funcMapper;
    @Autowired
    private com.bom.domain.mix.dao.ReferenceMapper referenceMapper;
    @Autowired
    private com.bom.domain.data.dao.DatHourDataMapper datHourDataMapper;

    @Override
    @TargetDataSource(name = "datadb")
    public void calc() {
        // 融合计算时间
        Date mixtime = null;
        // 实例化字符串计算类
        RunString rs = new RunString();
        // 用于存放基础测点和对应的值
        Map<String, String> map = new HashMap<String, String>();
        // 测点计数
        int count = 1;
        // 使用calendar类，得到当前时间
        Calendar now = Calendar.getInstance();
        // 设置时间(result_hour)
        now.add(Calendar.HOUR, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        String timeStr = sdf.format(now.getTimeInMillis());
        System.out.println("小时级聚合时间：" + timeStr);
        try {
            mixtime = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            //  throw new RuntimeException(e);
        }

        // 查询所有计算测点的记录
        //Query query = session.createQuery("from Func");
        List<Func> funcs = funcMapper.selectAll();

        // 遍历所有计算测点公式
        for (Func f : funcs) {
            System.out.println(count + ". 计算测点：" + f.getCalCode() + " 融合开始...");
            try {


                if (StringUtils.isNotBlank(f.getListVar())) {
                    // 获得基础测点（参数）列表
                    String[] codes = f.getListVar().split(",");

                    // 从小时级聚合表中得到每个基础测点的数值，并存入map<基础测点，测点值>中
                    for (String code : codes) {
                        map.put(code,getData(f.getProjectId(),code,timeStr)) ;
                    }

                    if (map.size() == codes.length) {
                        // 获得计算测点的公式
                        String function = f.getFunction();
                        // 初始化计算结果
                        Double result = null;
                        try {
                            result = rs.calString(function, map);
                            if("NaN".equals(result.toString())){
                                result = 0.0 ;
                            }
                            result=filterData(f.getCalCode(),result);
                        } catch (Exception e) {
                            result=0.0;
                            e.printStackTrace();
                        }
                        System.out.println("\t计算结果是：" + result + '\n');

                        // 开启事务
                        List<ResultHour> resultHourList = resultHourMapper.findByConditon(f.getProjectId(), f.getCalCode(), timeStr);
                        if (resultHourList != null && resultHourList.size() > 0) {//resultHour表中已经存在，则更新
                            ResultHour rh = resultHourList.get(0);
                            rh.setDataValue(result);
                            resultHourMapper.updateByPrimaryKey(rh);
                        } else {
                            ResultHour mh = new ResultHour();
                            mh.setProjectId(f.getProjectId());
                            mh.setCode(f.getCalCode());
                            mh.setRecTime(mixtime);
                            mh.setDataValue(result);
                            mh.setState(192);
                            mh.setRemarks(null);
                            // 插入计算测点
                            resultHourMapper.insert(mh);
                        }
                    } else {
                        System.out.println("\t错误：未得到计算结果\n");
                    }
                    count++;
                }
            } catch (Exception e) {
                map.clear();
                e.printStackTrace();
            }finally {
                map.clear();
            }
            count = 1;
        }
    }

    public double filterData(String code,double value){
        if(code.contains("CCR") || code.contains("CER") ||code.contains("CRR"))
        {
            if(value<0){
                return  0.0;
            }else{
                return  value;
            }
        }else if(code.contains("EETA")){
            if(value>99.9){
                return 99.9;
            }else{
                return  value;
            }
        }
        else {

            return  value;
        }
    }

    public Map<String,Func> getFunMap(String codes,List<Func> funcList){
        Map<String,Func> funcMap=new LinkedHashMap <>();
        String[] arrFunc=codes.split(",");
        for(int i=0;i<arrFunc.length;i++){
            String code=arrFunc[i];
            String[] codelist = code.split("_");
            if(codelist.length>3){
                code = code.replace(codelist[0] + "_", "");
            }
            
            String finalCode = code;
            funcMap= funcList.stream().filter(x-> finalCode.equals(x.getCalCode())).collect(Collectors.toMap(Func::getCalCode, y->y));
        }
        return  funcMap;
    }

    public String getData (String projectId,String code,String timeStr){
        String[] codelist = code.split("_");
        if(codelist.length>3){
            projectId = codelist[0] ;
            code = code.replace(codelist[0] + "_", "");
        }
        Func func = funcMapper.findByCode(projectId,code) ;
        if(func == null){
            List<DatHourData> datavalue = null;
            code = code.trim();
            datavalue = datHourDataMapper.findHourData(projectId, code, timeStr);
            if (datavalue != null && datavalue.size() > 0) {
                return datavalue.get(0).getCurValue().toString();
            }else{
                return "0" ;
            }

        }else{
            Map<String, String> map = new HashMap<String, String>();
            RunString rs = new RunString();
            String[] codesArray = func.getListVar().split(",") ;
            for(String code1 : codesArray){
                map.put(code1,getData(projectId,code1,timeStr)) ;
            }
            if (map.size() == codesArray.length) {
                // 获得计算测点的公式
                String function = func.getFunction();
                // 初始化计算结果
                Double result = null;
                try {
                    result = rs.calString(function, map);
                    if("NaN".equals(result.toString())){
                        result = 0.0 ;
                    }
                    result=filterData(func.getCalCode(),result);
                } catch (Exception e) {
                    result=0.0;
                    e.printStackTrace();
                }
                return result.toString();
            }else{
                return "0" ;
            }
        }
    }

}
package com.bom.domain.mix.service.imp;

import com.bom.datasource.TargetDataSource;
import com.bom.domain.BaseService;
import com.bom.domain.data.model.DatDayData;
import com.bom.domain.data.model.DatMonthData;
import com.bom.domain.mix.model.Func;
import com.bom.domain.mix.model.MixMonth;
import com.bom.domain.mix.model.Reference;
import com.bom.domain.mix.model.ResultMonth;
import com.bom.domain.mix.service.IResultMonthService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bom.utils.RunString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
public class ResultMonthServiceImp extends BaseService<ResultMonth> implements IResultMonthService {
    @Autowired
    private com.bom.domain.mix.dao.ResultMonthMapper resultMonthMapper;
    @Autowired
    private com.bom.domain.mix.dao.FuncMapper funcMapper;
    @Autowired
    private com.bom.domain.mix.dao.ReferenceMapper referenceMapper;
    @Autowired
    private com.bom.domain.data.dao.DatMonthDataMapper datMonthDataMapper;

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
        // 设置时间(result_day)
//        now.add(Calendar.MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00");
        String timeStr = sdf.format(now.getTimeInMillis());
        //System.out.println("月级聚合时间："+timeStr);
        try {
            mixtime = sdf.parse(timeStr);
        } catch (ParseException e1) {
            throw new RuntimeException(e1);
        }

        // 查询所有计算测点的记录
        List<Func> funcs = funcMapper.selectAll();

        // 遍历所有计算测点公式
        for (Func f : funcs) {

            //System.out.println("计算测点："+f.getCalCode() + "融合开始");
            // 获得基础测点（参数）列表

            // 从天级聚合表中得到每个基础测点的数值，并存入map<基础测点，测点值>中


            try {
                String[] codes = f.getListVar().split(",");
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
                    //System.out.println("计算结果是：" + result + '\n');

                    // 开启事务
                    List<ResultMonth> resultMonthList = resultMonthMapper.findByConditon(f.getProjectId(), f.getCalCode(), timeStr);
                    if (resultMonthList != null && resultMonthList.size() > 0) {
                        ResultMonth rm = resultMonthList.get(0);
                        rm.setDataValue(result);
                        resultMonthMapper.updateByPrimaryKey(rm);
                    } else {
                        ResultMonth mm = new ResultMonth();
                        mm.setProjectId(f.getProjectId());
                        mm.setCode(f.getCalCode());
                        mm.setRecTime(mixtime);
                        mm.setDataValue(result);
                        mm.setState(192);
                        mm.setRemarks(null);
                        // 插入计算测点
                        resultMonthMapper.insert(mm);
                    }
                } else {
                    //System.out.println("\t错误：未得到计算结果\n");
                }
                // 清空map
            } catch (Exception e) {
                map.clear();
                e.printStackTrace();
            }finally {
                map.clear();
                count++;
            }
        }
        System.out.println("****** 月聚合结束 ******");
        count = 1;
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

    public String getData (String projectId,String code,String timeStr){
        String[] codelist = code.split("_");
        if(codelist.length>3){
            projectId = codelist[0] ;
            code = code.replace(codelist[0] + "_", "");
        }
        Func func = funcMapper.findByCode(projectId,code) ;
        if(func == null){
            List<DatMonthData> datavalue = null;
            code = code.trim();
            datavalue = datMonthDataMapper.findByConditon(projectId, code, timeStr);
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
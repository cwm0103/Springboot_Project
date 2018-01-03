package com.func.controller;

import com.func.entity.SdRecoverTest;
import com.func.server.iserver.ISdRecoverTestServer;
import com.func.tools.DifferentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据操作控制器
 * Created by chenwangming on 2017/8/18.
 */

@Controller
@RequestMapping("/operate/")
public class DataOperate {

    @Autowired
    private ISdRecoverTestServer sdRecoverTestServer;
    /**
     * 读取所有的数据
     * @return
     */
    public List<SdRecoverTest> getsd_recover_copy()
    {
        return sdRecoverTestServer.getSdRecoverTestList();
    }

    /**
     * 通过分组查询iocode
     * @return
     */
    public List<String> getiocode()
    {
            return sdRecoverTestServer.getIOCodeList();
    }

    /**
     * 根据sd_io来查询点数据
     * @param sd_io
     * @return
     */
    public List<SdRecoverTest> getSdRecoverTestByIo(String sd_io)
    {
        return sdRecoverTestServer.getSdRecoverTestListByIo(sd_io);
    }

    /**
     * io点 和io点所有的数据集合
     * @param list
     * @param iocodelist
     * @return
     */
    public Map<String,List<SdRecoverTest>> getMapList(List<SdRecoverTest> list,List<String> iocodelist)
    {
        Map<String,List<SdRecoverTest>> map=new HashMap<String,List<SdRecoverTest>>();
        if(iocodelist.size()>0)
        {
            for (String str:iocodelist) {

              List<SdRecoverTest> clist=new ArrayList<SdRecoverTest>();
              for (SdRecoverTest sdr:list) {
                    if(sdr.getSd_io().equals(str))
                    {
                        clist.add(sdr);
                    }
              }
              map.put(str,clist);
            }
        }
        return map;
    }



    //获取所有存在map中的
    public Map<String,List<SdRecoverTest>> zhengliDayList()
    {
        Map<String,List<SdRecoverTest>> IOList=new HashMap<String,List<SdRecoverTest>>();
        List<String> iocodelist=getiocode();
        for (int i=0;i<iocodelist.size();i++)
        {
            List<SdRecoverTest> list=getSdRecoverTestByIo(iocodelist.get(i));
            IOList.put(iocodelist.get(i),list);
        }
        return IOList;
    }



    /**
     * 根据io点分配好的日期数
     */
    @RequestMapping("/ioall")
    public Map<String,Map<Date,List<SdRecoverTest>>> getDataIo() throws ParseException {
        //获取所有io点的数据
        Map<String,List<SdRecoverTest>> map=zhengliDayList();

        //创建一个Map io和他所有的数据
        Map<String,Map<Date,List<SdRecoverTest>>> mapiodata=new HashMap<String,Map<Date,List<SdRecoverTest>>>();
        for(Map.Entry<String,List<SdRecoverTest>> entry:map.entrySet())
        {
            //获取每个io点
            String iocode=  entry.getKey();
            //获取每个io点的数据
            List<SdRecoverTest> listsdr=entry.getValue();
            //获取每个io点的日期数据
            Map<Date,List<SdRecoverTest>> dayIo= setDataIo(listsdr,iocode);
            //获取每日的时间数据
            Map<Date,List<SdRecoverTest>> dayTotime= setTimeIoData(dayIo);

            mapiodata.put(iocode,dayTotime);
            System.out.print("成功！");
        }
        return mapiodata;
    }
    //获取io点日数据
    public Map<Date,List<SdRecoverTest>> setDataIo(List<SdRecoverTest> listsdr,String iocode)
    {
        //存储io点的日数据
        Map<Date,List<SdRecoverTest>> map=new HashMap<Date,List<SdRecoverTest>>();
        //io点的日期分组
        List<Date> datetime=sdRecoverTestServer.getIoDataTimeByIo(iocode);
        for (int i=0;i<datetime.size();i++)
        {
            Date date = datetime.get(i);
            List<SdRecoverTest> dayList=new ArrayList<SdRecoverTest>();
            for(int j=0;j<listsdr.size();j++)
            {
                if(date.equals(listsdr.get(j).getSd_data()))
                {
                   dayList.add(listsdr.get(j));
                }
            }
            map.put(date,dayList);
        }
        return map;
    }
    //设置io点每日的时间数据
    public Map<Date,List<SdRecoverTest>> setTimeIoData(Map<Date,List<SdRecoverTest>> dayIo) throws ParseException {
        Map<Date,List<SdRecoverTest>> timeIoData=new HashMap<Date,List<SdRecoverTest>>();
        //循环设置每天的数据
        for(Map.Entry<Date,List<SdRecoverTest>> entry:dayIo.entrySet())
        {
            Date time=entry.getKey();
            List<SdRecoverTest> list=entry.getValue();

            List<SdRecoverTest> orderList = getOrderList(time, list);
            timeIoData.put(time,orderList);
        }
        return timeIoData;
    }
    //设置一天24小时的io数据
    public  List<SdRecoverTest> getOrderList(Date time,List<SdRecoverTest> list) throws ParseException {
        //获取这天的24小时
       List<Date> listtime= DifferentDate.time24hours(time);
       //返回的日期数据
       List<SdRecoverTest> daylist=new ArrayList<SdRecoverTest>();
       for (Date hours :listtime) {
          daylist.add(getonemdel(hours,list));
       }
       return daylist;
    }
    //取出一条相近的数据
    private SdRecoverTest getonemdel(Date time,List<SdRecoverTest> list) {
        SdRecoverTest sdrt = new SdRecoverTest();
        //申明一个数据，存放计算数据；
        long[] arr = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            long millisecond =DifferentDate.getmillisecond(list.get(i).getSd_datatime(), time);
            arr[i] = millisecond;
        }
        //获取到的索引
        int index = DifferentDate.getMin(arr);
        //根据索引获取一条记录
        SdRecoverTest ssdrtt = list.get(index);

        sdrt.setSd_io(ssdrtt.getSd_io());
        sdrt.setSd_code(ssdrtt.getSd_code());
        sdrt.setSd_data(ssdrtt.getSd_data());
        sdrt.setSd_value(ssdrtt.getSd_value());
        sdrt.setSd_datatime(time);
        return sdrt;
    }






    //===================================单个io点来=========================================//
    //根据获取数据
    private List<SdRecoverTest> zhengliDayList(String io)
    {
        List<SdRecoverTest> list=getSdRecoverTestByIo(io);
        return list;
    }

    //设置每个io的所有日期数据
    private Map<Date,List<SdRecoverTest>> setDataByIo(String io) throws ParseException {
        //Map<String,Map<Date,List<SdRecoverTest>>> mapiodata=new HashMap<String,Map<Date,List<SdRecoverTest>>>();
        List<SdRecoverTest> sdRecoverTests = zhengliDayList(io);
        //获取每个io点的日期数据
        Map<Date,List<SdRecoverTest>> dayIo= setDataIo(sdRecoverTests,io);
        //获取每日的时间数据
        Map<Date,List<SdRecoverTest>> dayTotime= setTimeIoData(dayIo);
        return dayTotime;
    }

    //指定日期制造数据
    private Map<Date,List<SdRecoverTest>> setIoData(Date star,Date end,String io) throws ParseException {

        //计算出开始日期和计算日期的天数
        List<Date> listDate = DifferentDate.getListDate(star, end);

        //获取原有日期
        Map<Date, List<SdRecoverTest>> dateListMap = setDataByIo(io);
        //原数据的日期
        List<Date> originalDate=new ArrayList<Date>();
        for(Map.Entry<Date,List<SdRecoverTest>> entry:dateListMap.entrySet())
        {
            originalDate.add(entry.getKey());
        }

        //制造最终得日期数据
        Map<Date,List<SdRecoverTest>> saveDateList=new HashMap<Date,List<SdRecoverTest>>();

        for(Date day:listDate)
        {
            Date getmixcompore = DifferentDate.getmixcompore(originalDate, day);
            List<SdRecoverTest> sdRecoverTests = dateListMap.get(getmixcompore);
            saveDateList.put(day,sdRecoverTests);
        }
        return saveDateList;

    }


    //把执照好的数据保存到数据库
    private int  savebatchDateList(Map<Date,List<SdRecoverTest>> data)
    {
        int result=0;
        if(data.size()>0)
        {
            for (Map.Entry<Date,List<SdRecoverTest>> entry:data.entrySet())
            {
                result= sdRecoverTestServer.saveBatchData(entry.getValue());
            }
        }
        return result;
    }

    @RequestMapping("/SaveData")
    public String execfunc() throws ParseException {
        //20161106·20161207    20170213·20170221    20161209·20161217    三个时间段

        //第二次  20161209-20161217    20170213-20170221
        int result=0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        //开始日期
        String  start="2017-02-13 00:00:00";//2016-11-06 00:00:00   //2017-02-13 00:00:00  //2016-12-09 00:00:00
        //结束日期
        String end="2017-02-21 00:00:00";//2016-12-07 00:00:00    //2017-02-21 00:00:00    //2016-12-17 00:00:00
        //io
        //String io="YHZ_T_FIQ_301_INT";
        List<String> iocodelist=getiocode();
        for (int i=0;i<iocodelist.size();i++)
        {
            Map<Date, List<SdRecoverTest>> dateListMap = setIoData(sdf.parse(start), sdf.parse(end), iocodelist.get(i));

            result=savebatchDateList(dateListMap);
            System.out.print("提交成功！"+result);

        }



        System.out.print("=====提交成功！============");
        return "xxxxxxx";
    }

}

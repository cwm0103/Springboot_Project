package com.oper.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oper.dto.DataDispaly;
import com.oper.dto.PubDashboardConfigVModel;
import com.oper.dto.PubDashboardDataVModel;
import com.oper.entity.PubDashboardConfig;
import com.oper.entity.PubDashboardData;
import com.oper.entity.PubView;
import com.oper.repository.PubDashboardConfigRepository;
import com.oper.repository.PubDashboardDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * cwm
 * 2018-8-6
 * 创建图标配置类
 */
@Service
@Slf4j
public class PubDashboardConfigService {

    @Autowired
    private PubDashboardConfigRepository pubDashboardConfigRepository;
    @Autowired
    private PubDashboardDataRepository pubDashboardDataRepository;

    /**
     * 添加图标配置文件
     * @param pbc
     * @return
     */
    public PubDashboardConfig add(PubDashboardConfig pbc)
    {
        try {

            PubDashboardConfig save = pubDashboardConfigRepository.save(pbc);
            return save;
        }catch (Exception e)
        {
            log.error(e.toString());
            return null;
        }
    }

    /**
     * 根据id删除图表配置
     * @param dashboardId
     */
    @Transactional
    @Modifying
    public boolean delete(Integer dashboardId)
    {
        try {
            Object o = pubDashboardDataRepository.deletePubDashboardDataByDashboardId(dashboardId);
            if(o!=null)
            {
                pubDashboardConfigRepository.deletePubDashboardConfigByDashboardId(dashboardId);
                return true;
            }
            return false;

        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return false;
        }
    }

    /**
     * 修改图表配置
     * @param pbc
     */
    @Transactional
    @Modifying
    public boolean update(PubDashboardConfig pbc)
    {
        try {
            PubDashboardConfig pbcid = pubDashboardConfigRepository.findPubDashboardConfigByDashboardId(pbc.getDashboardId());
            pbcid.setTimeType(pbc.getTimeType());
            pbcid.setChartType(pbc.getChartType());
            pbcid.setDashboardDesc(pbc.getDashboardDesc());
            pbcid.setDashboardPosition(pbc.getDashboardPosition());
            pbcid.setDashboardSize(pbc.getDashboardSize());
            pbcid.setDashboardTitle(pbc.getDashboardTitle());
            pbcid.setNumber(pbc.getNumber());
            pbcid.setParamInfo(pbc.getParamInfo());
            pbcid.setViewId(pbc.getViewId());
            pbcid.setDasx(pbc.getDasx());// 添加x轴
            pbcid.setDasy(pbc.getDasy());//添加 y轴
            pbcid.setValueType(pbc.getValueType());//添加值类型
            return true;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return false;
        }
    }

    /**
     * 根据vid来查询所有的数据
     * @param vid
     * @return
     */
    public Object  getAlllist(Integer vid)
    {
        try {
            List<PubDashboardConfigVModel> allList=new LinkedList<>();
            List<PubDashboardConfig> allByViewId = pubDashboardConfigRepository.findAllByViewIdIs(vid);
            if(allByViewId!=null)
            {
                //region old
//                allByViewId.forEach(pdb -> {
//                    List<PubDashboardData> pubDashboardDataByDashboardIdIs = pubDashboardDataRepository.findPubDashboardDataByDashboardIdIs(pdb.getDashboardId());
//                    List<PubDashboardData> list=new LinkedList<>();
//
//                    if(pubDashboardDataByDashboardIdIs.size()>0)
//                    {
//                        pubDashboardDataByDashboardIdIs.forEach(pvid->{
//                            list.add(pvid);
//                        });
//                    }
//                    PubDashboardConfigVModel pviData=new PubDashboardConfigVModel();
//                    pviData.setPubDashboardData(list);
//                    pviData.setChartType(pdb.getChartType());
//                    pviData.setDashboardDesc(pdb.getDashboardDesc());
//                    pviData.setDashboardPosition(pdb.getDashboardPosition());
//                    pviData.setDashboardSize(pdb.getDashboardSize());
//                    pviData.setDashboardTitle(pdb.getDashboardTitle());
//                    pviData.setNumber(pdb.getNumber());
//                    pviData.setParamInfo(pdb.getParamInfo());
//                    pviData.setTimeType(pdb.getTimeType());
//                    pviData.setViewId(pdb.getViewId());
//                    pviData.setDashboardId(pdb.getDashboardId());
//                    allList.add(pviData);
//                });
//                return allList;
                //endregion


                //region new
                allByViewId.forEach(pdc->{
                    List<PubDashboardDataVModel> pddvm=new LinkedList<>();
                    //根据did来查询数据
                    List<PubDashboardData> pddis = pubDashboardDataRepository.findPubDashboardDataByDashboardIdIs(pdc.getDashboardId());
                    if(pddis.size()>0)
                    {
                        pddis.forEach(pdd->{
                            List<DataDispaly> codeListData = getCodeListData(pdd.getCode());
                            PubDashboardDataVModel pvm=new PubDashboardDataVModel();
                            pvm.setDataDispalies(codeListData);
                            pvm.setDataName(pdd.getDataName());
                            pvm.setCode(pdd.getCode());
                            pvm.setColor(pdd.getColor());
                            pvm.setDashboardId(pdd.getDashboardId());
                            pvm.setUnit(pdd.getUnit());
                            pvm.setDataId(pdd.getDataId());
                            pddvm.add(pvm);
                        });
                    }
                    PubDashboardConfigVModel pdcv=new PubDashboardConfigVModel();
                    pdcv.setPubDashboardDataVModels(pddvm);
                    pdcv.setChartType(pdc.getChartType());
                    pdcv.setDashboardDesc(pdc.getDashboardDesc());
                    pdcv.setDashboardPosition(pdc.getDashboardPosition());
                    pdcv.setDashboardSize(pdc.getDashboardSize());
                    pdcv.setDashboardTitle(pdc.getDashboardTitle());
                    pdcv.setNumber(pdc.getNumber());
                    pdcv.setParamInfo(pdc.getParamInfo());
                    pdcv.setTimeType(pdc.getTimeType());
                    pdcv.setViewId(pdc.getViewId());
                    pdcv.setDashboardId(pdc.getDashboardId());
                    allList.add(pdcv);
                });
                return allList;
                //endregion
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }


    /**
     * 根据did来查询所有的数据
     * @param did
     * @return
     */
    public Object  getOnelist(Integer did)
    {
        try {
            PubDashboardConfig pdcdid = pubDashboardConfigRepository.findPubDashboardConfigByDashboardId(did);
            if(pdcdid!=null)
            {
                //region new
                    List<PubDashboardDataVModel> pddvm=new LinkedList<>();
                    //根据did来查询数据
                    List<PubDashboardData> pddis = pubDashboardDataRepository.findPubDashboardDataByDashboardIdIs(pdcdid.getDashboardId());
                    if(pddis.size()>0)
                    {
                        pddis.forEach(pdd->{
                            List<DataDispaly> codeListData = getCodeListData(pdd.getCode());
                            PubDashboardDataVModel pvm=new PubDashboardDataVModel();
                            pvm.setDataDispalies(codeListData);
                            pvm.setDataName(pdd.getDataName());
                            pvm.setCode(pdd.getCode());
                            pvm.setColor(pdd.getColor());
                            pvm.setDashboardId(pdd.getDashboardId());
                            pvm.setUnit(pdd.getUnit());
                            pvm.setDataId(pdd.getDataId());
                            pddvm.add(pvm);
                        });
                    }
                    PubDashboardConfigVModel pdcv=new PubDashboardConfigVModel();
                    pdcv.setPubDashboardDataVModels(pddvm);
                    pdcv.setChartType(pdcdid.getChartType());
                    pdcv.setDashboardDesc(pdcdid.getDashboardDesc());
                    pdcv.setDashboardPosition(pdcdid.getDashboardPosition());
                    pdcv.setDashboardSize(pdcdid.getDashboardSize());
                    pdcv.setDashboardTitle(pdcdid.getDashboardTitle());
                    pdcv.setNumber(pdcdid.getNumber());
                    pdcv.setParamInfo(pdcdid.getParamInfo());
                    pdcv.setTimeType(pdcdid.getTimeType());
                    pdcv.setViewId(pdcdid.getViewId());
                    pdcv.setDashboardId(pdcdid.getDashboardId());
                    return pdcv;
                //endregion
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }


    /**
     * 根据标题，类别，描述来查询一个报表
     * @param pubDashboardConfig
     * @return
     */
    public PubDashboardConfig getPubDcByTTD(PubDashboardConfig pdc)
    {
        try{
            if(pdc!=null)
            {
                PubDashboardConfig pdcbyttd = pubDashboardConfigRepository.findPubDashboardConfigByDashboardTitleEqualsAndChartTypeAndDashboardDesc(pdc.getDashboardTitle(), pdc.getChartType(), pdc.getDashboardDesc());
                if(pdcbyttd!=null)
                {
                    return pdcbyttd;
                }
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 根据id来修改报表位置
     * @param id
     * @param position
     * @return
     */
    @Transactional
    @Modifying
    public Boolean udatePosition(Integer id,String position)
    {
        try{
            PubDashboardConfig one = pubDashboardConfigRepository.findPubDashboardConfigByDashboardId(id);
            if(one!=null)
            {
                one.setDashboardPosition(position);
                return true;
            }
            return false;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return false;
        }
    }

    /**
     * 根据id来查询一条实体报表数据
     * @param id
     * @return
     */
    public PubDashboardConfig getById(Integer id)
    {
        try{

            PubDashboardConfig pubDashboardConfigByDashboardId = pubDashboardConfigRepository.findPubDashboardConfigByDashboardId(id);
            if(pubDashboardConfigByDashboardId!=null)
            {
                return pubDashboardConfigByDashboardId;
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 根据vid返回所有得报表数据
     * @param vid
     * @return
     */
    public List<PubDashboardConfig> getPDCListByVid(Integer vid)
    {
        //x排序后y排序
            try{
                List<PubDashboardConfig> allByViewIdIs = pubDashboardConfigRepository.getAllByVidSort(vid);//findAllByViewIdIs(vid);
                if(allByViewIdIs!=null)
                {
                    return allByViewIdIs;
                }
                return null;
    }catch (Exception ex){
        log.error(ex.getMessage());
        return null;
    }
    }

    /**
     * 批量修改报表视图位置
     * @param pdclist
     * @return
     */
    @Transactional
    @Modifying
    public Boolean updateListPosition(JSONArray pdclist)
    {
        try
        {
            if(pdclist.size()>0)
            {
                pdclist.forEach(p->{
                 JSONObject pl= (JSONObject) JSON.toJSON(p);
                 String id= pl.getString("dashboardId");
                 String position= pl.getString("dashboardPosition");
                 Integer dasx=pl.getInteger("dasx");
                 Integer dasy=pl.getInteger("dasy");
                 //要加
                    PubDashboardConfig pddci = pubDashboardConfigRepository.findPubDashboardConfigByDashboardId(Integer.valueOf(id));
                    if(pddci!=null)
                    {
                        pddci.setDashboardPosition(position);
                        //要做
                        pddci.setDasx(dasx);
                        pddci.setDasy(dasy);
                    }
                });
                return true;
            }
            return false;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return false;
        }
    }

    private List<DataDispaly> getCodeListData(String code)
    {
        if(!code.isEmpty())
        {
            List<DataDispaly> dataDispalies=new LinkedList<>();

            for(int i=0;i<12;i++)
            {
                DataDispaly dispaly=new DataDispaly();
                dispaly.setDate((new Date()).toString());
                Integer value=i+new Random().nextInt(100);
                dispaly.setValue(value.toString());
                dataDispalies.add(dispaly);
            }
            return dataDispalies;
        }
        return null;

    }

}

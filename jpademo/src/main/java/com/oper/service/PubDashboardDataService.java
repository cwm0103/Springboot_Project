package com.oper.service;

import com.oper.dto.DashboardDataDTO;
import com.oper.entity.PubDashboardData;
import com.oper.repository.PubDashboardDataRepository;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * cwm
 * add
 * 2018-8-8
 * 数据服务
 */
@Service
@Slf4j
public class PubDashboardDataService {

    @Autowired
    private PubDashboardDataRepository pubDashboardDataRepository;

    /**
     * 数据添加的方法
     * @param pbd
     * @return
     */
    public PubDashboardData add(PubDashboardData pbd)
    {
        try{
            PubDashboardData save = pubDashboardDataRepository.save(pbd);
            if(save!=null)
            {
                return save;
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @Transactional
    public boolean del(Integer id)
    {
        try{
            pubDashboardDataRepository.deletePubDashboardDataByDataId(id);
            return true;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return false;
        }
    }

    /**
     * 数据修改
     * @param pbd
     * @return
     */
    @Transactional
    @Modifying
    public boolean update(PubDashboardData pbd)
    {
        try{
            PubDashboardData pbdid = pubDashboardDataRepository.findPubDashboardDataByDataId(pbd.getDataId());
            if(pbdid!=null)
            {
                pbdid.setDataName(pbd.getDataName());
                pbdid.setCode(pbd.getCode());
                pbdid.setColor(pbd.getColor());
                pbdid.setDashboardId(pbd.getDashboardId());
                pbdid.setUnit(pbd.getUnit());
                pbdid.setAxis(pbd.getAxis());
                pbdid.setAxisIndex(pbd.getAxisIndex());
                pbdid.setChartType(pbd.getChartType());
                pbdid.setInstanceCode(pbd.getInstanceCode());
                pbdid.setDataType(pbd.getDataType());
                return true;
            }
            return false;
        }catch (Exception ex)
        {
            return false;
        }
    }

    /**
     * 根据
     * @param
     * @return
     */
    public List<PubDashboardData> getListByDid(Integer did)
    {
        try{
            List<PubDashboardData> pubDashboardDataByDashboardIdIs = pubDashboardDataRepository.findPubDashboardDataByDashboardIdIs(did);
            if(pubDashboardDataByDashboardIdIs!=null)
            {
                return pubDashboardDataByDashboardIdIs;
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 根据
     * @param did 来获取所有的数据关联出对象名
     * @return
     */
    public List<DashboardDataDTO> getDtoListByDid(Integer did)
    {
        try{
            List<DashboardDataDTO> pubDashboardDataByDashboardIdIs = pubDashboardDataRepository.getPubDashboardDataDtoByDashboardIdIs(did);
            if(pubDashboardDataByDashboardIdIs!=null)
            {
                return pubDashboardDataByDashboardIdIs;
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }


    /**
     * 根据ID获取对象
     * @param id
     * @return
     */
    public PubDashboardData getPubDashboardDataById(Integer id)
    {
        try{
            PubDashboardData pubDashboardData = pubDashboardDataRepository.findPubDashboardDataByDataId(id);

            return pubDashboardData;
        }catch (Exception ex)
        {
            return null;
        }
    }
}

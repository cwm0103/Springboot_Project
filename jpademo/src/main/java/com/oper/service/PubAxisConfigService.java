package com.oper.service;

import java.util.List;

import com.oper.entity.PubAxisConfig;
import com.oper.repository.PubAxisConfigRepository;
import com.oper.util.ForeignResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 由MyBatis Generator工具自动生成
 */
@Service
@Slf4j
public class PubAxisConfigService {
    @Autowired
    private PubAxisConfigRepository pubAxisConfigRepository;

    /**
     * 根据主键id删除一条数据
     * @param id
     * @return
     */
    @Transactional
    @Modifying
    public Boolean DeleteById(Integer id)
    {
        try{
            Object o = pubAxisConfigRepository.deletePubAxisConfigByAxisId(id);
            if(o!=null)return true;
            return false;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return false;
        }
    }

    /**
     * 添加一条数据
     * @param pubAxisConfig
     * @return
     */
    public PubAxisConfig add(PubAxisConfig pubAxisConfig)
    {
        try{
            PubAxisConfig save = pubAxisConfigRepository.save(pubAxisConfig);
            return  save;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 修改一条数据
     * @param pubAxisConfig
     * @return
     */
    @Modifying
    @Transactional
    public  Boolean update(PubAxisConfig pacg)
    {
        try{
            PubAxisConfig axid = pubAxisConfigRepository.findPubAxisConfigByAxisId(pacg.getAxisId());
            if(axid!=null)
            {
                axid.setAxisInterval(pacg.getAxisInterval());
                axid.setAxisMax(pacg.getAxisMax());
                axid.setAxisMin(pacg.getAxisMin());
                axid.setAxisName(pacg.getAxisName());
                axid.setAxisOffset(pacg.getAxisOffset());
                axid.setAxisOrder(pacg.getAxisOrder());
                axid.setAxisPosition(pacg.getAxisPosition());
                axid.setAxisType(pacg.getAxisType());
                axid.setDashboardId(pacg.getDashboardId());
                axid.setXory(pacg.getXory());
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
     * 根据did来获取坐标轴
     * @param did
     * @return
     */
    public List<PubAxisConfig> getListByDid(Integer did)
    {
        try{
            List<PubAxisConfig> byDashboardIds = pubAxisConfigRepository.findPubAxisConfigsByDashboardId(did);
            return byDashboardIds;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 根据主键Id来获取一条记录
     * @param id
     * @return
     */
    public PubAxisConfig getByIdOne(Integer id)
    {
        try{
            PubAxisConfig pubAxisConfigByAxisId = pubAxisConfigRepository.findPubAxisConfigByAxisId(id);
            return pubAxisConfigByAxisId;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }
}
package com.oper.service;

import com.oper.entity.PubView;
import com.oper.repository.PubViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PubViewService {

    @Autowired
    private PubViewRepository pubViewRepository;

    /**
     * 添加视图
     * @param pvw
     * @return
     */
    public PubView add(PubView pvw) throws Exception {
        if(pubViewRepository.existsByViewName(pvw.getViewName()))
        {
            throw new Exception("视图名称重复");
        }
        try{
           return pubViewRepository.save(pvw);
        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 删除视图
     * @param id
     */
    @Transactional
    public boolean del(Integer id)
    {
        try {
            Object o = pubViewRepository.deletePubViewByViewId(id);
            return true;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return false;
        }
    }

    /**
     * 修改视图
     * @param pvw
     * @return
     */
    @Transactional
    @Modifying
    public boolean update(PubView pvw) throws Exception {
        if(pubViewRepository.existsByViewNameAndViewIdNot(pvw.getViewName(),pvw.getViewId())){
            throw  new Exception("视图名称不能重复");
        }
        try
        {
            PubView pvwid = pubViewRepository.findPubViewByViewId(pvw.getViewId());
            if(pvwid!=null)
            {
                pvwid.setDirectoryId(pvw.getDirectoryId());
                pvwid.setTimeType(pvw.getTimeType());
                pvwid.setViewName(pvw.getViewName());
                pvwid.setViewDesc(pvw.getViewDesc());
            }
            return true;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return false;
        }
    }

    /**
     * 根据Did来查询所有的视图
     * @param did
     * @return
     */
    public List<PubView> getListPubViewBydid(Integer did)
    {
        try{
            List<PubView> allByDirectoryId = pubViewRepository.findAllByDirectoryId(did);
            if(allByDirectoryId!=null)
            {
                return allByDirectoryId;
            }
            return null;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 根据主键id来获取一个视图
     * @param id
     * @return
     */
    public PubView getPubViewByIdOne(Integer id)
    {
        try{
            PubView pubViewByViewId = pubViewRepository.findPubViewByViewId(id);
            if(pubViewByViewId!=null)return pubViewByViewId;
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 根据视图id来查找视图，并修改目录id
     * @param vid
     * @param did
     * @return
     */
    @Transactional
    @Modifying
    public Boolean uodatePid(Integer vid,Integer did)
    {
        try
        {
            //根据vid查找视图
            PubView pubViewByViewId = pubViewRepository.findPubViewByViewId(vid);
            if(pubViewByViewId!=null)
            {
                pubViewByViewId.setDirectoryId(did);
                return true;
            }
            return false;
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            return false;
        }
    }

    public PubView findTimeTypesByDid(Integer did)
    {
        try{

            PubView timeTypesDTO = pubViewRepository.findTimeTypesByDid(did) ;
            if(timeTypesDTO!=null)
            {
                return timeTypesDTO;
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

}

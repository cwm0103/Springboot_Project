package com.oper.service;

import com.oper.dto.PubDirectoryVModel;
import com.oper.entity.PubDirectory;
import com.oper.entity.PubView;
import com.oper.repository.PubDirectoryRepository;
import com.oper.repository.PubViewRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: jindb
 * @Date: 2018/8/7 15:40
 * @Description:
 */
@Service
@Slf4j
public class PubDirectoryService {

    @Autowired
    PubDirectoryRepository _pubDirectoryRepository;
    @Autowired
    PubViewRepository pubViewRepository;

    /**
     * 添加目录
     * @param pubDirectory
     * @return
     * @throws Exception
     */
    public PubDirectory add(PubDirectory pubDirectory) throws Exception {
        //添加返回值
        PubDirectory save=null;
        if(_pubDirectoryRepository.existsByDirectoryName(pubDirectory.getDirectoryName())){
            throw new Exception("目录名重复！");
        }else{
             save = _pubDirectoryRepository.save(pubDirectory);

        }
        return save;
    }

    /**
     * 更新目录
     * @param pubDirectory
     * @return
     * @throws Exception
     */
    public boolean update(PubDirectory pubDirectory) throws Exception {
        Integer id=pubDirectory.getDirectoryId();
        if(id==null || id==0){
            throw new Exception("id is null or zeor");
        }
        if(_pubDirectoryRepository.existsByDirectoryNameAndDirectoryIdIsNot(pubDirectory.getDirectoryName(),id)){
            throw new Exception("目录名已存在！");
        }else{
            _pubDirectoryRepository.updateOne(pubDirectory.getDirectoryName(),pubDirectory.getDirectoryDesc(),id);
        }
        return true;
    }

    /**
     * 移动位置
     * @param pid
     * @param id
     * @return
     */
    public boolean move(Integer pid ,Integer id){
        try{
            _pubDirectoryRepository.move(pid,id);
        }catch (Exception ex){
            throw ex;
        }
        return  true;
    }

    /**
     * 返回所有目录
     * @return
     */
    public List<PubDirectory> findAll(){
       return  _pubDirectoryRepository.findAll();
    }

    /**
     * 删除目录
     * @param id
     * @return
     */
    public boolean del(Integer id){
        _pubDirectoryRepository.deleteById(id);
        return true;
    }

    /**
     * 根据Id来查询一个目录
     * @param id
     * @return
     */
    public PubDirectory getByidOne(Integer id)
    {
        try {
            PubDirectory pubDirectoryByDirectoryId = _pubDirectoryRepository.findPubDirectoryByDirectoryId(id);
            if(pubDirectoryByDirectoryId!=null)
            {
                return pubDirectoryByDirectoryId;
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 获取所有的目录数和下面的视图
     * @return
     */
    public List<PubDirectoryVModel> getAllDirList()
    {
        List<PubDirectoryVModel> pdvm=new LinkedList<>();
        try
        {
            List<PubDirectory> all = _pubDirectoryRepository.findAll();
            if(all!=null)
            {
                all.forEach(pd->{
                    List<PubView> allByDirectoryIdIs = pubViewRepository.findAllByDirectoryIdIs(pd.getDirectoryId());
                    PubDirectoryVModel pubDirectoryVModel=new PubDirectoryVModel();
                    pubDirectoryVModel.setPubViews(allByDirectoryIdIs);
                    pubDirectoryVModel.setDirectoryDesc(pd.getDirectoryDesc());
                    pubDirectoryVModel.setDirectoryId(pd.getDirectoryId());
                    pubDirectoryVModel.setDirectoryName(pd.getDirectoryName());
                    pubDirectoryVModel.setPid(pd.getPid());
                    pdvm.add(pubDirectoryVModel);
                });
                return pdvm;
            }
            return null;
        }catch (Exception ex)
        {
            log.error(ex.getMessage());
            return null;
        }
    }

}

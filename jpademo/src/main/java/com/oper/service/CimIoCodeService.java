package com.oper.service;

import com.alibaba.fastjson.JSONObject;
import com.oper.dto.CimInstanceDTO;
import com.oper.entity.CimIoCode;
import com.oper.repository.CimIoCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * cwm
 * 2018-8-17
 * 创建cimiocode
 */
@Service
@Slf4j
public class CimIoCodeService {

    @Autowired
    private CimIoCodeRepository cimIoCodeRepository;


    /**
     *根据name和code来模糊查询IO点
     * @param code
     * @param name
     * @return
     */
    public List<CimIoCode> getListLikeCodeOrName(String code,String name)
    {
        try{
            List<CimIoCode> allByCimCodeIsLikeOrCimNameLike = cimIoCodeRepository.findAllByCimCodeIsLikeOrCimNameIsLike(code, name);
            if(allByCimCodeIsLikeOrCimNameLike!=null)
                return allByCimCodeIsLikeOrCimNameLike;
            return null;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 根据名称模糊查询获取设备实例对像信息
     * @param name
     * @return
     */
    public List<JSONObject> getCimInstanceList(String name){

        List<CimInstanceDTO> instanceDTOS=  null;
        if(StringUtils.isNotBlank(name)){
            instanceDTOS=cimIoCodeRepository.findAllByCimName(name);
        }else{
            instanceDTOS=cimIoCodeRepository.findAllCimInstance();
        }

        List<JSONObject> result=new ArrayList<>();
        if(instanceDTOS!=null && instanceDTOS.size()>0){
            List<String> codes=new ArrayList<>();
            //获取设备类信息
            List<CimInstanceDTO> cimClasses= instanceDTOS.parallelStream().filter(x->{
                boolean flag=!codes.contains(x.getClassCode());
                codes.add(x.getClassCode());
                return  flag;
            }).collect(Collectors.toList());
            //获取类下实例信息
            for (CimInstanceDTO classcodes: cimClasses) {
                JSONObject classObject=new JSONObject();
                classObject.put("code",classcodes.getClassCode());
                classObject.put("name",classcodes.getCimName());
                List<JSONObject> childrens=new ArrayList<>();
                for (CimInstanceDTO instance: instanceDTOS) {
                    if(instance.getClassCode().equals(classcodes.getClassCode())){
                        JSONObject instanceObj=new JSONObject();
                        instanceObj.put("code",instance.getInstanceCode());
                        instanceObj.put("name",instance.getInstanceName());
                        childrens.add(instanceObj);
                    }
                }
                classObject.put("children",childrens);
                result.add(classObject);
            }
        }
        return  result;
    }

    /**
     * 根据实例code获取CIM属性信息及公式信息
     * @param code
     * @return
     */
    public List<CimIoCode> getCimIOCodeByObjectCode(String code){
        List<CimIoCode> cimIoCodeList=cimIoCodeRepository.findAllByObjectCode(code);
        List<CimIoCode> cimIoCodeListFun=cimIoCodeRepository.findAllByObjectCodeFun(code);
        if(cimIoCodeList!=null){
            cimIoCodeList.addAll(cimIoCodeListFun);
        }else
        {
           return cimIoCodeListFun;
        }
        return  cimIoCodeList;
    }
}

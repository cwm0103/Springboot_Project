package com.oper.repository;

import com.oper.dto.CimInstanceDTO;
import com.oper.entity.CimIoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * create by cwm 2018/8/17
 */
public interface CimIoCodeRepository extends JpaRepository<CimIoCode,Integer> {

    /**
     * 根据code 和name 模糊查询
     * @param code
     * @param name
     * @return
     */

    //List<CimIoCode> findAllByCimCodeEqualsOrCimNameEquals(String code,String name);
    @Query(value = "select * from (\n" +
            "select a.id, a.cim_code, a.cim_name, \n" +
            "a.class_code, a.code_type, a.create_time,\n" +
            " a.initial_value, a.is_math_code,\n" +
            " a.is_normal, a.lower_limit, a.object_code, \n" +
            "a.sc_id, a.scada_code, a.unit, a.up_limit,b.equ_name as io_code from cim_io_code a join bas_equ_info b on a.object_code=b.obj_code \n" +
            "UNION \n" +
            "select a.id, a.cim_code, a.cim_name, \n" +
            "a.class_code, a.code_type, a.create_time,\n" +
            " a.initial_value, a.is_math_code,\n" +
            " a.is_normal, a.lower_limit, a.object_code, \n" +
            "a.sc_id, a.scada_code, a.unit, a.up_limit,b.organization_name as io_code  from cim_io_code a join pub_organization_info b on a.object_code=b.organization_no \n" +
            ") t where cim_code  ilike ?1 or cim_name ilike ?2", nativeQuery = true)
    List<CimIoCode> findAllByCimCodeIsLikeOrCimNameIsLike(String code,String name);

    @Query(value = "SELECT new com.oper.dto.CimInstanceDTO(pi.instanceCode,pi.instanceName,pi.classCode,cc.cimName) FROM PubCimInstance  pi JOIN  PubCimClass cc on pi.classCode=cc.cimCode WHERE pi.instanceName like %?1%")
    List<CimInstanceDTO> findAllByCimName(String cimName);

    @Query(value = "SELECT new com.oper.dto.CimInstanceDTO(pi.instanceCode,pi.instanceName,pi.classCode,cc.cimName) FROM PubCimInstance  pi JOIN  PubCimClass cc on pi.classCode=cc.cimCode ")
    List<CimInstanceDTO> findAllCimInstance();

  	@Query(value = "SELECT new com.oper.entity.CimIoCode(ci.cimCode,ci.cimName,ci.objectCode,ci.unit, case when ci.codeType=1 then 1 else 2 end ) FROM CimIoCode ci WHERE  ci.objectCode=?1")
    List<CimIoCode> findAllByObjectCode(String objectCode);
    @Query(value = "SELECT new com.oper.entity.CimIoCode(ci.code,ci.name,ci.instancecode,ci.unit, case when ci.valueway='1' then 1 else 2 end ) FROM PolyFunCode ci WHERE  ci.codetype='2' And ci.instancecode=?1")
    List<CimIoCode> findAllByObjectCodeFun(String objectCode);


}

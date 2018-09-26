package com.oper.dto;

import com.oper.entity.CimIoCode;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Auther: jindb
 * @Date: 2018/9/15 11:12
 * @Description:
 */
@Data
@Entity
@Table(name="pub_cim_instance")
public class CimInstanceDTO implements Serializable  {
    @Id
    private String instanceCode;
    private String instanceName;
    private  String classCode;
    private String cimName;

    public CimInstanceDTO(String instanceCode, String instanceName, String classCode, String cimName) {
        this.instanceCode = instanceCode;
        this.instanceName = instanceName;
        this.classCode = classCode;
        this.cimName = cimName;
    }
}

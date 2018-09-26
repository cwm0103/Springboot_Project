package com.oper.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "cim_io_code")
@Data
public class CimIoCode {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "menuSeq")
    @SequenceGenerator(name = "menuSeq", initialValue = 1, allocationSize = 1, sequenceName = "cim_io_code_id_seq")
    private  Integer id;

    /**
     * 编码
     */
    private String cimCode;

    /**
     * 名称
     */
    private String cimName;

    /**
     * io点
     */
    private String ioCode;

    /**
     * 类名
     */
    private String classCode;

    /**
     *对像名
     */
    private String objectCode;

    /**
     * 单位
     */
    private String unit;

    /**
     * 初始值
     */
    private Double initialValue;

    /**
     * SCADA所属
     */
    private String scadaCode;

    /**
     * 测点类型
     */
    private Integer codeType;

    /**
     * 上限
     */
    private Double upLimit;

    /**
     * 下限
     */
    private Double lowerLimit;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    private Integer isMathCode;

    private Integer scId;

    private Integer isNormal;


    public CimIoCode(String cimCode, String cimName, String objectCode, String unit,Integer codeType) {
        this.cimCode = cimCode;
        this.cimName = cimName;
        this.objectCode = objectCode;
        this.unit = unit;
        this.codeType=codeType;
    }
}

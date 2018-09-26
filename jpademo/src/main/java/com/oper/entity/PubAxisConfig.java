package com.oper.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pub_axis_config")
@Data
public class PubAxisConfig {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "menuSeq")
    @SequenceGenerator(name = "menuSeq", initialValue = 1, allocationSize = 1, sequenceName = "pub_axis_config_axis_id_seq")
    private Integer axisId;

    private Integer dashboardId;

    private String axisType;

    private String axisName;

    private Integer axisMin;

    private Integer axisMax;

    private String axisPosition;

    private Short axisOffset;

    private Short axisInterval;

    @Size(min = 1,max = 2,message = "xory字段不能大于2个字符")
    private String xory;

    private Short axisOrder;
}
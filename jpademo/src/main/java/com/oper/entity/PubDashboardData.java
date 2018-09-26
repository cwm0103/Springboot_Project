package com.oper.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pub_dashboard_data")
@Data
public class PubDashboardData {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "menuSeq")
    @SequenceGenerator(name = "menuSeq", initialValue = 1, allocationSize = 1, sequenceName = "pub_dashboard_data_data_id_seq")
    private Integer dataId;

    /**
     * 图表Id
     */
    private Integer dashboardId;

    /**
     * 测点code
     */
    private String code;

    /**
     * 显示名称
     */
    private String dataName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 图表类型 line,bar
     */
    private String chartType;

    /**
     * 颜色
     */
    private String color;

    /**
     * 坐标轴 x,y
     */
    private String axis;

    /**
     * 坐标轴序号
     */
    private Short axisIndex;

    /**
     * 实例code
     */
    private String instanceCode;
    /**
     * 1:瞬时量;2:累计量
     */
    private Integer dataType;
}
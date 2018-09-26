package com.oper.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pub_dashboard_config")
@Data
public class PubDashboardConfig {
    /**
     * null
     */
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "menuSeq")
    @SequenceGenerator(name = "menuSeq", initialValue = 1, allocationSize = 1, sequenceName = "pub_dashboard_config_dashboard_id_seq")
    private Integer dashboardId;

    /**
     * 标题
     */

    private String dashboardTitle;

    /**
     * 描述
     */
    private String dashboardDesc;

    /**
     * 视图ID
     */
    private Integer viewId;

    /**
     * 类型
     */
    private String chartType;

    /**
     * 定位
     */
    private String dashboardPosition;

    /**
     * 大小
     */
    private String dashboardSize;

    /**
     * 培数
     */
    private Short number;

    /**
     * null
     */
    private String timeType;

    /**
     * 参数信息
     */
    private String paramInfo;

    /**
     * x轴
     */
    private Integer  dasx;
    /**
     * y轴
     */
    private Integer dasy;

    /**
     * 值类型
     */
    private String valueType;
}
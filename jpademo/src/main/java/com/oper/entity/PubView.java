package com.oper.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pub_view")
@Data
public class PubView {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "menuSeq")
    @SequenceGenerator(name = "menuSeq", initialValue = 1, allocationSize = 1, sequenceName = "pub_view_view_id_seq")
    private Integer viewId;

    /**
     * 视图名称
     */
    private String viewName;

    /**
     * 时间类型;日，月，年，总
     */
    private String timeType;

    /**
     * 目录ID
     */
    private Integer directoryId;
    /**
     * 描述
     */
    private String viewDesc;
}
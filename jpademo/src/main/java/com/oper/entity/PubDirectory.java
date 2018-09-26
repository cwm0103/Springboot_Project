package com.oper.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "pub_directory")
@Data
public class PubDirectory {
    /**
     * null
     */
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "pub_directory_id_seq")
    @SequenceGenerator(name = "pub_directory_id_seq", initialValue = 1, allocationSize = 1, sequenceName = "pub_directory_directory_id_seq")
    private Integer directoryId;

    /**
     * 名称
     */
    @NotEmpty(message = "目录名不能为空！")
    private String directoryName;

    /**
     * 描述
     */
    private String directoryDesc;

    /**
     * 父ID
     */
    private Integer pid;
}
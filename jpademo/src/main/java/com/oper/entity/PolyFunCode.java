package com.oper.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: jindb
 * @Date: 2018/9/18 09:00
 * @Description:
 */
@Data
@Entity
@Table(name="poly_fun_code")
public class PolyFunCode {
    @Id
    private String code;
    private String name;
    private String unit;
    private String valueway;
    private String codetype;
    private String instancecode;

}

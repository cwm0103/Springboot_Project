package com.bjly.webentity.financeManage;

import javax.persistence.*;
import lombok.*;

@Table(name = "ly_test")
@Data
public class LyTest {
    /**
     * 
     */
    @Id
    private Integer id;

    /**
     * 
     */
    private String name;
}
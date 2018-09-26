package com.bjly.webentity.userManage;

import javax.persistence.*;
import lombok.*;

@Table(name = "ly_role")
@Data
public class LyRole {
    /**
     * 
     */
    private Integer rId;

    /**
     * 
     */
    private String rName;

    /**
     * 
     */
    private String rDesc;
}
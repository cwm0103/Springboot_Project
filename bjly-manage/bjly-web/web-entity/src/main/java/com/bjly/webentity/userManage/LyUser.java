package com.bjly.webentity.userManage;

import javax.persistence.*;
import lombok.*;

@Table(name = "ly_user")
@Data
public class LyUser {
    /**
     * 
     */
    @Id
    private Integer uId;

    /**
     * 
     */
    private String uName;

    /**
     * 
     */
    private String uSex;

    /**
     * 
     */
    private Integer uAge;

    /**
     * 
     */
    private Integer uEducation;

    /**
     * 
     */
    private String uTel;

    /**
     * 
     */
    private String uPhoto;

    /**
     * 
     */
    private String uCode;

    /**
     * 
     */
    private String uPwd;
}
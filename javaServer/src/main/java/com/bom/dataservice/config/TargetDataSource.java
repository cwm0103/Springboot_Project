package com.bom.dataservice.config;

/**
 * Created by chenwangming on 2017/11/14.
 */

import java.lang.annotation.*;

/**
 *在方法上使用，用于指定使用哪个数据源
 *@author 陈王明
 *@version v.0.1
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value();
}

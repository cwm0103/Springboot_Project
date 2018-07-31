package com.bjly.webapi.config;

public class AppConstants {

    private AppConstants() {

    }

    public static final String DATA_SOURCE_PREfIX_CUSTOM="spring.custom.datasource.";

    public static final String DATA_SOURCE_CUSTOM_NAME="name";

    public static final String SEP = ",";
    public static final String DRUID_SOURCE_PREFIX = "spring.datasource.druid.";

    public static final String DATA_SOURCE_TYPE = "type";
    public static final String DATA_SOURCE_DRIVER = "driver-class-name";
    public static final String DATA_SOURCE_URL = "url";
    public static final String DATA_SOURCE_USER_NAME = "username";
    public static final String DATA_SOURCE_PASSWORD = "password";

    public static final String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";


    public static final  String RESULT_CONSTANTS = "result";
    public static final  String SUCCESS_RESULT = "SUCCESS";
    public static final  String FAIL_RESULT = "FAIL";
    public static final  String MESSAGE_CONSTANTS = "message";

    public static final String STUDENTS_NODE_NAME = "students";
    public static final String STUDENT_NODE_NAME = "student";

    public static final String ID_ATTRIBUTE_NAME = "id";
    public static final String NAME_ATTRIBUTE_NAME = "name";
    public static final String CLASS_ATTRIBUTE_NAME = "className";
    public static final String CRAETE_DATE_ATTRIBUTE_NAME = "createDate";
    public static final String UPDATE_DATE_ATTRIBUTE_NAME = "updateDate";
    public static final String ENABLED_ATTRIBUTE_NAME = "enabled";
    public static final String DB_TYPE = "dbType";







}

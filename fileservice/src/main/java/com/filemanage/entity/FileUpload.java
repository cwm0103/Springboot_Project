package com.filemanage.entity;

import java.util.Date;

/**
 * Created by chenwangming on 2017/8/30.
 * 文件上传实体
 */
public class FileUpload {

    //region old
//    private  int file_id;
//    private String file_name;
//    private String file_path;
//    private String file_type;
//    private String file_size;
//    private int upload_loginid;
//    private Date upload_time;
//    private char flag;
//    private String file_tags;
//    private String category;
//
//    public int getFile_id() {
//        return file_id;
//    }
//
//    public void setFile_id(int file_id) {
//        this.file_id = file_id;
//    }
//
//    public String getFile_name() {
//        return file_name;
//    }
//
//    public void setFile_name(String file_name) {
//        this.file_name = file_name;
//    }
//
//    public String getFile_path() {
//        return file_path;
//    }
//
//    public void setFile_path(String file_path) {
//        this.file_path = file_path;
//    }
//
//    public String getFile_type() {
//        return file_type;
//    }
//
//    public void setFile_type(String file_type) {
//        this.file_type = file_type;
//    }
//
//    public String getFile_size() {
//        return file_size;
//    }
//
//    public void setFile_size(String file_size) {
//        this.file_size = file_size;
//    }
//
//    public int getUpload_loginid() {
//        return upload_loginid;
//    }
//
//    public void setUpload_loginid(int upload_loginid) {
//        this.upload_loginid = upload_loginid;
//    }
//
//    public Date getUpload_time() {
//        return upload_time;
//    }
//
//    public void setUpload_time(Date upload_time) {
//        this.upload_time = upload_time;
//    }
//
//    public char getFlag() {
//        return flag;
//    }
//
//    public void setFlag(char flag) {
//        this.flag = flag;
//    }
//
//    public String getFile_tags() {
//        return file_tags;
//    }
//
//    public void setFile_tags(String file_tags) {
//        this.file_tags = file_tags;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
    //endregion


    //region new

    private int file_id;
    private String file_name;
    private  String file_save_name;
    private String file_type;
    private long file_size;
    private Date file_create_time;
    private String file_path;
    private String category;

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_save_name() {
        return file_save_name;
    }

    public void setFile_save_name(String file_save_name) {
        this.file_save_name = file_save_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public Date getFile_create_time() {
        return file_create_time;
    }

    public void setFile_create_time(Date file_create_time) {
        this.file_create_time = file_create_time;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //endregion
}

package com.filemanage.dao;


import com.filemanage.entity.FileUpload;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by chenwangming on 2017/8/30.
 * 文件操作
 */
@Mapper
public interface UploadFileDao {
    /**
     * 添加上传文件
     * @param fileupload
     * @return
     */
   public int adduploadfile(FileUpload fileupload);

    /**
     * 删除文件
     * @param fileid
     * @return
     */
    public int deleteuploadfile(int fileid);

    /**
     * 获取文件
     * @param fileids
     * @return
     */
    public List<FileUpload> getfiles(List fileids);

    /**
     * 根据文件id获取一个文件
     * @param fileid
     * @return
     */
    public FileUpload getFileUpload(int fileid);

    /**
     *查询所有的文件
     * @return
     */
    public List<FileUpload> getList();
}

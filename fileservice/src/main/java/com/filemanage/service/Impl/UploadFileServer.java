package com.filemanage.service.Impl;


import com.filemanage.dao.UploadFileDao;
import com.filemanage.entity.FileUpload;
import com.filemanage.service.IUploadFileServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenwangming on 2017/8/30.
 */
@Service
public class UploadFileServer implements IUploadFileServer {

    @Autowired
    private UploadFileDao uploadFileDao;
    @Override
    public int adduploadfile(FileUpload fileupload) {
        return uploadFileDao.adduploadfile(fileupload);
    }

    @Override
    public int deleteuploadfile(int fileid) {
        return uploadFileDao.deleteuploadfile(fileid);
    }

    @Override
    public List<FileUpload> getfiles(List fileids) {
        return uploadFileDao.getfiles(fileids);
    }

    @Override
    public FileUpload getFileUpload(int fileid) {
        return uploadFileDao.getFileUpload(fileid);
    }

    @Override
    public List<FileUpload> getList() {
        return uploadFileDao.getList();
    }
}

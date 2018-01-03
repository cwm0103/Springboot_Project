package com.filemanage.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.filemanage.entity.FileUpload;
import com.filemanage.service.IUploadFileServer;
import com.filemanage.tools.FileHelper;
import com.filemanage.tools.ImageHelper;
import com.filemanage.tools.VerifyCodeUtils;
import com.google.gson.Gson;
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.FileUploadException;
//import org.apache.tomcat.util.http.fileupload.RequestContext;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import redis.clients.jedis.Jedis;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by chenwangming on 2017/9/11.
 * 文件服务起接口
 * 提供所有的文件上传下载功能
 * 提供所有的图片处理功能
 */
@Controller
@RequestMapping("/file")
public class FileInterfaceController {

    //在声明实例时创建自定义级别标签，MOBITOR就是自定义的标签。
    Logger monitorLogger = LoggerFactory.getLogger("monitor");

    @Value("${weburl}")
    private  String weburl;

    @Value("${serverPath}")
    private String serverPathUrl;

    @Value("${loadPath}")
    private String  loadPath;

    @Autowired
    private IUploadFileServer uploadFileServer;
    //region 文件上传

    /**
     * 文件上传
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
   public Map<String,Object> upload(HttpServletRequest request,HttpServletResponse response)
     {
         System.out.println("文件上传");
        Map<String,Object> map=new HashMap<String,Object>();
        //region 注释
        /**
         * 注意getFile方法不会返回一个Java.io.File的实例，而是返回org.springframework.web.multipart.MultipartFile的一个实例，关于org.springframework.web.multipart.MultipartFile的详细信息，请参考清单7-34。如果在请求中没有找到文件则getFile方法返回null。

         清单7-34 org.springframework.web.multipart.MultipartFile接口

         interface MultipartFile {

         public byte[] getBytes();

         public String getContentType();

         public java.io.InputStream getInputStream();

         public String getName();

         public String getOriginalFilename();

         public long getSize();

         public boolean isEmpty();

         public void transferTo(java.io.File dest);

         }

         在MultipartFile接口中定义了如下很多有用的方法。

         l  使用getSize()方法获得文件长度，以此决定允许上传的文件大小。

         l  使用isEmpty()方法判断上传文件是否为空文件，以此决定是否拒绝空文件。

         l 使用getInputStream()方法将文件读取为java.io.InputStream流对象。

         l  使用getContentType()方法获得文件类型，以此决定允许上传的文件类型。

         l  使用transferTo（dest）方法将上传文件写到服务器上指定的文件。
         */
//endregion

        //保存的服务器路径
        String serverPath=serverPathUrl;
        String result="";//消息显示
        String savePath="";//保存文件路径
        StandardMultipartHttpServletRequest req=(StandardMultipartHttpServletRequest) request;
     // String s =  req.getAttribute("userName").toString();
        try {
            Iterator<String> iterator = req.getFileNames();//迭代器
            while (iterator.hasNext())//判断容器是否还有可以选择的内容
            {
                MultipartFile file= req.getFile(iterator.next());//获取文件
                byte[] bytes = file.getBytes();//获取文件的字节数组
                String contentType = file.getContentType();//获取文件的类型
                InputStream in = file.getInputStream();//获取文件流
                String name = file.getName();//获取控件的文件名称
                String originalFilename = file.getOriginalFilename();//获取原文件名称
                //判断扩展名
                String[] extension={".jpg",".png",".jpeg",".bmp"};
                String[] extension1={".docx",".xlsx",".pptx"};
                boolean isext= FileHelper.fileExtension(originalFilename,extension);
//                if(!isext)
//                {
//                    map.put("no","扩展名不匹配！");
//                    return map;
//                }
                int split = originalFilename.lastIndexOf(".");
                //文件扩展名
                String ext = originalFilename.substring(split + 1, originalFilename.length());
                long size = file.getSize();//获取文件大小
                //设置文件大小
//                boolean isSize = FileHelper.fileSize(size, 1000000);
//                if(!isSize)
//                {
//                    map.put("no","文件大小超出了范围！");
//                    return map;
//                }
                boolean empty = file.isEmpty();//判断文件是否为空
                //设置上传文件名称的唯一性
                String newfileName = FileHelper.makeFileName(originalFilename);
                //制造新保存路径
                Map<String,String> map1=FileHelper.makePath(newfileName,serverPath);
                savePath=map1.get("url_all");



                //int index = savePath.lastIndexOf(".");
                //String newFile=savePath.substring(0,index)+"_100x100"+savePath.substring(index, savePath.length());
                //创建一个输出流
                FileOutputStream out=new FileOutputStream(savePath);
                //创建一个缓存区
                byte[] buffer=new byte[1024];
                //判断输入流中的数据是否已经读完的标识
                int len=0;
                //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                while ((len=in.read(buffer))>0)
                {
                    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                    out.write(buffer,0,len);
                }
                //关闭输入流
                in.close();
                //关闭输出流
                out.close();

                //region 保存到数据库
                    String savePathWeb= map1.get("url_has") +"/"+newfileName;
                    FileUpload fil=new FileUpload();
                    fil.setFile_name(originalFilename);
                    fil.setCategory("");
                    fil.setFile_path(savePathWeb);
                    fil.setFile_create_time(new Date());
                    fil.setFile_size(size);
                    fil.setFile_type(ext);
                    fil.setFile_save_name(newfileName);
                    Integer id=uploadFileServer.adduploadfile(fil);
                    map.put("ok",fil);

                //endregion
                //region  图片压缩
//                    ImageHelper imge=new ImageHelper(savePath,newFile);
//                    imge.resizeFix(100, 100);
                //endregion
            }
        } catch (Exception e) {
            monitorLogger.info(new Date()+"==upload==文件上传=="+e.getMessage());
            map.put("no","上传出错！");

            e.printStackTrace();
        }
        return map;
    }

    /**
     * 文件删除
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request)
    {
        Map<String,Object> map=new HashMap<String ,Object>();
        try {
            Integer file_id=Integer.parseInt(request.getParameter("fileid"));

            //根据id查询路径
            FileUpload fileUpload = uploadFileServer.getFileUpload(file_id);
            if(fileUpload!=null)
            {
                boolean flag=   FileHelper.deleteFile(fileUpload.getFile_path());
            }
            int deleteuploadfile = uploadFileServer.deleteuploadfile(file_id);
            if(deleteuploadfile>0)
            {
                map.put("ok",deleteuploadfile);
            }else
            {
                map.put("no","数据库删除文件失败！");
            }

        } catch (NumberFormatException e) {
            monitorLogger.info(new Date()+"==delete==文件删除=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    //endregion

    //region 文件下载

    @RequestMapping("/getfileinfo")
    @ResponseBody
    public FileUpload GetFileInfo(int fileId)
    {
        Map<String,Object> map=new HashMap<String ,Object>();
        try {
            //Integer file_id=Integer.parseInt(request.getParameter("fileid"));
            if(fileId>0) {
                //根据id查询路径
                FileUpload fileUpload = uploadFileServer.getFileUpload(fileId);
                return fileUpload;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件下载
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/downLoad")
    @ResponseBody
    public Map<String,Object> downLoad(HttpServletRequest request,HttpServletResponse response) throws IOException {

        Map<String,Object> map= null;
        try {
            map = new HashMap<String ,Object>();
            //得到要下载的文件名
            request.setCharacterEncoding("UTF-8");
            String fileName = request.getParameter("filename");
            //fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
            //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
            String fileSaveRootPath=  loadPath;//this.getServletContext().getRealPath("/WEB-INF/upload");
            //通过文件名找出文件的所在目录
            String path = FileHelper.findFileSavePathByFileName(fileName,fileSaveRootPath);
            //得到要下载的文件
            File file = new File(path + "/" + fileName);
            //如果文件不存在
            if(!file.exists()){
                //request.setAttribute("message", "您要下载的资源已被删除！！");
                //request.getRequestDispatcher("/message.jsp").forward(request, response);
               map.put("no","您要下载的资源已被删除！！");
                monitorLogger.info(new Date()+"==downLoad==您要下载的资源已被删除");
               return map;
            }
            //处理文件名
            String realname = fileName.substring(fileName.indexOf("_")+1);
            //设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
            //读取要下载的文件，保存到文件输入流
            FileInputStream in = new FileInputStream(path + "/" + fileName);
            //创建输出流
            OutputStream out = response.getOutputStream();
            //创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            //循环将输入流中的内容读取到缓冲区当中
            while((len=in.read(buffer))>0){
                //输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
            //关闭文件输入流
            in.close();
            //关闭输出流
            out.close();
            map.put("ok","下载成功！");
        } catch (IOException e) {
            monitorLogger.info(new Date()+"==downLoad==文件下载=="+e.getMessage());
            map.put("no","下载失败！");
            e.printStackTrace();
        }
        return map;
    }
    //endregion

    //region 图片裁剪 （通用）

    /**
     * 图片裁剪 （通用）
     * @param request
     * @return
     */
    @RequestMapping("/imgcut")
    @ResponseBody
    public Map<String,Object> imgcut(HttpServletRequest request,HttpServletResponse response)
    {
        String src=request.getParameter("src");
        String x=request.getParameter("x");
        String y=request.getParameter("y");
        String w=request.getParameter("w");
        String h=request.getParameter("h");
        //region 防盗链
        boolean b=false;
        try {
            b= doFilter(request,response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //endregion
        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc=loadPath+photoName;
//        monitorLogger.info("本地地址： "+loadsrc);
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_"+x+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;

        //endregion

        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_"+x+src.substring(src.lastIndexOf('.'),src.length());
        String loadsrc=loadPath+src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=serverPathUrl+newname;
        //endregion
        //图片复制
        Map<String,Object> map=new HashMap<String ,Object>();
        boolean flag=ImageHelper.copyFile(loadsrc,targetsrc);
        if(flag)
        {
            try {
                ImageHelper.cutImage(targetsrc,targetsrc,Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h));
                monitorLogger.info("图片裁剪（通用）");
                map.put("ok",target);
            } catch (IOException e) {
                monitorLogger.info(new Date()+"==imgcut==图片裁剪（通用）=="+e.getMessage());
                map.put("no",e.getMessage());
                e.printStackTrace();
            }
        }else
        {
            monitorLogger.info("图片复制失败");
            map.put("no","图片复制失败！");
        }

        return map;
    }

    /**
     * 图片裁剪展示
     * @param request
     * @param response
     */
    @RequestMapping("/imgcut_jcrop")
    public void  imgcut_jcrop(HttpServletRequest request,HttpServletResponse response)
    {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String x= request.getParameter("x");
        String y=request.getParameter("y");
        String w=request.getParameter("w");
        String h=request.getParameter("h");

        //String path="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\img\\cutpicther.jpg";
        String path=request.getSession().getServletContext().getRealPath("img/cutpicther.jpg");
        try {
            ImageHelper.cutImage(path,response.getOutputStream(),Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h));
            monitorLogger.info("图片裁剪（通用）jcrop");
        } catch (IOException e) {
            monitorLogger.info(new Date()+"==imgcut_jcrop==图片裁剪（通用）jcrop=="+e.getMessage());
            e.printStackTrace();
        }
    }

    //endregion

    //region 图片裁剪二分之一
    @RequestMapping("/imgcutahalf")
    @ResponseBody
    public Map<String ,Object> imgcutahalf(HttpServletRequest request)
    {
        String src=request.getParameter("src");
        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\demo_img\\"+photoName;
//
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_half"+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;

        //endregion
        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_half"+src.substring(src.lastIndexOf('.'),src.length());
        String loadsrc=loadPath+src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=serverPathUrl+newname;
        //endregion

        //图片复制
        ImageHelper.copyFile(loadsrc,targetsrc);


        Map<String,Object> map=new HashMap<String,Object>();

        try {
            ImageHelper.cutHalfImage(targetsrc,targetsrc);
            monitorLogger.info("图片裁剪二分之一");
            map.put("ok",target);

        } catch (IOException e) {
            monitorLogger.info(new Date()+"==imgcutahalf==图片裁剪二分之一=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    //endregion

    //region 图片居中裁剪

    /**
     * 图片居中裁剪
     * @param request
     * @return
     */
    @RequestMapping("/imgcutcenter")
    @ResponseBody
    public Map<String ,Object> imgcutcenter(HttpServletRequest request)
    {
        String src=request.getParameter("src");
        String w=request.getParameter("w");
        String h=request.getParameter("h");

        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\demo_img\\"+photoName;
//
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_"+w+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;

        //endregion

        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_"+h+src.substring(src.lastIndexOf('.'),src.length());
        String loadsrc=loadPath+src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=serverPathUrl+newname;
        //endregion
        //图片复制
        ImageHelper.copyFile(loadsrc,targetsrc);

        Map<String ,Object> map=new HashMap<String,Object>();
        try {
            ImageHelper.cutCenterImage(targetsrc,targetsrc,Integer.parseInt(w),Integer.parseInt(h));
            monitorLogger.info("图片居中裁剪");
            map.put("ok",target);
        } catch (IOException e) {
            monitorLogger.info(new Date()+"==imgcutcenter==图片居中裁剪=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    //endregion

    //region  旋转图片
    @RequestMapping("/imgrotate")
    @ResponseBody
    public Map<String,Object> imgrotate(HttpServletRequest request)
    {
        String src=request.getParameter("src");
        String degree=request.getParameter("degree");
        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\demo_img\\"+photoName;
//
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_"+degree+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;

        //endregion

        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_"+degree+src.substring(src.lastIndexOf('.'),src.length());
        String loadsrc=loadPath+src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=serverPathUrl+newname;
        //endregion
        //图片复制
        ImageHelper.copyFile(loadsrc,targetsrc);
        Map<String,Object> map=new  HashMap<String,Object>();
        try {
            ImageHelper.rotateImage(targetsrc,Integer.parseInt(degree), java.awt.Color.gray);
            monitorLogger.info("图片旋转");
            map.put("ok",target);
        } catch (IOException e) {
            monitorLogger.info(new Date()+"==imgrotate==图片旋转=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    //endregion

    //region 图片添加水印文字

    /**
     * 图片添加水印文字
     * @param request
     * @return
     */
    @RequestMapping("/imgmarkfont")
    @ResponseBody
    public Map<String,Object> imgmarkfont(HttpServletRequest request)
    {

        String src=request.getParameter("src");
        String font=request.getParameter("font");
        String fontname=request.getParameter("fontname");
        String fontBlok=request.getParameter("fontBlok");
        String fontSize=request.getParameter("fontSize");
        String x=request.getParameter("x");
        String y=request.getParameter("y");
        String alpha=request.getParameter("alpha");

        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\demo_img\\"+photoName;
//
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_"+font+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;

        //endregion
        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_"+font+src.substring(src.lastIndexOf('.'),src.length());
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//        String loadsrc=  ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String loadsrc1=loadPath+src;
        String loadsrc= request.getContextPath() + src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=serverPathUrl+newname;
        //endregion

        //图片复制
        ImageHelper.copyFile(loadsrc,targetsrc);
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            ImageHelper.pressText(targetsrc,font,fontname,Integer.parseInt(fontBlok),Integer.parseInt(fontSize),java.awt.Color.MAGENTA,Integer.parseInt(x),Integer.parseInt(y),Float.parseFloat(alpha));
            monitorLogger.info("图片添加字体水印");
            map.put("ok",target);
        } catch (NumberFormatException e) {
            monitorLogger.info(new Date()+"==imgmarkfont==图片添加字体水印=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    //endregion

    //region 图片添加水印
    @RequestMapping("/imgmarkimg")
    @ResponseBody
    public  Map<String,Object> imgmarkimg(HttpServletRequest request)
    {
        String src=request.getParameter("src");
        String tarsrc=request.getParameter("targetsrc");
        String x=request.getParameter("x");
        String y=request.getParameter("y");
        String alpha=request.getParameter("alpha");

        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\demo_img\\"+photoName;
//
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_"+x+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;

        //endregion

        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_"+x+src.substring(src.lastIndexOf('.'),src.length());
        String loadsrc=loadPath+"/"+src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+"/"+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=weburl+"/"+newname;
        //endregion
        //图片复制
        ImageHelper.copyFile(loadsrc,targetsrc);
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            ImageHelper.pressImage(targetsrc,tarsrc,Integer.parseInt(x),Integer.parseInt(y),Float.parseFloat(alpha));
            monitorLogger.info("图片添加图片水印");
            map.put("ok",target);
        } catch (Exception e) {
            monitorLogger.info(new Date()+"==imgmarkimg==图片添加图片水印=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    //endregion

    //region  图片按照固定的比例缩放

    /**
     * 图片按照固定的比例缩放
     * @param request
     * @return
     */
    @RequestMapping("/imgreduce")
    @ResponseBody
    public Map<String,Object> imgreduce(HttpServletRequest request)
    {
        String src=request.getParameter("src");
        String w=request.getParameter("w");
        String h=request.getParameter("h");
        String rate=request.getParameter("rate");

        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\demo_img\\"+photoName;
//
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_"+rate+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;

        //endregion

        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_"+rate+src.substring(src.lastIndexOf('.'),src.length());
        String loadsrc=loadPath+src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+"/"+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=weburl+"/"+newname;
        //endregion
        //图片复制
        ImageHelper.copyFile(loadsrc,targetsrc);

        Map<String,Object> map=new HashMap<String ,Object>();

        try {
            ImageHelper.reduceImg(targetsrc,targetsrc,Integer.parseInt(w),Integer.parseInt(h),Float.parseFloat(rate));
            monitorLogger.info("图片按照固定的比例缩放");
            map.put("ok",target);
        } catch (NumberFormatException e) {
            monitorLogger.info(new Date()+"==imgreduce==图片按照固定的比例缩放=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    //endregion

    //region 已宽度为基准，等比例缩放图片   以高度为基准，等比例缩放图片

    /**
     *以高度为基准，等比例缩放图片
     * @param request
     * @return
     */
    @RequestMapping("/imgresizeByHeight")
    @ResponseBody
    public Map<String,Object> imgresizeByHeight(HttpServletRequest request)
    {
        String src=request.getParameter("src");
        String h=request.getParameter("h");

        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\demo_img\\"+photoName;
//
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_"+h+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;

        //endregion

        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_"+h+src.substring(src.lastIndexOf('.'),src.length());
        String loadsrc=loadPath+src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=weburl+"/"+newname;
        //endregion
        //图片复制
        ImageHelper.copyFile(loadsrc,targetsrc);
        Map<String,Object> map=new HashMap<String,Object>();

        try {
            ImageHelper img=new ImageHelper(targetsrc,targetsrc);
            img.resizeByWidth(Integer.parseInt(h));
            monitorLogger.info("以高度为基准，等比例缩放图片");
            map.put("ok",target);
        } catch (IOException e) {
            monitorLogger.info(new Date()+"==imgresizeByHeight==以高度为基准，等比例缩放图片=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 已宽度为基准，等比例缩放图片
     * @param request
     * @return
     */
    @RequestMapping("/imgresizeByWidth")
    @ResponseBody
    public Map<String,Object> imgresizeByWidth(HttpServletRequest request)
    {
        String src=request.getParameter("src");
        String w=request.getParameter("w");
        //region 处理路径
//        int index=src.lastIndexOf("/");
//        String photoName=src.substring(index+1,src.length());
//
//        String loadsrc="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\demo_img\\"+photoName;
//
//        String targetsrc=loadsrc.substring(0,loadsrc.lastIndexOf('.'))+"_"+w+loadsrc.substring(loadsrc.lastIndexOf('.'),loadsrc.length());
//
//        int indextow=targetsrc.lastIndexOf("\\");
//        String phototargetName=targetsrc.substring(indextow+1,targetsrc.length());
//        String target=src.substring(0,index)+"/"+phototargetName;
        //endregion
        //region new url
        String newname=src.substring(0,src.lastIndexOf('.'))+"_"+w+src.substring(src.lastIndexOf('.'),src.length());
        String loadsrc=loadPath+src;
        monitorLogger.info("本地地址： "+loadsrc);
        String targetsrc=loadPath+"/"+newname;
        monitorLogger.info("本地新地址： "+targetsrc);
        String target=weburl+"/"+newname;
        //endregion
        //图片复制
        ImageHelper.copyFile(loadsrc,targetsrc);
        Map<String,Object> map=new HashMap<String,Object>();

        try {
            ImageHelper img=new ImageHelper(targetsrc,targetsrc);
            img.resizeByWidth(Integer.parseInt(w));
            monitorLogger.info("已宽度为基准，等比例缩放图片");
            map.put("ok",target);
        } catch (IOException e) {
            monitorLogger.info(new Date()+"==imgresizeByWidth==已宽度为基准，等比例缩放图片=="+e.getMessage());
            map.put("no",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    //endregion

    //region 获取验证码

    /**
     * 生成验证码
     * @param response
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response)
    {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //生成图片
        int w = 100, h = 30;
        try {
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
            monitorLogger.info("获取验证码");
        } catch (IOException e) {
            monitorLogger.info(new Date()+"==verifyCode==获取验证码=="+e.getMessage());
            e.printStackTrace();
        }
    }
    //endregion

    //region 防盗链

    /**
     * 防盗链
     * @param request
     * @param response
     * @return
     * @throws MalformedURLException
     */
    public Boolean doFilter(ServletRequest request, ServletResponse response) throws MalformedURLException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse rep=(HttpServletResponse)response;
        String requestAddress = req.getRequestURL().toString();//获取当前页面的地址
        boolean flag=false;
        String host1="",host2="";

        if(requestAddress!=null){        //判断当前的页面的请求地址为空时
            URL urlOne=new URL(requestAddress);//实例化URL方法
            host1=urlOne.getHost();  //获取请求页面的服务器主机

        }
        String requestHeader = req.getHeader("referer");//获取页面的请求地址
        if(requestHeader!=null){        //判断当前的页面的请求地址为空时
            URL urlOne=new URL(requestAddress);//实例化URL方法
            host2=urlOne.getHost();  //获取请求页面的服务器主机

        }
        if(host1.equals(host2))flag=true;
       return flag;
    }
    //endregion

    //region  头像上传

    /**
     * 头像上传
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/pictureUpload")
    @ResponseBody
    public void  pictureUpload(HttpServletRequest request,HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();  //创建Json对象
       String avatar_src= request.getParameter("avatar_src");
       String avatar_data= request.getParameter("avatar_data");
        monitorLogger.info("头像上传");
        //保存的服务器路径
        String serverPath=serverPathUrl;
        String result="";//消息显示
        String savePath="";//保存文件路径



//region
        StandardMultipartHttpServletRequest req=(StandardMultipartHttpServletRequest) request;
        try {
            Iterator<String> iterator = req.getFileNames();//迭代器
            while (iterator.hasNext())//判断容器是否还有可以选择的内容
            {
                MultipartFile file= req.getFile(iterator.next());//获取文件
                byte[] bytes = file.getBytes();//获取文件的字节数组
                String contentType = file.getContentType();//获取文件的类型
                InputStream in = file.getInputStream();//获取文件流
                String name = file.getName();//获取控件的文件名称
                String originalFilename = file.getOriginalFilename();//获取原文件名称
                //判断扩展名
                String[] extension={".jpg",".png",".jpeg",".bmp"};
                String[] extension1={".docx",".xlsx",".pptx"};
                boolean isext= FileHelper.fileExtension(originalFilename,extension);
                if(!isext)
                {
                    //map.put("no","扩展名不匹配！");
                    jsonObject.put("message", "扩展名不匹配！");
                    jsonObject.put("state", 100);
                    jsonObject.put("result","");
                    response.getWriter().write(jsonObject.toString());
                }
                int split = originalFilename.lastIndexOf(".");
                //文件扩展名
                String ext = originalFilename.substring(split + 1, originalFilename.length());
                long size = file.getSize();//获取文件大小
                //设置文件大小
                boolean isSize = FileHelper.fileSize(size, 1000000);
                if(!isSize)
                {
                    //map.put("no","文件大小超出了范围！");
                    jsonObject.put("state", 100);
                    jsonObject.put("result","");
                    jsonObject.put("message", "文件大小超出了范围！");
                    response.getWriter().write(jsonObject.toString());
                    //return map;
                }
                boolean empty = file.isEmpty();//判断文件是否为空
                //设置上传文件名称的唯一性
                String newfileName = FileHelper.makeFileName(originalFilename);
                //制造新保存路径
                Map<String,String> map1=FileHelper.makePath(newfileName,serverPath);
                savePath=map1.get("url_all");



                int index = savePath.lastIndexOf(".");
                String newFile=savePath.substring(0,index)+"_100x100"+savePath.substring(index, savePath.length());
                //创建一个输出流
                FileOutputStream out=new FileOutputStream(savePath);
                //创建一个缓存区
                byte[] buffer=new byte[1024];
                //判断输入流中的数据是否已经读完的标识
                int len=0;
                //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                while ((len=in.read(buffer))>0)
                {
                    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                    out.write(buffer,0,len);
                }
                //关闭输入流
                in.close();
                //关闭输出流
                out.close();

                //region  裁剪图片
                //获取文件路劲
                String targetsrc=serverPath+"/"+map1.get("url_has")+"/"+newfileName;
                //下面是把拿到的json字符串转成 json对象
                JSONObject jsStr = JSONObject.parseObject(avatar_data); //将字符串{“id”：1}

                Double xx= Double.parseDouble(jsStr.getString("x"));
                int x = (new Double(xx)).intValue();//获取x的值
                Double yy= Double.parseDouble(jsStr.getString("y"));
                int y = (new Double(yy)).intValue();//获取y的值
                Double hh=Double.parseDouble(jsStr.getString("height"));
                int h=(new Double(hh)).intValue();//获取h的值
                Double ww=Double.parseDouble(jsStr.getString("width"));
                int w=(new Double(ww)).intValue();//获取w的值
                Double rr=Double.parseDouble(jsStr.getString("rotate"));
                int rotate=(new Double(rr)).intValue();//获取rotate的值
                System.out.println(targetsrc);
                try {
                    ImageHelper.rotateImage(targetsrc,rotate, java.awt.Color.gray);
                    ImageHelper.cutImage(targetsrc,targetsrc,x,y,w,h);
                    monitorLogger.info("图片裁剪（通用）");
                    //map.put("ok",target);
                } catch (IOException e) {
                    monitorLogger.info(new Date()+"==imgcut==图片裁剪（通用）=="+e.getMessage());
                    //map.put("no",e.getMessage());
                    e.printStackTrace();
                }

                //endregion

                //region 输入内容
                jsonObject.put("state", 200);
                jsonObject.put("message", "上传成功！");
                //jsonObject.put("result", "/"+map1.get("url_has")+"/"+newfileName);
                //endregion
                //region 保存到数据库
                String savePathWeb=weburl+map1.get("url_has") +"/"+newfileName;
                FileUpload fil=new FileUpload();
                fil.setFile_name(originalFilename);
                fil.setCategory("");
                fil.setFile_path(savePathWeb);
                fil.setFile_create_time(new Date());
                fil.setFile_size(size);
                fil.setFile_type(ext);
                fil.setFile_save_name(newfileName);
                Integer id=uploadFileServer.adduploadfile(fil);
                //map.put("ok",fil);
                //endregion
                //region  图片压缩
                //ImageHelper imge=new ImageHelper(savePath,newFile);
                //imge.resizeFix(100, 100);
                //endregion
                jsonObject.put("result", fil);
            }
        } catch (Exception e) {
            monitorLogger.info(new Date()+"==pictureUpload==头像上传=="+e.getMessage());
            jsonObject.put("message", "上传出错！");
            jsonObject.put("state", 100);
            jsonObject.put("result","");
            e.printStackTrace();
        }
        //endregion
        //设置Json对象的属性
        response.getWriter().write(jsonObject.toString());
    }


    //endregion


    //region 大文件上传

    //合并、验证分片方法
    @RequestMapping("/mergeOrCheckChunks")
    public void mergeOrCheckChunks(HttpServletRequest request, HttpServletResponse response) {
        String param = request.getParameter("param");
        String fileName = request.getParameter("fileName");


        String newFilePath = "no_"+fileName;
        String savePath = loadPath;
        String serverPath=serverPathUrl;
        String savePathResult="";//保存文件路径

        monitorLogger.info("大文件上传");
        //文件上传的临时文件保存在项目的temp文件夹下 定时删除
        savePath = new File(savePath) + "/temp/";

        //region 合并
        if(param.equals("mergeChunks")){
            //合并文件
            HttpSession session=request.getSession();
            String newfileName = FileHelper.makeFileName(fileName);
            //制造新保存路径
            Map<String,String> map1=FileHelper.makePath(newfileName,serverPath);
            savePathResult=map1.get("url_all");
            try {
                File f = new File(savePath+"/"+session.getAttribute("fileName_"+fileName));
                File[] fileArray = f.listFiles(new FileFilter(){
                    //排除目录只要文件
                    @Override
                    public boolean accept(File pathname) {
                        if(pathname.isDirectory()){
                            return false;
                        }
                        return true;
                    }
                });

                //转成集合，便于排序
                List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
                Collections.sort(fileList,new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if(Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())){
                            return -1;
                        }
                        return 1;
                    }
                });

                //截取文件名的后缀名
                //最后一个"."的位置
                //int pointIndex=fileName.lastIndexOf(".");
                //后缀名
                //String suffix=fileName.substring(pointIndex);
                //合并后的文件


                //File outputFile = new File(savePath+"/"+session.getAttribute("fileName_"+fileName)+suffix);
                File outputFile = new File(savePathResult);
                //创建文件
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //输出流
                FileChannel outChnnel = new FileOutputStream(outputFile).getChannel();
                //合并
                FileChannel inChannel;
                for(File file : fileList){
                    inChannel = new FileInputStream(file).getChannel();
                    try {
                        inChannel.transferTo(0, inChannel.size(), outChnnel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        inChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //删除分片
                    file.delete();
                }
                try {
                    outChnnel.close();
                } catch (IOException e) {
                    monitorLogger.info(new Date()+"==mergeOrCheckChunks==mergeChunks==大文件上传=="+e.getMessage());
                    e.printStackTrace();
                }

                //清除文件夹
               // File tempFile = new File(savePath+"/"+jedis.get("fileName_"+fileName));
                File tempFile = new File(savePath+"/"+session.getAttribute("fileName_"+fileName));
                if(tempFile.isDirectory() && tempFile.exists()){
                    tempFile.delete();
                }

                Map<String, String> resultMap=new HashMap<>();
                //将文件的最后上传时间和生成的文件名返回
               // resultMap.put("lastUploadTime", jedis.get("lastUploadTime_"+newFilePath));
                //resultMap.put("pathFileName", jedis.get("fileName_"+fileName)+suffix);

                resultMap.put("lastUploadTime", (String) session.getAttribute("lastUploadTime_"+newFilePath));
                //resultMap.put("pathFileName", session.getAttribute("fileName_"+fileName)+suffix);

                /****************清除redis中的相关信息**********************/
                //合并成功后删除redis中的进度信息

                session.setAttribute("jindutiao_"+newFilePath,"");
                //合并成功后删除redis中的最后上传时间，只存没上传完成的

                session.setAttribute("lastUploadTime_"+newFilePath,"");
                //合并成功后删除文件名称与该文件上传时生成的存储分片的临时文件夹的名称在redis中的信息  key：上传文件的真实名称   value：存储分片的临时文件夹名称（由上传文件的MD5值+时间戳组成）
                //如果下次再上传同名文件  redis中将存储新的临时文件夹名称  没有上传完成的还要保留在redis中 直到定时任务生效

                session.setAttribute("fileName_"+fileName,"");
                Gson gson=new Gson();
                String json=gson.toJson(resultMap);
                //PrintWriterJsonUtils.printWriter(response, json);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{

            }

        }//endregion
        //region 分块
        else if(param.equals("checkChunk")){
            /*************************检查当前分块是否上传成功**********************************/
            //接受参数

            String fileMd5 = request.getParameter("fileMd5");
            String chunk = request.getParameter("chunk");
            String chunkSize = request.getParameter("chunkSize");
            String jindutiao=request.getParameter("jindutiao");//文件上传的实时进度

            //创建Session
            HttpSession session=request.getSession();
            session.setAttribute("chunk",chunk);
            session.setAttribute("fileName",fileName);
            session.setAttribute("fileMd5",fileMd5);
            session.setAttribute("savePath",savePath);

            try {
                //将当前进度存入redis
                session.setAttribute("jindutiao_"+newFilePath,jindutiao);
                //将系统当前时间转换为字符串
                Date date=new Date();
                SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String lastUploadTime=formatter.format(date);
                //将最后上传时间以字符串形式存入redis
                session.setAttribute("lastUploadTime_"+newFilePath, lastUploadTime);
                //自定义文件名： 时间戳（13位）
                String tempFileName= String.valueOf(System.currentTimeMillis());
                if(session.getAttribute("fileName_"+fileName)==null || "".equals(session.getAttribute("fileName_"+fileName))){
                    //将文件名与该文件上传时生成的存储分片的临时文件夹的名称存入redis
                    //文件上传时生成的存储分片的临时文件夹的名称由MD5和时间戳组成
                    session.setAttribute("fileName_"+fileName, fileMd5+tempFileName);
                }


                File checkFile = new File(savePath+"/"+session.getAttribute("fileName_"+fileName)+"/"+chunk);
                response.setContentType("text/html;charset=utf-8");
                //检查文件是否存在，且大小是否一致
                if(checkFile.exists() && checkFile.length()==Integer.parseInt(chunkSize)){
                    //上传过
                    try {
                        response.getWriter().write("{\"ifExist\":1}");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    //没有上传过
                    try {
                        response.getWriter().write("{\"ifExist\":0}");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                monitorLogger.info(new Date()+"==mergeOrCheckChunks==checkChunk==大文件上传=="+e.getMessage());
                e.printStackTrace();
            }finally{

            }
        }
        //endregion
    }


    //保存上传分片
    @RequestMapping("/fileSave")
    @ResponseBody
    public void fileSave(HttpServletRequest request, HttpServletResponse response) throws IOException {



        //保存的服务器路径
        HttpSession session = request.getSession();
        String fileMd5 = session.getAttribute("fileMd5").toString();
        String chunk = session.getAttribute("chunk").toString();
        String fileName=session.getAttribute("fileName").toString();
        String savePath=session.getAttribute("savePath").toString();//保存文件路径

        //region old
//        StandardMultipartHttpServletRequest req=(StandardMultipartHttpServletRequest) request;
//        try {
//            Iterator<String> iterator = req.getFileNames();//迭代器
//            while (iterator.hasNext())//判断容器是否还有可以选择的内容
//            {
//                MultipartFile file= req.getFile(iterator.next());//获取文件
//                byte[] bytes = file.getBytes();//获取文件的字节数组
//                String contentType = file.getContentType();//获取文件的类型
//                InputStream in = file.getInputStream();//获取文件流
//                String name = file.getName();//获取控件的文件名称
//                String originalFilename = file.getOriginalFilename();//获取原文件名称
//
//                int split = originalFilename.lastIndexOf(".");
//                //文件扩展名
//                String ext = originalFilename.substring(split + 1, originalFilename.length());
//                long size = file.getSize();//获取文件大小
//
//                boolean empty = file.isEmpty();//判断文件是否为空
//                //设置上传文件名称的唯一性
//                //String newfileName = FileHelper.makeFileName(originalFilename);
//                //制造新保存路径
//                //Map<String,String> map1=FileHelper.makePath(newfileName,serverPath);
//                //savePath=map1.get("url_all");
//
//
//                    try {
//                        File filel = new File(savePath+"/"+session.getAttribute("fileName_"+fileName));
//                        if(!filel.exists()){
//                            filel.mkdir();
//                        }
//                        File chunkFile = new File(savePath+"/"+session.getAttribute("fileName_"+fileName)+"/"+chunk);
//
//                        FileOutputStream out=new FileOutputStream(chunkFile);
//                        //创建一个缓存区
//                        byte[] buffer=new byte[1024];
//                        //判断输入流中的数据是否已经读完的标识
//                        int len=0;
//                        //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
//                        while ((len=in.read(buffer))>0)
//                        {
//                            //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
//                            out.write(buffer,0,len);
//                        }
//                        //关闭输入流
//                        in.close();
//                        //关闭输出流
//                        out.close();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }finally {
//
//                    }
//
//            }
//        } catch (Exception e) {
//            monitorLogger.info(new Date()+"==upload==文件上传=="+e.getMessage());
//            e.printStackTrace();
//        }
        //endregion

        //region new
        try {
            InputStream in = request.getInputStream();//获取文件流

            try {
                File filel = new File(savePath+"/"+session.getAttribute("fileName_"+fileName));
                if(!filel.exists()){
                    filel.mkdir();
                }
                File chunkFile = new File(savePath+"/"+session.getAttribute("fileName_"+fileName)+"/"+chunk);

                FileOutputStream out=new FileOutputStream(chunkFile);
                //创建一个缓存区
                byte[] buffer=new byte[1024];
                //判断输入流中的数据是否已经读完的标识
                int len=0;
                //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                while ((len=in.read(buffer))>0)
                {
                    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                    out.write(buffer,0,len);
                }
                //关闭输入流
                in.close();
                //关闭输出流
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }finally {

            }

        }
        catch (Exception e) {
            monitorLogger.info(new Date()+"==upload==文件上传=="+e.getMessage());
            e.printStackTrace();
        }
        //endregion

    }


    //当有文件添加进队列时 通过文件名查看该文件是否上传过 上传进度是多少
    @RequestMapping("/selectProgressByFileName")
    @ResponseBody
    public String selectProgressByFileName(HttpServletRequest request,String fileName) {
        String jindutiao="";
        HttpSession session = request.getSession();
        try {
            if(null!=fileName && !"".equals(fileName)){
                jindutiao= (String) session.getAttribute("jindutiao_"+fileName);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return jindutiao;
    }

    //endregion

}

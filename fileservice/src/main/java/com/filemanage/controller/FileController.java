package com.filemanage.controller;


import com.filemanage.entity.FileUpload;
import com.filemanage.service.IUploadFileServer;
import com.filemanage.tools.FileHelper;
import com.filemanage.tools.ImageHelper;
import com.filemanage.tools.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;



/**
 * Created by chenwangming on 2017/8/29.
 * 文件控制器
 *
 * 支持的浏览器
 *
 * 图片
 *
 * 图片按比例生成
 *
 * 图片裁剪----
 *

 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Value("${loadPath}")
    private String  loadPath;

    @Value("${weburl}")
    private String weburl;

    @Autowired
    private IUploadFileServer uploadFileServer;

    //region  上传文件
    @RequestMapping("/fileupload")
    public String index()
    {
        return "/file/fileupload";
    }


    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<Integer,Object> uploadFile(HttpServletRequest request, HttpServletResponse response)
    {

        Map<Integer,Object> json=new HashMap<Integer,Object>();
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
        String serverPath="D:\\fileupload\\upload";
        String result="";//消息显示
        String savePath="";//保存文件路径
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
                boolean isext= FileHelper.fileExtension(originalFilename,extension1);

                int split = originalFilename.lastIndexOf(".");
                //文件扩展名
                String ext = originalFilename.substring(split + 1, originalFilename.length());

                long size = file.getSize();//获取文件大小
                //设置文件大小
                boolean isSize = FileHelper.fileSize(size, 10000);

                boolean empty = file.isEmpty();//判断文件是否为空

                //设置上传文件名称的唯一性
                String newfileName = FileHelper.makeFileName(originalFilename);
                //制造新保存路径
                Map<String,String> map=FileHelper.makePath(newfileName,serverPath);
                savePath=map.get("url_all");
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

                //保存到数据库
                FileUpload fil=new FileUpload();
                fil.setFile_name(originalFilename);
                fil.setCategory("");
                fil.setFile_path(savePath);
                fil.setFile_create_time(new Date());
                fil.setFile_size(size);
                fil.setFile_type(ext);
                fil.setFile_save_name(newfileName);
                Integer id=uploadFileServer.adduploadfile(fil);
                json.put(id,fil);


                //region压缩图片
                //ImageHelper imge=new ImageHelper(savePath,newFile);
                try {
                    ImageHelper imge=new ImageHelper(savePath,newFile);
                    imge.resizeFix(100, 100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //endregion
            }


        } catch (Exception e) {
           json.put(0,"上传出错！");
            e.printStackTrace();
        }
        return json;
    }
    //endregion

    //region 修改上传文件
    @RequestMapping("/edit")
    public String edit()
    {
        return "/file/fileupdate";
    }
    @RequestMapping("/getfiles")
    @ResponseBody
    public List<FileUpload> getfiles(HttpServletRequest request) {
        String fileid = request.getParameter("id");
        List fileids = new ArrayList();
        if (fileid.split(",").length > 0) {
            for (String item : fileid.split(",")) {
                fileids.add(Integer.parseInt(item));
            }
        } else {
            fileids.add(Integer.parseInt(fileid));
        }
        List<FileUpload> list = uploadFileServer.getfiles(fileids);
        return list;
    }
    //endregion

    //region 删除文件

    @RequestMapping("/deletePic")
    @ResponseBody
    public Map<String,Object> deletePic(String key)
    {
        System.out.print(key);
        Map<String,Object> map=new HashMap<String,Object>();
        int deleteuploadfile = 1;//uploadFileServer.deleteuploadfile(Integer.parseInt(key));
        if(deleteuploadfile>0) {
            //根据id查询路径
//            FileUpload fileUpload = uploadFileServer.getFileUpload(Integer.parseInt(key));
//            if(fileUpload!=null)
//            {
//                boolean flag=   FileHelper.deleteFile(fileUpload.getFile_path());
//            }
            map.put("success","ok");
        } else
        {
            map.put("error","ok");
        }
        return  map;
    }

    @RequestMapping("/deletefile")
    @ResponseBody
    public String deletefile(HttpServletRequest request)
    {

        try {
            int fileid =Integer.parseInt(request.getParameter("fileid")) ;

            //根据id查询路径
            FileUpload fileUpload = uploadFileServer.getFileUpload(fileid);
            if(fileUpload!=null)
            {
                boolean flag=   FileHelper.deleteFile(fileUpload.getFile_path());
            }
            int deleteuploadfile = uploadFileServer.deleteuploadfile(fileid);
            if(deleteuploadfile>0)
            {
                return "ok";
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "no";
    }
    //endregion

    //region 文件下载
    @RequestMapping("/downLoadFile")
    @ResponseBody
    public String downLoadFile(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //得到要下载的文件名
        request.setCharacterEncoding("UTF-8");
        String fileName = request.getParameter("filename");
        //fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
        //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
        String fileSaveRootPath=  "D:\\fileupload\\upload";//this.getServletContext().getRealPath("/WEB-INF/upload");
        //通过文件名找出文件的所在目录
        String path = FileHelper.findFileSavePathByFileName(fileName,fileSaveRootPath);
        //得到要下载的文件
        File file = new File(path + "\\" + fileName);
        //如果文件不存在
        if(!file.exists()){
            request.setAttribute("message", "您要下载的资源已被删除！！");
            //request.getRequestDispatcher("/message.jsp").forward(request, response);
            return "";
        }
        //处理文件名
        String realname = fileName.substring(fileName.indexOf("_")+1);
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(path + "\\" + fileName);
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
        return "";
    }

    /**
     * 获取图片路径
     * @param request
     */
    @RequestMapping("/showImg")
    public String getFilePath(HttpServletRequest request,HttpServletResponse response)
    {
        String fileSaveRootPath=  "D:\\fileupload\\upload";
        String fileName = request.getParameter("filename");
        String path = FileHelper.findFileSavePathByFileName(fileName,fileSaveRootPath);
        String url=path+"\\"+fileName;
        return url;
    }
    //endregion

    //region  文件加载
    /**
     *  文件显示
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/fileList")
    public String filelist(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //获取上传文件的目录
        String uploadFilePath = loadPath;//this.getServletContext().getRealPath("/WEB-INF/upload");
        String httpurl=weburl;
        //存储要下载的文件名
        Map<String,String> fileNameMap = new HashMap<String,String>();
        //递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
        //FileHelper.listfile(new File(uploadFilePath),fileNameMap);//File既可以代表一个文件也可以代表一个目录
        Map<String,String> fileurlMap=new HashMap<String,String>();
        FileHelper.listfile(new File(uploadFilePath),fileNameMap,fileurlMap,uploadFilePath,httpurl);
        //将Map集合发送到listfile.jsp页面进行显示
        request.setAttribute("fileNameMap", fileNameMap);
        request.setAttribute("fileurlMap", fileurlMap);
        //request.getRequestDispatcher("/listfile.ftl").forward(request, response);

//        List<FileUpload> list = uploadFileServer.getList();
//        request.setAttribute("list", list);
        //重数据库加载


        return "/file/filelist";
    }

    //endregion

    //region   验证码
    @RequestMapping("/yzmCode")
    public String yacode()
    {
        return "/file/yanzhengma";
    }


    /**
     * 获取验证嘛
     * @param request
     * @param response
     */
    @RequestMapping("/getYanZhengMa")
    public void getYanZhengMa(HttpServletRequest request,HttpServletResponse response) throws IOException {

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
//        HttpSession session = request.getSession(true);
//        //删除以前的
//        session.removeAttribute("verCode");
//        session.setAttribute("verCode", verifyCode.toLowerCase());
        //生成图片
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

    }

    //endregion

    //region  图片裁剪

    /**
     *
     * @return
     */
    @RequestMapping("/cutview")
    public String cutpotoView()
    {
        return "/file/cutview";
    }

    /**
     * 图片裁剪
     */
    @RequestMapping("/cutpphoto")
    public void cutPhoto(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String src="D:\\fileupload\\upload\\5\\12\\4c8dd40602a44729a218a1657d6f876b_2.jpg";
        String des="D:\\fileupload\\upload\\5\\12\\4c8dd40602a44729a218a1657d6f876b_2_100200.jpg";

        try {
           // ImageHelper.cutImage(src,des,0,0,100,100);//根据尺寸图片居中裁剪
           // ImageHelper.cutHalfImage(src,des);//图片裁剪二分之一
            ImageHelper.cutImage(src,des,0,0,150,200);//图片裁剪通用接口
        } catch (IOException e) {
            e.printStackTrace();
        }
        //response.getOutputStream();
    }
    //endregion

    //region 图片裁剪2
    /**
     * 图片裁剪2
     * @param request
     * @param response
     */
    @RequestMapping("/cutphotos")
    public void cutphotos(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
       String x= request.getParameter("x");
       String y=request.getParameter("y");
       String w=request.getParameter("w");
       String h=request.getParameter("h");
       String path="D:\\研发库\\Product\\HMA\\源代码\\hmaplant-v1.2\\fileservice\\src\\main\\resources\\static\\img\\cutpicther.jpg";

       ImageHelper.cutImage(path,response.getOutputStream(),Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(w),Integer.parseInt(h));
    }
    //endregion


    //region 防盗链练



    /**
     * 防盗链练
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/dofilter")
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse rep=(HttpServletResponse)response;
        String requestAddress = req.getRequestURL().toString();//获取当前页面的地址
        if(requestAddress!=null){        //判断当前的页面的请求地址为空时
            URL urlOne=new URL(requestAddress);//实例化URL方法
           String pathAdd=urlOne.getHost();  //获取请求页面的服务器主机
            System.out.print("");
        }
        String requestHeader = req.getHeader("referer");//获取页面的请求地址
        System.out.print("");

    }
    //endregion


    //region  头像上传
    @RequestMapping("/photos")
    public String photos()
    {
        return "/file/photos";
    }
    //endregion



    //region Demo页面
    @RequestMapping("/demo_imgmarkfont")
    public String demo_imgmarkfont()
    {
        return "file/demo_imgmarkfont";
    }
    @RequestMapping("/demo_imgrotate")
    public String demo_imgrotate()
    {
        return "file/demo_imgrotate";
    }
    @RequestMapping("/demo_imgmarkimg")
    public String demo_imgmarkimg()
    {
        return "file/demo_imgmarkimg";
    }
    @RequestMapping("/demo_imgcutcenter")
    public String demo_imgcutcenter()
    {
        return  "file/demo_imgcutcenter";
    }
    @RequestMapping("/demo_imgcutahalf")
    public String demo_imgcutahalf()
    {
        return "file/demo_imgcutahalf";
    }
    @RequestMapping("/demo_imgcut")
    public String demo_imgcut()
    {
        return "file/demo_imgcut";
    }
    @RequestMapping("/demo_imgcut_jcrop")
    public String demo_imgcut_jcrop()
    {
        return "file/demo_imgcut_jcrop";
    }
    @RequestMapping("/demo_verifycode")
    public String demo_verifycode()
    {
        return "file/demo_verifycode";
    }
    @RequestMapping("/demo_imgreduce")
    public String demo_imgreduce()
    {
        return "file/demo_imgreduce";
    }
    @RequestMapping("/demo_imgresizeByHeight")
    public String demo_imgresizeByHeight()
    {
        return "file/demo_imgresizeByHeight";
    }
    @RequestMapping("/demo_imgresizeByWidth")
    public String demo_imgresizeByWidth()
    {
        return "file/demo_imgresizeByWidth";
    }
    @RequestMapping("/demo_upload")
    public String demo_upload()
    {
        return "file/demo_upload";
    }
    @RequestMapping("/demo_downLoad")
    public String demo_downLoad(HttpServletRequest request)
    {
        //获取上传文件的目录
        String uploadFilePath =loadPath;//this.getServletContext().getRealPath("/WEB-INF/upload");
        String httpurl=weburl;
        //存储要下载的文件名
        Map<String,String> fileNameMap = new HashMap<String,String>();
        //递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
        //FileHelper.listfile(new File(uploadFilePath),fileNameMap);//File既可以代表一个文件也可以代表一个目录
        Map<String,String> fileurlMap=new HashMap<String,String>();
        FileHelper.listfile(new File(uploadFilePath),fileNameMap,fileurlMap,uploadFilePath,httpurl);
        //将Map集合发送到listfile.jsp页面进行显示
        request.setAttribute("fileNameMap", fileNameMap);
        request.setAttribute("fileurlMap", fileurlMap);
        //region  重数据库加载
//        List<FileUpload> list = uploadFileServer.getList();
//        request.setAttribute("list", list);
        //endregion

        return "file/demo_downLoad";
    }
    @RequestMapping("/demo_pictureUpload")
    public String demo_pictureUpload()
    {
        return "file/demo_pictureUpload";
    }
    @RequestMapping("/demo_bigupload")
    public String demo_bigupload(){return "file/demo_bigupload";}

    //endregion

}

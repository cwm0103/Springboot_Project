package com.func.controller;

import com.func.entity.DemoEntity;
import com.func.entity.SdRecover;
import com.func.server.iserver.IDemoServer;
import com.func.server.iserver.ISdRecoverServer;
import com.func.tools.readexeclhelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenwangming on 2017/8/9.
 */
@Controller
@RequestMapping("/home")
public class Home {

    @Autowired
    private IDemoServer demoServer;
    @Autowired
    private ISdRecoverServer sdRecoverServer;
    @RequestMapping("/index")
    //@ResponseBody
    public String index(Model model)
    {

//        List<DemoEntity> list=demoServer.getAll();
//        model.addAttribute("list",list);
        List<SdRecover> sdRecoverList = sdRecoverServer.getSdRecoverList();
        model.addAttribute("list",sdRecoverList);
        return "/home/index";
    }

    /**
     * 导入execl
     * @return
     */
    @RequestMapping("/export")
    public String exelreport(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        //region 上传文件
        //保存文件路径
        String savePath = "D:\\fileupload\\upload";
        String message="";
        String realSavePath="";
        String saveFilename="";

        //强制转换request
        StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
        try {
            Iterator<String> iterator = req.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                String fileNames = file.getOriginalFilename();
                int split = fileNames.lastIndexOf(".");
                //文件名 String substring = fileNames.substring(0, split);
                //文件格式 String substring1 = fileNames.substring(split + 1, fileNames.length());
                //文件内容 byte[] bytes = file.getBytes();
                //存储文件

                //文件大小
                long size = file.getSize();

                //文件扩展名
                String extension = fileNames.substring(split + 1, fileNames.length());

                //获取item中的上传文件的输入流
                InputStream in = file.getInputStream();
                //得到文件保存的名称
                saveFilename = makeFileName(fileNames);
                //得到文件的保存目录
                realSavePath = makePath(saveFilename, savePath);
                //创建一个文件输出流
                FileOutputStream out = new FileOutputStream(realSavePath + "\\" + saveFilename);
                //创建一个缓冲区
                byte buffer[] = new byte[1024];
                //判断输入流中的数据是否已经读完的标识
                int len = 0;
                //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                while ((len = in.read(buffer)) > 0) {
                    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                    out.write(buffer, 0, len);
                }
                //关闭输入流
                in.close();
                //关闭输出流
                out.close();
                message="上传成功！";
            }
        } catch (IOException e) {
            message = "上传失败！";
            e.printStackTrace();
        }
        //endregion

        //region 保存到数据库
       List<SdRecover> sdlist= saveDataExecl(realSavePath+"\\"+saveFilename);

        int result = saveData(sdlist);

        System.out.print(result);
        //endregion
        return null;
    }

    /**
     * 保存到数据库
     * @param list
     */
    private int saveData(List<SdRecover> list)
    {
        int result=0;
        if(list.size()>0)
        {
            if(list.size()>5000)
            {
                int n=list.size()/5000;
                if(list.size()%5000>0)
                {
                   n= n+1;
                }
                List<List<SdRecover>> lists = averageAssign(list, n);
                //分批保存到数据库
                for (int i=0;i<lists.size();i++)
                {
                    //保存数据库
                    result = sdRecoverServer.saveData(lists.get(i));
                }
            }else
            {
                //保存数据库
                result = sdRecoverServer.saveData(list);
            }
        }
        return result;
    }

    /**
     * 把execl中的数据读取到集合中
     * @param filePath
     */
    private  List<SdRecover> saveDataExecl(String filePath) throws ParseException {
        readexeclhelper reh=new readexeclhelper();
        List<List<String>> read = reh.read(filePath);
        List<SdRecover> sdList=new ArrayList<SdRecover>();
        if(read.size()>0)
        {
            for (int i=0;i<read.size();i++)
            {
                List<String> colums=read.get(i);
                SdRecover sdr=new SdRecover();
                //region 正常文档
                if(colums.size()==3)//正常文档
                {
                    if(colums.get(0).equals("")&&colums.get(2).equals("")&&colums.get(1).equals(""))
                    {

                    }else
                    {
                        sdr.setSd_code(colums.get(0));
                        String datatime=colums.get(2);

                        Date date = null;
                        if(!datatime.equals(""))
                        {
                            try {
                                SimpleDateFormat sdf =   new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" ,Locale.US);
                                date = sdf.parse( datatime );
                            } catch (ParseException e) {
                                SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                                date = sdf.parse(datatime);
                                e.printStackTrace();
                            }
                        }
                        sdr.setSd_datatime(date);
                        sdr.setSd_io("");
                        String value=colums.get(1);
                        sdr.setSd_value(Float.parseFloat(value));
                    }

                }
                //endregion

                if(colums.size()>3)//大于三列的文档
                {
                    if(colums.get(0).equals("")&&colums.get(2).equals("")&&colums.get(3).equals(""))
                    {

                    }else {
                        sdr.setSd_code(colums.get(0));
                        String datatime = colums.get(2);
                        Date date = null;
                        if (!datatime.equals("")) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
                                date = sdf.parse(datatime);
                            } catch (ParseException e) {
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                                try {
                                    date = sdf.parse(datatime);
                                }catch (ParseException ex)
                                {
                                    date = sdf.parse( datatime );
                                    ex.printStackTrace();
                                }

                                e.printStackTrace();
                            }
                        }
                        sdr.setSd_datatime(date);
                        sdr.setSd_io("");
                        String value = colums.get(3);
                        if(value.equals(""))
                        {
                            value="0";
                        }
                        sdr.setSd_value(Float.parseFloat(value));
                    }
                }
                sdList.add(sdr);
            }

        }
        return sdList;
    }



    /**
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     * @Anthor:陈王明
     */
    private String makeFileName(String filename) {  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + "_" + filename;
    }

    /**
     * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
     *
     * @param filename 文件名，要根据文件名生成存储目录
     * @param savePath 文件存储路径
     * @return 新的存储目录
     * @Method: makePath
     * @Description:
     * @Anthor:陈王明
     */
    private String makePath(String filename, String savePath) {
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode & 0xf;  //0--15
        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
        //构造新的保存目录
        String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        return dir;
    }


    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;  //(先计算出余数)
        int number=source.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }
}

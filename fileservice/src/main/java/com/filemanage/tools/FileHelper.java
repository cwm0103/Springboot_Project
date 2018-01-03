package com.filemanage.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenwangming on 2017/8/29.
 * 文件上传下载帮助类
 */
public class FileHelper {

    /**
     * @Method: findFileSavePathByFileName
     * @Description: 通过文件名和存储上传文件根目录找出要下载的文件的所在路径
     * @param filename 要下载的文件名
     * @param saveRootPath 上传文件保存的根目录，也就是/WEB-INF/upload目录
     * @return 要下载的文件的存储目录
     */
    public static String findFileSavePathByFileName(String filename,String saveRootPath){
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;  //0--15
        int dir2 = (hashcode&0xf0)>>4;  //0-15
        String dir = saveRootPath + "/" + dir1 + "/" + dir2;  //upload\2\3  upload\3\5
        File file = new File(dir);
        if(!file.exists()){
            //创建目录
            file.mkdirs();
        }
        return dir;
    }


    /**
     * 扩展名判断方法
     * @param file
     * @param extension
     * @return
     */
    public static boolean fileExtension(String file,String[] extension)
    {
        boolean flag=false;
        //String[] extension={".jpg",".png",".jpeg",".bmp"};
        for (String ext:extension) {
            //region 后缀判断方法
            if(file.endsWith(ext))
            {
                flag=true;
                break;
            }
            //endregion
            //region indexOf判断方法
//            if(file.indexOf(ext)>-1)
//            {
//                flag=true;
//                break;
//            }
            //endregion
        }
        return flag;
    }

    /**
     * 设置文件不能超过的大小
     * @param size
     * @param setSize
     * @return
     */
    public static boolean fileSize(long size,long setSize)
    {
        boolean flag=false;
        if(size<=setSize)
        {
            flag=true;
        }
        return flag;
    }

    /**
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     * @Anthor:陈王明
     */
    public static String makeFileName(String filename) {  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        //去掉uuid的-
        String uuid=UUID.randomUUID().toString();
        uuid = uuid.replace("-","").trim();
        return uuid + "_" + filename;
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
    public static Map<String,String> makePath(String filename, String savePath) {
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode & 0xf;  //0--15
        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
        //构造新的保存目录
        String dir = savePath + "/" + dir1 + "/" + dir2;  //upload\2\3  upload\3\5
        String dirhas=dir1+"/"+dir2;
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        Map<String,String> map=new HashMap<String,String>();
        map.put("url_all",dir+"/"+filename);
        map.put("url_has",dirhas);
        return map;
    }


    /**
     * @Method: listfile
     * @Description: 递归遍历指定目录下的所有文件
     * @param file 即代表一个文件，也代表一个文件目录
     * @param map 存储文件名的Map集合
     */
    public static void listfile(File file,Map<String,String> map){
        //如果file代表的不是一个文件，而是一个目录
        if(!file.isFile()){
            //列出该目录下的所有文件和目录
            File files[] = file.listFiles();
            //遍历files[]数组
            for(File f : files){
                //递归
                listfile(f,map);
            }
        }else{
            /**
             * 处理文件名，上传后的文件是以uuid_文件名的形式去重新命名的，去除文件名的uuid_部分
             file.getName().indexOf("_")检索字符串中第一次出现"_"字符的位置，如果文件名类似于：9349249849-88343-8344_阿_凡_达.avi
             那么file.getName().substring(file.getName().indexOf("_")+1)处理之后就可以得到阿_凡_达.avi部分
             */
            String realName = file.getName().substring(file.getName().indexOf("_")+1);
            //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
            map.put(file.getName(), realName);
        }
    }

    /**
     * listfile
     * @param file 递归遍历指定目录下的所有文件
     * @param map 即代表一个文件，也代表一个文件目录
     * @param mapurl 即代表一个文件，也代表一个文件路径
     * @param fileSaveRootPath 本地物理路径
     * @param weburl 服务器路劲
     */
    public static void listfile(File file,Map<String,String> map,Map<String,String>mapurl,String fileSaveRootPath,String weburl)
    {
        //如果file代表的不是一个文件，而是一个目录
        if(!file.isFile()){
            //列出该目录下的所有文件和目录
            File files[] = file.listFiles();
            //遍历files[]数组
            for(File f : files){
                //递归
                listfile(f,map,mapurl,fileSaveRootPath,weburl);
            }
        }else{
            /**
             * 处理文件名，上传后的文件是以uuid_文件名的形式去重新命名的，去除文件名的uuid_部分
             file.getName().indexOf("_")检索字符串中第一次出现"_"字符的位置，如果文件名类似于：9349249849-88343-8344_阿_凡_达.avi
             那么file.getName().substring(file.getName().indexOf("_")+1)处理之后就可以得到阿_凡_达.avi部分
             */
            String realName = file.getName().substring(file.getName().indexOf("_")+1);
            //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
            map.put(file.getName(), realName);
            String path =findFileSavePathByFileName(file.getName(),weburl);
            String url=path+"\\"+file.getName();
            mapurl.put(file.getName(),url);
        }
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}

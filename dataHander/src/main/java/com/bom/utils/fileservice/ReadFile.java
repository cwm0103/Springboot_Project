package com.bom.utils.fileservice;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取测点列表类
 * <p>Title: ReadFile</p>
 * <p>Description: </p>
 * @author	liying
 * @date	2017年11月15日
 * @version 1.0
 */

public class ReadFile {


    public static List<String> readAllCode(String filename) {
        List<String> code = null;

        File file = new File(filename);

        try {
            // 读取文件的输入流
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "utf-8");

            // 判断文件是否存在
            if (file.isFile() && file.exists()) {
                code = new ArrayList<String>();

                // 字符缓存输入流
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String str = null;
                // 读取文件，将文件内容放入到set中
                while ((str = bufferedReader.readLine()) != null) {
                    code.add(str);
                }
                bufferedReader.close();
            }
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}

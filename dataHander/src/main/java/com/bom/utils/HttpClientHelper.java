package com.bom.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;


/**
 * Created by chenwangming on 2017/11/14.
 */
public class HttpClientHelper {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);
    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam){
        return httpPost(url, jsonParam, false);
    }
    public static JSONArray  httpPostArray(String url, JSONObject jsonParam){
        return httpPostArray(url, jsonParam, false);
    }


    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
//                    jsonResult = JSONObject.fromObject(str);
                    jsonResult = JSONObject.parseObject(str);

                } catch (Exception e) {
                   logger.error("post请求提交失败:" + url, e);
                    System.out.print("post请求提交失败:" + url+"；错误信息："+ e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
            System.out.print("post请求提交失败:" + url+"；错误信息："+ e);
        }
        return jsonResult;
    }

    public static JSONArray httpPostArray(String url,JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONArray jsonArray = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
//                    jsonResult = JSONObject.fromObject(str);
                    jsonArray = JSONArray.parseArray(str);

                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                    System.out.print("post请求提交失败:" + url+"；错误信息："+ e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
            System.out.print("post请求提交失败:" + url+"；错误信息："+ e);
        }
        return jsonArray;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url){
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.parseObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                System.out.print("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            System.out.print("get请求提交失败:" + url+";错误："+e);
        }
        return jsonResult;
    }


    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPostPV(String url,JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
                method.setHeader("ENN-TOKEN","b46376240a6e452a9f6bf7c6ccddbe28");
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
//                    jsonResult = JSONObject.fromObject(str);
                    jsonResult = JSONObject.parseObject(str);

                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                    System.out.print("post请求提交失败:" + url+"；错误信息："+ e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
            System.out.print("post请求提交失败:" + url+"；错误信息："+ e);
        }
        return jsonResult;
    }
}






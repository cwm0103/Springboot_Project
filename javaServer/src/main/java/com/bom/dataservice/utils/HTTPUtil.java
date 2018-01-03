package com.bom.dataservice.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;



public class HTTPUtil {
    private static final Logger logger = LoggerFactory.getLogger(HTTPUtil.class);

    public static String HttpPostWithJson(String url, String json,CloseableHttpClient httpClient) {
        String returnValue = "这是默认返回值，接口调用失败";
        try {
            // 第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            // 第三步：给httpPost设置JSON格式的参数
           /* StringEntity requestEntity = new StringEntity(json,"UTF-8");
            requestEntity.setContentType("text/plain");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.addHeader("Content-type","text/plain; charset=utf-8");
            httpPost.setHeader("Accept", "text/plain");
            httpPost.setEntity(requestEntity);*/
            httpPost.addHeader("Content-type","text/plain; charset=utf-8");
            byte[] b=null;
            try {
                b = json.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            InputStream is = new ByteArrayInputStream(b, 0, b.length);
            InputStreamEntity entity = new InputStreamEntity(is, b.length);
            httpPost.setEntity(entity);
            // 第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost, responseHandler); // 调接口获取返回值时，必须用此方法
        } catch (Exception e) {
            System.out.println(json);
            e.printStackTrace();
        }
        // 第五步：处理返回值
        return returnValue;
    }

    @SuppressWarnings("deprecation")
    private static CloseableHttpClient getHttpClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder
                .<ConnectionSocketFactory> create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        // 指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            // 信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates,
                        String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS()
                    .loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        // 设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                registry);
        // connManager.setDefaultConnectionConfig(connConfig);
        // connManager.setDefaultSocketConfig(socketConfig);
        // 构建客户端
        return HttpClientBuilder.create().setConnectionManager(connManager)
                .build();
    }

    public static JSONObject post(String url, String params) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        // 参数处理
        StringEntity entity = new StringEntity(params, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        // 执行请求
        try {
            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity resEntity = response.getEntity();
                return JSONObject.parseObject(EntityUtils.toString(resEntity));
            } else {
                logger.error("post请求提交失败:" + url+"status="+ status);
                logger.error("status="+ status);
                return null;
            }
        } catch (Exception e) {
            logger.error("post请求提交失败:" + url, e);
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T get(String url,Class<T> c) throws IllegalAccessException, InstantiationException {
        T t=c.newInstance();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        // 执行请求
        try {
            CloseableHttpResponse response = getHttpClient().execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity resEntity = response.getEntity();
                return JSON.parseObject(EntityUtils.toString(resEntity),c) ;//JSONObject.parseObject(EntityUtils.toString(resEntity));
            } else {
                logger.error("get请求提交失败:" + url +"status=" + status);
                return t;
            }
        } catch (Exception e) {
            logger.error("get请求提交失败:" + url, e);
            e.printStackTrace();
        }
        return t;
    }
    public static JSONObject get(String url) {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        // 执行请求
        try {
            CloseableHttpResponse response = getHttpClient().execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity resEntity = response.getEntity();
               // JSONArray json=JSONObject.parseArray(EntityUtils.toString(resEntity));
                return JSONObject.parseObject(EntityUtils.toString(resEntity));
            } else {
                logger.error("get请求提交失败:" + url +"status=" + status);
                return null;
            }
        } catch (Exception e) {
            logger.error("get请求提交失败:" + url, e);
            e.printStackTrace();
        }
        return null;
    }

    public static String getContent(String url) {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        // 执行请求
        try {
            CloseableHttpResponse response = getHttpClient().execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity resEntity = response.getEntity();
                // JSONArray json=JSONObject.parseArray(EntityUtils.toString(resEntity));
                return EntityUtils.toString(resEntity);
            } else {
                logger.error("get请求提交失败:" + url +"status=" + status);
                return null;
            }
        } catch (Exception e) {
            logger.error("get请求提交失败:" + url, e);
            e.printStackTrace();
        }
        return null;
    }

    public static String postContent(String url, String params) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        // 参数处理
        StringEntity entity = new StringEntity(params, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        // 执行请求
        try {
            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity resEntity = response.getEntity();
                return EntityUtils.toString(resEntity);
            } else {
                System.out.println("status=" + status);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
package com.bom.utils;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpEntity;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;



public class HTTPUtil {
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

    public static DataResult post(String url, String params) {
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
                return JSON.parseObject(EntityUtils.toString(resEntity),
                        DataResult.class);
            } else {
                System.out.println("status=" + status);
                return null;
            }
        } catch (Exception e) {
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
                //EntityUtils.toString(resEntity);
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
    
    public static DataResult get(String url) {
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
                return JSON.parseObject(EntityUtils.toString(resEntity),
                        DataResult.class);
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
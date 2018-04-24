package com.cwm.elastic.elasticapi;

import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;

import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.core.util.IOUtils;
import org.elasticsearch.client.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * cwm
 * add
 * 2018-4-20
 * region Java Low level REST Client
 * 低级客户端的特征包括：
 * 最小的依赖
 * 跨越所有可用节点的负载平衡
 * 在节点故障和特定响应代码的情况下的故障转移
 * 失败的连接惩罚（是否重试失败的节点取决于它连续失败多少次；失败的尝试越多，客户端在再次尝试相同的节点之前将等待的时间越长）。
 * 持久连接
 * 请求和响应的跟踪日志记录
 * 可选的群集节点自动发现
 */

public class elasticsearchApi {

    //region Java Low level REST Client
    /**
     * 连接
     */
    static RestClient restClient=null;
    public static void esStart() throws IOException {
        if(restClient==null)
        {
            restClient=RestClient.builder(new HttpHost("10.4.78.66",9200,"http")).build();
        }



         //region    restclientbuilder也允许随意设置以下配置参数而建立的restclient实例
        /*
        //设置需要在每个请求中发送的缺省报头，以防止必须用每个请求指定它们。
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
        builder.setDefaultHeaders(defaultHeaders);
        */
        //设置在同一请求多次尝试时应遵守的超时时间。默认值为30秒，与默认套接字超时相同。在自定义套接字超时的情况下，应相应调整最大重试超时时间。
        /*
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        builder.setMaxRetryTimeoutMillis(10000);
        */
        //设置一个侦听器，每当节点失败时，都要通知它，以防需要采取行动。启用嗅探器时启用内部使用。
        /*
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        builder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(HttpHost host) {

            }
        });*/

        //设置一个回调函数，允许修改默认的要求配置（例如，请求超时，认证，或任何的org.apache.http.client.config.requestconfig.builder允许设置）
        /*
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder.setSocketTimeout(10000);
            }
        });*/
        //设置一个回调函数，允许修改HTTP客户端配置（如SSL加密通信，或任何的org.apache.http.impl.nio.client.httpasyncclientbuilder允许设置）
        /*
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setProxy(new HttpHost("proxy", 9000, "http"));
            }
        });*/
        //endregion
    }
    /**
     * 关闭
     * @throws IOException
     */
    public static void esStop() throws IOException {
        restClient.close();
    }

    /**
     * 读取请求
     * @throws IOException
     */
    public static String PerFromingRequest(String method,String url,Map<String ,String > parm,HttpEntity httpEntity ) throws IOException {
        String responseBody="";
        try {
            Response response = restClient.performRequest(method,url,parm,httpEntity);
            RequestLine requestLine = response.getRequestLine();
            HttpHost host = response.getHost();
            int statusCode = response.getStatusLine().getStatusCode();
            Header[] headers = response.getHeaders();
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            responseBody = IOUtils.toString(new InputStreamReader(content, Charsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        esStop();
        return responseBody;
    }

    /**
     * 添加索引
     * @param index
     * @return
     * @throws IOException
     */
    public static String AddIndex(String index) throws IOException {
        Map<String,String> params= Collections.emptyMap();
        String result="";
        try {
            result = PerFromingRequest("PUT", index, params, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }




    /**
     * 请求注释
     * @return
     * @throws IOException
     */
    public  static String PerFromingRequests() throws IOException {
        //通过只提供谓词和端点来发送请求，所需参数的最小集合
        //Response response = restClient.performRequest("GET", "/");

        //发送一个请求提供的动词，端点，和一些查询字符串参数
        //Map<String, String> params = Collections.singletonMap("pretty", "true");
        //Response response = restClient.performRequest("GET", "/", params);

        //发送一个请求提供的动词，端点，可选的查询字符串参数和封闭在一个org.apache.http.httpentity对象请求体
        /*
        Map<String, String> params = Collections.emptyMap();
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("PUT", "/posts/doc/1", params, entity);
        */
        //发送一个请求提供的动词，端点，可选的查询字符串参数，可选的请求体和可选的工厂是用来创建一个org.apache.http.nio.protocol.httpasyncresponseconsumer回调实例的每个请求的尝试。控制响应体如何从客户端上的非阻塞HTTP连接获得流式传输。当未提供时，使用默认实现，该缓冲区缓冲堆内存中的整个响应体，最多为100 MB。
        /*
        Map<String, String> params = Collections.emptyMap();
        HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory consumerFactory =
                new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024);
        Response response = restClient.performRequest("GET", "/posts/_search", params, null, consumerFactory);
        */
        //定义当请求成功执行时需要发生什么
        //
        //定义当请求失败时需要发生什么，这意味着每当出现连接错误或返回带有错误状态代码的响应时。
        //
        //发送一个异步请求只提供动词，端点，和响应的听众可以一次完成请求所需的最小集合的通知，争论
        /*
        ResponseListener responseListener = new ResponseListener() {
            @Override
            public void onSuccess(Response response) {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        };
        restClient.performRequestAsync("GET", "/", responseListener);
        */
        return"";
    }
    //endregion

}

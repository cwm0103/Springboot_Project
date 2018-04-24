package com.cwm.elastic.elasticController;

import com.alibaba.fastjson.JSON;
import com.cwm.elastic.elasticapi.elasticsearchApi;
import com.cwm.elastic.elasticapi.elasticsearchHighAPI.ESCMothed;
import entity.FileUpload;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("elasticApi")
public class ApiController {

    @Autowired
    private RestHighLevelClient rhlClient;

    /**
     * 默认构造函数
     *
     * @throws IOException
     */
    private ApiController() throws IOException {
        //elasticsearchApi.esStart();
    }

    @RequestMapping("/low_search")
    @ResponseBody
    public String search() throws IOException {
        Map<String, String> params = Collections.emptyMap();
        String body = elasticsearchApi.PerFromingRequest("GET", "index1/pub_file_upload/_search", params, null);

        return body;
    }

    @RequestMapping("high_search")
    @ResponseBody
    public String highsearch() throws IOException {

        SearchResponse search = ESCMothed.search("index1", "pub_file_upload", rhlClient, null);
        return search.toString();

    }
    @RequestMapping("add")
    @ResponseBody
    public String highAdd() {
        FileUpload fileUpload = new FileUpload();
        fileUpload.setCategory("测试");
        fileUpload.setFileCreateTime(new Date());
        fileUpload.setFileId(1000);
        fileUpload.setFileName("测试");
        fileUpload.setFilePath("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png");
        fileUpload.setFileSaveName("1280w_1l_2o_100sh");
        fileUpload.setFileSize(Long.parseLong("199492"));
        fileUpload.setFileType(".png");
        String json = JSON.toJSON(fileUpload).toString();
        ESCMothed.add("index1", "pub_file_upload", json,rhlClient);
        return "";
    }

    @RequestMapping("update")
    @ResponseBody
    public String update()
    {
        Map<String,String> map=new HashMap<>();
        map.put("file_name","测试目录.jpg");
        ESCMothed.upate("index1","pub_file_upload",rhlClient,map,"2");
        return "";
    }
    @RequestMapping("delete")
    @ResponseBody
    public String delete()
    {

       // ESCMothed.delete("index1","pub_file_upload",rhlClient,"VI6J9WIBLRdAPT66EfBF");
        //ESCMothed.queryDelete("index1","pub_file_upload",rhlClient,"1目录.jpg");
            return "";
    }
}




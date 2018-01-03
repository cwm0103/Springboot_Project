package com.bom.dataservice;

import com.alibaba.fastjson.JSON;
import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.GroupBy;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springfox.documentation.staticdocs.SwaggerResultHandler;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-11
 * Time: 14:24
 */
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@RunWith(SpringRunner.class)
@SpringBootTest
public class Documentation {
    private String snippetDir = "target/generated-snippets";
    private String outputDir = "target/asciidoc";
    //private String indexDoc = "docs/asciidoc/index.adoc";

    @Autowired
    private MockMvc mockMvc;

    @After
    public void Test() throws Exception{
        // 得到swagger.json,写入outputDir目录中
        mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON))
                .andDo(SwaggerResultHandler.outputDirectory(outputDir).build())
                .andExpect(status().isOk())
                .andReturn();

        // 读取上一步生成的swagger.json转成asciiDoc,写入到outputDir
        // 这个outputDir必须和插件里面<generated></generated>标签配置一致
        Swagger2MarkupConverter.from(outputDir + "/swagger.json")
                .withPathsGroupedBy(GroupBy.TAGS)// 按tag排序
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)// 格式
                .withExamples(snippetDir)
                .build()
                .intoFolder(outputDir);// 输出
    }

    @Test
    public void TestApi() throws Exception{

        Map<String,Object> paraMap1=new LinkedHashMap<>();
        paraMap1.put("token","asdfasd");
        paraMap1.put("projectId","123456");
        paraMap1.put("type","HH");
        paraMap1.put("codes","dddd");
        paraMap1.put("date","2017-10-11 13:00:00");
        System.out.println(JSON.toJSONString(paraMap1));
        mockMvc.perform(post("/ds/getdata").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(paraMap1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(document("getdata", preprocessResponse(prettyPrint())));

        Map<String,Object> paraMap=new LinkedHashMap<>();
        paraMap.put("token","asdfasd");
        paraMap.put("projectId","123456");
        paraMap.put("type","HH");
        paraMap.put("codes","dddd,33344");
        paraMap.put("beginDate","2017-10-11 13:00:00");
        paraMap.put("endDate","2017-10-11 22:00:00");
        System.out.println(JSON.toJSONString(paraMap));
        mockMvc.perform(post("/ds/getldata").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(paraMap))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(document("getldata", preprocessResponse(prettyPrint())));
        Map<String,Object> paraMap2=new LinkedHashMap<>();
        paraMap2.put("token","asdfasd");
        paraMap2.put("projectId","123456");
        paraMap2.put("type","HH");
        paraMap2.put("codes","dddd,1111");
        paraMap2.put("beginDate","2017-10-11 13:00:00");
        paraMap2.put("endDate","2017-10-11 22:00:00");
        mockMvc.perform(post("/ds/getkldata").contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(paraMap2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(document("getldata", preprocessResponse(prettyPrint())));

    }

}

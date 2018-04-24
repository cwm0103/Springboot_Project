package com.cwm.elastic.elasticapi.elasticsearchHighAPI;



import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class ESCMothed {
    //region add
    /**
     *  添加方法
     */
    public static void add(String index, String type,String source,RestHighLevelClient rhlClient)
    {
        IndexRequest indexRequest = new IndexRequest(index, type);
        indexRequest.source(source, XContentType.JSON);
        try {
            rhlClient.index(indexRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 批量添加
     * @param index
     * @param type
     * @param source
     * @param rhlClient
     */
    public static void batchAdd(String index,String type,List<String> source,RestHighLevelClient rhlClient)
    {
        BulkRequest bulkRequest = new BulkRequest();
        List<IndexRequest> requests = generateRequests(index,type,source);
        for (IndexRequest indexRequest : requests) {
            bulkRequest.add(indexRequest);
        }
        try {
            rhlClient.bulk(bulkRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 制造一个
     * @param index
     * @param type
     * @param source
     * @return
     */
    public static IndexRequest generateNewsRequest(String index,String type,String source){
        IndexRequest indexRequest = new IndexRequest(index, type);
        indexRequest.source(source, XContentType.JSON);
        return indexRequest;
    }

    /**
     * 添加多个
     * @param index
     * @param type
     * @param source
     * @return
     */
    public static List<IndexRequest> generateRequests(String index,String type,List<String> source){
        List<IndexRequest> requests = new ArrayList<>();
        if(source.size()>0)
        {
            for (String item:source) {
                requests.add(generateNewsRequest(index, type, item));
            }
        }
        return requests;
    }
    //endregion

    //region search
    /**
     * 查询
     * @param index
     * @param type
     * @param rhlClient
     * @param map
     * @return
     */
    public static SearchResponse search(String index, String type, RestHighLevelClient rhlClient, Map<String,String> map)
    {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10);

        //sourceBuilder.fetchSource(new String[]{"title"}, new String[]{});
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "费德勒");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("tag", "体育");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime");
        rangeQueryBuilder.gte("2018-01-26T08:00:00Z");
        rangeQueryBuilder.lte("2018-01-26T20:00:00Z");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(matchQueryBuilder);
        boolBuilder.must(termQueryBuilder);
        boolBuilder.must(rangeQueryBuilder);
        //sourceBuilder.query(boolBuilder);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types(type);
        searchRequest.source(sourceBuilder);
        try {

           SearchResponse response= rhlClient.search(searchRequest);
            System.out.println(response);
            return response;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    //endregion

    //region update
    /**
     * 修改
     * @param index
     * @param type
     * @param rhlClient
     * @param map
     * @param id
     */
    public static void upate(String index,String type,RestHighLevelClient rhlClient,Map<String,String>map,String id)
    {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(map);
        try {
            UpdateResponse update = rhlClient.update(updateRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //endregion

    //region  del

    /**
     * 根据id删除
     * @param index
     * @param type
     * @param rhlClient
     * @param id
     */
    public static void delete(String index,String type,RestHighLevelClient rhlClient,String id)
    {
        DeleteRequest deleteRequest = new DeleteRequest(index,type,id);
        DeleteResponse response = null;
        try {
            response = rhlClient.delete(deleteRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(response);
    }

    /**
     * 查询删除
     */
    public static void queryDelete(String index,String type,RestHighLevelClient rhlClient,String match)
    {
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.timeout(new TimeValue(2, TimeUnit.SECONDS));
            TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery("file_name", match);
            sourceBuilder.query(termQueryBuilder1);
            SearchRequest searchRequest = new SearchRequest(index);
            searchRequest.types(type);
            searchRequest.source(sourceBuilder);
            SearchResponse response = rhlClient.search(searchRequest);
            SearchHits hits = response.getHits();
            List<String> docIds = new ArrayList<>(hits.getHits().length);
            for (SearchHit hit : hits) {
                docIds.add(hit.getId());
            }

            BulkRequest bulkRequest = new BulkRequest();
            for (String id : docIds) {
                DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
                bulkRequest.add(deleteRequest);
            }
            rhlClient.bulk(bulkRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion

}

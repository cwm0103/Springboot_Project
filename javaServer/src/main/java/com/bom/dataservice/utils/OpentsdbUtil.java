package com.bom.dataservice.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bom.dataservice.model.OpentsdbParam;
import com.bom.dataservice.model.TSDBResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-25
 * Time: 15:43
 */
public class OpentsdbUtil {
    protected static Logger logger = LoggerFactory.getLogger(OpentsdbUtil.class);

    /**
     * OpenTSDBQuery 数据查询
     *
     * @param beginDate  开始时间
     * @param endDate    结束时间
     * @param codes      测点code
     * @param downsample 间隔表达试
     * @param staId      站id
     * @param url        查询URL
     * @return 查询后的json字符串
     */
    public static String OpenTSDBQuery(Date beginDate, Date endDate, String codes, String downsample, String staId, String aggregator, String url) {
        //  url="http://10.37.147.243:4242/api/query";
        OpentsdbParam param = new OpentsdbParam();
        long begin = beginDate.getTime();
        long end = endDate.getTime();
        param.setStart(Long.toString(begin));
        param.setEnd(Long.toString(end));
        JSONArray querieList = new JSONArray();
        String[] arrCodes = codes.split(",");
        for (String code : arrCodes) {
            if (StringUtils.isNotBlank(code)) {
                JSONObject querie = createQuerie(code, downsample, staId, aggregator);
                querieList.add(querie);
            }
        }
        param.setQueries(querieList);
       // logger.info("参数信息：" + JSON.toJSONString(param));
        //System.out.println(JSON.toJSONString(param));
        String content = HTTPUtil.postContent(url, JSON.toJSONString(param));
        System.out.println(content);
       // logger.info("结果信息：" + content);
        return content;
    }
    public static String OpenTSDBQuery(Date beginDate, Date endDate, String codes, String downsample, String aggregator, String url) {
        //  url="http://10.37.147.243:4242/api/query";
        OpentsdbParam param = new OpentsdbParam();
        long begin = beginDate.getTime();
        long end = endDate.getTime();
        param.setStart(Long.toString(begin));
        param.setEnd(Long.toString(end));
        JSONArray querieList = new JSONArray();
        String[] arrCodes = codes.split(",");
        for (String code : arrCodes) {
            if (StringUtils.isNotBlank(code)) {
                JSONObject querie = createQuerie(code, downsample, aggregator);
                querieList.add(querie);
            }
        }
        param.setQueries(querieList);
       // logger.info("参数信息：" + JSON.toJSONString(param));
        //System.out.println(JSON.toJSONString(param));
        String content = HTTPUtil.postContent(url, JSON.toJSONString(param));
        System.out.println(content);
       // logger.info("结果信息：" + content);
        return content;
    }

    public static  String OpenTSDBQueryLast(){
        return "";
    }

    /**
     * 解析opentsdb查询结果
     *
     * @param result
     * @return 结果实体
     */
    public static List<TSDBResult> paseTSDBResult(String result) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TSDBResult> resultList = new LinkedList<>();
        if (StringUtils.isNotBlank(result)) {
            JSONArray jsonArray = JSONObject.parseArray(result);
            if (jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject dpsJson = jsonObject.getJSONObject("dps");
                    String metricJson = jsonObject.getString("metric");
                    JSONObject tagsJson = jsonObject.getJSONObject("tags");
                    if(dpsJson.size()>0) {
                        List<Map.Entry<String, Object>> maplist = new ArrayList<Map.Entry<String, Object>>((Collection<? extends Map.Entry<String, Object>>) dpsJson.entrySet());
                        Collections.sort(maplist, (o1, o2) -> {
                            return o1.getKey().compareTo(o2.getKey());
                        });

                        String code = tagsJson.getString("equipMK") + "_" + tagsJson.getString("equipID") + "_" + metricJson.replace("fns.", "");
                        for (Map.Entry<String, Object> sortmap : maplist) {
                            TSDBResult tsdbResult = new TSDBResult();
                            long time = Long.parseLong(sortmap.getKey());
                            Date date = new Date(time * 1000);
                            tsdbResult.setCode(code);
                            tsdbResult.setDate(sdf.format(date));
                            BigDecimal value;
                            if (sortmap.getValue() == null)
                                value = null;
                            else if (sortmap.getValue().equals(0))
                                value = new BigDecimal((int) sortmap.getValue());
                            else
                                value = (BigDecimal) sortmap.getValue();
                            tsdbResult.setValue(value);
                            //System.out.println(sdf.format(date) + "(" + sortmap.getValue() + ")");
                            resultList.add(tsdbResult);
                        }
                    }else{
                        TSDBResult tsdbResult = new TSDBResult();
                        tsdbResult.setValue(new BigDecimal(0));
                        String code = tagsJson.getString("equipMK") + "_" + tagsJson.getString("equipID") + "_" + metricJson.replace("fns.", "");
                        tsdbResult.setCode(code);
                        resultList.add(tsdbResult);

                    }
                }
            }
        }
       /* for (TSDBResult tsdbResult:resultList) {
            System.out.println(tsdbResult.getCode() + "\t" + tsdbResult.getValue() + "\t" +tsdbResult.getDate() +"\t" + sdf.format(new Date())  );
        }*/
        return resultList;
    }

    private static JSONObject createQuerie(String code, String downsample, String aggregator) {
        if (StringUtils.isBlank(aggregator)) {
            aggregator = "none";
        }
        JSONObject object = new JSONObject();
        JSONObject tagJson = new JSONObject();
        String[] codes = code.split("_");
        object.put("aggregator", aggregator);
        object.put("downsample", downsample);//1h-first-none
        object.put("tags", tagJson);
        object.put("metric", "fns." + codes[3]);
        tagJson.put("staId", codes[0]);
        tagJson.put("equipID", codes[2]);
        tagJson.put("equipMK", codes[1]);
        return object;
    }
    private static JSONObject createQuerie(String code, String downsample, String staId, String aggregator) {
        if (StringUtils.isBlank(aggregator)) {
            aggregator = "none";
        }
        JSONObject object = new JSONObject();
        JSONObject tagJson = new JSONObject();
        String[] codes = code.split("_");
        object.put("aggregator", aggregator);
        object.put("downsample", downsample);//1h-first-none
        object.put("tags", tagJson);
        object.put("metric", "fns." + codes[2]);
        tagJson.put("staId", staId);
        tagJson.put("equipID", codes[1]);
        tagJson.put("equipMK", codes[0]);
        return object;
    }

    //region 光伏openstb

    /**
     * OpenTSDBQuery 数据查询
     *
     * @param beginDate  开始时间
     * @param endDate    结束时间
     * @param codes      测点code
     * @param downsample 间隔表达试
     * @param staId      站id
     * @param url        查询URL
     * @return 查询后的json字符串
     */
    public static String OpenTSDBQuery_Spv(Date beginDate, Date endDate, String codes, String equipID, String downsample, String staId, String url) {
        //url = "http://10.37.147.243:4242/api/query";
        OpentsdbParam param = new OpentsdbParam();
        long begin = beginDate.getTime();
        long end = endDate.getTime();
        param.setStart(Long.toString(begin));
        param.setEnd(Long.toString(end));
        JSONArray querieList = new JSONArray();
        String[] arrCodes = codes.split(",");
        for (String code : arrCodes) {
            if (StringUtils.isNotBlank(code)) {
                JSONObject querie = createQuerie_Spv(code, equipID, downsample, staId);
                querieList.add(querie);
            }
        }
        param.setQueries(querieList);
       // logger.info("参数信息：" + JSON.toJSONString(param));
        System.out.println(JSON.toJSONString(param));
        String content = HTTPUtil.postContent(url, JSON.toJSONString(param));
        System.out.println(content);
      //  logger.info("结果信息：" + content);
        return content;
    }

    /**
     * 解析opentsdb查询结果
     *
     * @param result
     * @return 结果实体
     */
    public static List<TSDBResult> paseTSDBResult_Spv(String result) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TSDBResult> resultList = new LinkedList<>();
        JSONArray jsonArray = JSONObject.parseArray(result);
        if (jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject dpsJson = jsonObject.getJSONObject("dps");
                String metricJson = jsonObject.getString("metric");
                JSONObject tagsJson = jsonObject.getJSONObject("tags");
                List<Map.Entry<String, Object>> maplist = new ArrayList<Map.Entry<String, Object>>((Collection<? extends Map.Entry<String, Object>>) dpsJson.entrySet());
                Collections.sort(maplist, (o1, o2) -> {
                    return o1.getKey().compareTo(o2.getKey());
                });
                String code = tagsJson.getString("equipMK") + "_" + tagsJson.getString("equipID") + "_" + metricJson.replace("spv.", "");
//                for (Map.Entry<String, Object> sortmap : maplist) {
//                    TSDBResult tsdbResult = new TSDBResult();
//                    long time = Long.parseLong(sortmap.getKey());
//                    Date date = new Date(time * 1000);
//                    tsdbResult.setCode(code);
//                    tsdbResult.setDate(sdf.format(date));
//                    tsdbResult.setValue((BigDecimal) sortmap.getValue());
//                    //System.out.println(sdf.format(date) + "(" + sortmap.getValue() + ")");
//                    resultList.add(tsdbResult);
//                }
                Map.Entry<String, Object> stringObjectEntry = maplist.get(maplist.size()-1);

                TSDBResult tsdbResult=new TSDBResult();
                long time = Long.parseLong(stringObjectEntry.getKey());
                Date date = new Date(time * 1000);
                tsdbResult.setCode(code);
                tsdbResult.setDate(sdf.format(date));
                //Object value = stringObjectEntry.getValue();
                //BigDecimal bigDecimal = BigDecimal.valueOf((Integer) value);
                tsdbResult.setDatavalue(stringObjectEntry.getValue().toString());
                resultList.add(tsdbResult);
            }
        }
        return resultList;
    }


    private static JSONObject createQuerie_Spv(String code, String equipID, String downsample, String staId) {
        JSONObject object = new JSONObject();
        JSONObject tagJson = new JSONObject();
        String[] codes = code.split("_");
        object.put("aggregator", "none");
        object.put("downsample", downsample);//1h-first-none
        object.put("tags", tagJson);
        object.put("metric", "spv." + codes[2]);
        tagJson.put("staId", staId);
        tagJson.put("equipID", codes[1]);
        tagJson.put("equipMK", codes[0]);
        return object;
    }
    //endregion
}

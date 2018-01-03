import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bom.domain.data.model.OpentsdbParam;
import com.bom.domain.data.model.Querie;
import com.bom.domain.data.model.TSDBResult;
import com.bom.domain.data.model.TSDBTag;
import com.bom.utils.HTTPUtil;
import com.bom.utils.OpentsdbUtil;
import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: jindb
 * Date: 2017-11-21
 * Time: 19:05
 */

public class ToolsTest {
    @Test
    public void test() throws ParseException {

       /* SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        Date beginDate=sdf.parse("2017-11-21 00:00:00");
        long begintimes = beginDate.getTime();
        Date endDate=sdf.parse("2017-11-24 00:00:00");
        long endtimes = endDate.getTime();
        System.out.println(begintimes);""
        System.out.println(endtimes);*/



        //region spv
        String strbeginDate_spv = "2017-11-28 10:25:00";//DateTime.now().toString("yyyy-MM-dd HH:00:00");
        SimpleDateFormat sdf_spv = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strendDate_spv = "2017-11-28 10:35:00";
        Date beginDate_spv=sdf_spv.parse(strbeginDate_spv);
        Date endDate_spv= sdf_spv.parse(strendDate_spv);//new Date();
        String codes_spv="IV_IVS101_Eday";
        String staid_spv="442";
        String equipID_spv="IVS101";
        //String downsample_spv="1d-first-none";
        String downsample_spv=null;
       // downsample_spv="1n-last";
        String content_spv= "";//OpentsdbUtil.OpenTSDBQuery_Spv(beginDate_spv,endDate_spv,codes_spv,equipID_spv,downsample_spv,staid_spv,"");
        List<TSDBResult> resultList_spv=null;//OpentsdbUtil.paseTSDBResult_Spv(content_spv);
        System.out.println("213");
        //endregion

       //region fns

        String strbeginDate ="2017-11-27 05:00:00";//DateTime.now().toString("yyyy-MM-dd HH:00:00");

        //String strbeginDate =DateTime.now().toString("yyyy-MM-dd HH:00:00");




        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate=sdf.parse(strbeginDate);
        Date endDate=new Date();
        String codes="MFOD_TW_FwInt,,CEU_FIQ105_FsInt,GSB_GSB1_FfuelInt,GSB_GSB2_FfuelInt,GSB_GSB3_FfuelInt,ES_LYNC_FwaterInt,ES_LYNC_Pconsum,ES_LYNC_Econsum,ES_LYNC_Ffuel,ES_LYNC_FfuelInt,ES_LYNC_Fs,ES_LYNC_FsInt,ACLN_AH10101_EPT";
        String staid="1009";
        String downsample="1d-first-none";
        downsample="1n-last";
//        String content= "";//OpentsdbUtil.OpenTSDBQuery(beginDate,endDate,codes,downsample,staid,"");
//        List<TSDBResult> resultList=null;//OpentsdbUtil.paseTSDBResult(content);
        //endregionf
        System.out.println("213");





    }
}

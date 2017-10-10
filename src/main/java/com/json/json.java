package com.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.json.msc.MSCLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.StringReader;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/5/11.
 */
public class json {

    private static final Log logger = LogFactory.getLog(json.class);

    @Test
    public void testJson() {

        String str = "{\"extinfo\":{\"client_ip\":\"123.179.63.249\"},\"logtype\":\"mscsessioninfo\",\"up_time\":\"2017/05/07 06:32:52 164\",\"net\":{\"n_frsl\":3111,\"n_sok\":4059,\"n_lrsl\":0,\"n_frs\":4059,\"n_con\":[{\"cok\":167,\"con\":12,\"used\":1}],\"n_sse\":20809507,\"n_pver\":1,\"n_txt\":167,\"n_rsc\":5,\"n_tgrsl\":10675,\"n_ced\":[12,14,30,32,40],\"n_ssb\":167},\"sess\":{\"sid\":\"tts62a47369@li00af0c6949ca861d00\",\"sip\":\"120.197.234.167:1028\",\"cver\":\"5.0.12.1089\",\"etime\":\"2017/05/07 06:32:52 112\",\"sip_des\":\"12#120.197.234.167:1028\",\"url\":\"http://lxzhangyue.lingxicloud.com/msp.do\",\"des\":\"MSC:[soc] [0] error 10205 57!\",\"sub\":\"tts\",\"efun\":\"on_transport_exception for tts\",\"stime\":\"2017/05/07 00:46:02 606\",\"csid\":\"ctts1PPHHBHewZ80wxKUh1aVCbP1j3u8t51g\",\"aue\":\"speex-wb;7\",\"appid\":\"543cc1ce\",\"ret\":10205,\"encode_cost\":2,\"n_dns\":1236,\"auf\":\"audio/L16;rate=16000\",\"uid\":\"d4878665330\",\"dtime\":\"2017/05/07 00:43:31 424\",\"ntt\":\"wifi\"},\"user\":{\"u_frsl\":32640,\"u_frs\":4079,\"u_tgrsl\":112000,\"u_sse\":20809507,\"u_txt\":14,\"u_lrsl\":26240,\"u_lrs\":7148,\"u_rsc\":5},\"logver\":\"1.1.9\",\"mscextinfo\":{\"total\":12516,\"errs\":[10131,10131,10131,10114,10114,10131,10131,10131,10205],\"fail\":9},\"os\":{\"os_sys\":\"MacOS X\"}} ";
        MSCLog mscLog = null;
        try {
            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(new StringReader(str));
            jsonReader.setLenient(true);
            mscLog = gson.fromJson(jsonReader, MSCLog.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
        }
        System.out.println(mscLog);
    }


    @Test
    public void jsonIterator() throws JSONException {
//        String str = "{ \"_id\" : { \"$oid\" : \"59b2530ba0704068e8e9a67e\" }, \"time\" : 2.0170908E7, \"item\" : \"HPU\", \"userid\" : \"223456789\", \"item111\" : \"HPU\" }";
        String str = "{ \"_id\" : 10.0, \"userid\" : \"777\", \"time\" : 2.0170919E7, \"item88\" : 88.0 }";
        JSONObject jsonObj = new JSONObject(str);
        Iterator it = jsonObj.keys();
        while (it.hasNext()) {
           String key = (String) it.next();
           String value = jsonObj.getString(key);
            System.out.println(key+"---"+value);
        }
    }
}

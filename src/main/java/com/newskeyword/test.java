package com.newskeyword;

/**
 * Created by Administrator on 2017/12/27.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class test {
    public static void main(String[] args) throws Exception {
        String filePath = "D:\\Desktop\\李开复.txt";
        String tt = new String();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
        String str;
        while ((str = in.readLine()) != null) {
            tt += str;
        }
        test1(tt);

//        System.out.println("*************************");
//        filePath = "./test1.txt";
//        BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
//        String str2;
//        String tt2=new String();
//        while ((str2 = in2.readLine()) != null) {
//            tt2+=str2;
//        }
//        test1(tt2);

    }

    public static void test1(String content) throws Exception {
        GetStopWordList getStopWordList = new GetStopWordList();
        Map<String, List> map = getStopWordList.getStopWordList();
        List<String> list_c = map.get("MacroDef.STOP_CHINESE");
        List<String> list_e = map.get("MacroDef.STOP_ENGLISH");

        String title = "李开复：无人驾驶进入黄金时代 AI有巨大投资机会";
        KeyWordComputer key = new KeyWordComputer(10);
        Iterator it = key.computeArticleTfidf(title,content).iterator();
        while (it.hasNext()) {
//            boolean flag = true;
            Keyword key2 = (Keyword) it.next();
//            for (String str_c : list_c) {
//                if (str_c.equals(key2.toString()))
//                    flag = false;
//            }
//            for (String str_e : list_e) {
//                if (str_e.equals(key2.toString()))
//                    flag = false;
//            }
//            if (flag) {
                System.out.println(key2.toString() + key2.getScore());
//            }
        }
    }
}

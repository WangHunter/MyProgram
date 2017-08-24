package com.File;

import com.google.common.annotations.VisibleForTesting;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/4/1.
 */
public class TestFile {

    public void changeFiletime(String filename) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        File fileToChange = new File(filename);

        //读取文件(夹)的最后修改时间
        Date filetime = new Date(fileToChange.lastModified());
        System.out.println(formatter.format(filetime));

        //将最近修改时间修改为当前时间
//        if (fileToChange.setLastModified(System.currentTimeMillis()))
//            System.out.println("Success!");
//        else
//            System.out.println("Failed!");

        //读取最近更新时间
//        filetime = new Date(fileToChange.lastModified());
//        System.out.println(formatter.format(filetime));

    }

    /**
     * 列出路径下的所有文件
     * @param path 文件路径
     */
    public void listFile(String path) throws IOException {
        Map<String,Long> mapDat = new HashMap<>();
        Map<String,String> mapChk = new HashMap<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");

        File file = new File(path);
        File[] tempList = file.listFiles();
//        System.out.println("该目录下对象个数：" + tempList.length);
        for (int i = 0; i < tempList.length; i++) {

            //获取数据文件.dat
            if(tempList[i].isFile() && tempList[i].toString().toLowerCase().endsWith(".dat")){
                if(tempList[i].toString().substring(38,48).equalsIgnoreCase("2017022703")){
//                    System.out.println(tempList[i].toString().substring(28)+"-----"+TestFile.getFileSize(tempList[i].toString()));
                    mapDat.put(tempList[i].toString().substring(28),TestFile.getFileSize(tempList[i].toString()));
                    if(String.valueOf(TestFile.getFileSize(tempList[i].toString())).equalsIgnoreCase(mapChk.get(tempList[i].toString().substring(28)))){
                        System.out.println("SUCCESS");  //校验成功才进行传输到HDFS
                    }
                }
            }
//            System.out.println(mapDat);
            //获取校验文件.chk
            if (tempList[i].isFile() && tempList[i].toString().toLowerCase().endsWith(".chk")) {
                if(tempList[i].toString().substring(38,48).equalsIgnoreCase("2017022703")){
//                    System.out.println(tempList[i]);
                    FileReader fr = new FileReader(tempList[i]);
                    BufferedReader br = new BufferedReader(fr);
                    String str = null;
                    while((str = br.readLine())!=null){
                        String[] chkSplit = str.split("�");
                        mapChk.put(chkSplit[0],chkSplit[1]);
//                        System.out.println(chkSplit[1]+"--"+String.valueOf(mapDat.get(chkSplit[0])));
                    }

                }
            }

            /*if (tempList[i].isDirectory()) {
                System.out.println("文件夹：" + tempList[i]);
            }*/
        }
    }

    /**
     * 获取系统前num小时的时间
     * @return 2017040608
     */
    @Test
    public String getTime(int num){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - num);
//        System.out.println(formatter.format(cal.getTime()));
        return formatter.format(cal.getTime());
    }

    /**
     * 获取文件大小
     * @param filePath 文件路径
     * @return 752590694
     */
    public static long getFileSize(String filePath){
        File file = new File(filePath);
        return file.length();
    }

    public static void main(String[] args) throws IOException {
        TestFile fileutil = new TestFile();
//        fileutil.changeFiletime("/data/client");
        fileutil.listFile("D:/desktop/kehuduan20170227");
//        fileutil.getTime(1);
    }
}

package com.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
public class ReadAvro {

    List listFile = new ArrayList<>();


    public void printExtraction() throws IOException, InterruptedException {
        GenericDatumReader<GenericData.Record> dr = new GenericDatumReader<GenericData.Record>();

        List fileList = getFile("/data/idata/mss/MspData/2017-05-17/13/current/");
        for (int i = 0; i < listFile.size(); i++) {
//            System.out.println("读取的文件为："+listFile.get(i).toString());
            DataFileReader<GenericData.Record> reader = new DataFileReader(new File("/data/idata/mss/MspData/2017-05-17/13/current/-r-00021/data.avro"), dr);
//        DataFileReader<GenericData.Record> reader=new DataFileReader(new File("/data/idata/mss/MspData/2017-05-17/13/current/-r-00004/data.avro"),dr);
            FileOutputStream out = null;
            out = new FileOutputStream(new File("/data/idata/mss/asr.txt"));
            String str ="";

            try {
                while (reader.hasNext()) {
                    if ((str=reader.next().toString()).substring(9, 12).equals("asr")) {
                        System.out.println(new String(str.getBytes("utf-8"), "GB2312"));
                        out.write(str.getBytes());
                        Thread.currentThread().sleep(20000000l);
                    }
//                GenericData.Record student=reader.next();
//                System.out.print(student.get("Name")+" "+student.get("Phone")+"\r\n");

                }
            } finally {
                reader.close();
            }
        }
    }

    /**
     * 获取目录下文件
     * @param src
     * @return
     */
    public List getFile(String src) {
        File file = new File(src);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()&&tempList[i].getName().endsWith(".avro")&&!tempList[i].getName().startsWith("index")) {
                listFile.add(tempList[i]);
            }
            if (tempList[i].isDirectory()) {
                getFile(tempList[i].toString());
            }
        }
        return listFile;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("执行获取asr日志jar");
        ReadAvro readAvro = new ReadAvro();
        readAvro.printExtraction();
    }

}

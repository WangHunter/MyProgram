package com.HDFS;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class hdfsGYT {

    private static  final String HDFS = "hdfs://server1-34:8020";

    public hdfsGYT(String hdfs,  Configuration conf ){
        this.hdfsPath = hdfs;
        this.conf = conf;
    }

    public hdfsGYT() {
        // TODO Auto-generated constructor stub
    }

    private String hdfsPath;
    Configuration config = getConf();
    private Configuration conf = new Configuration();

    //显示文件
    private void cat(String folder) throws IOException, URISyntaxException {
        // 与hdfs建立联系
        FileSystem fs = FileSystem.get(new URI(HDFS),new Configuration());
        Path path = new Path(folder);
        FSDataInputStream fsdis = null;
        System.out.println("cat: " + folder);
        try {
            fsdis =fs.open(path);
            IOUtils.copyBytes(fsdis, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(fsdis);
            fs.close();
        }
    }

    //删除文件
    private void rm(String folder) throws IOException, URISyntaxException {
        //与hdfs建立联系
        FileSystem fs = FileSystem.get(new URI(HDFS),new Configuration());
        Path path = new Path(folder);
        if(fs.deleteOnExit(path)){
            fs.delete(path);
            System.out.println("delete:" + folder);
        }else{
            System.out.println("The fiel is not exist!");
        }
        fs.close();
    }

    //下载文件
    private void get(String remote,  String local) throws IllegalArgumentException, IOException, URISyntaxException {
        // 建立联系
        FileSystem fs = FileSystem.get(new URI(HDFS), new Configuration());
        fs.copyToLocalFile(new Path(remote), new Path(local));
        System.out.println("Get From :   " + remote  + "   To :" + local);
        fs.close();
    }

    //上传文件
    private void put(String local, String remote) throws IOException, URISyntaxException, InterruptedException {
        String src = "/data/SDK/a_LX20903_2017022703_001.dat";
        String dest = "/hdfs/LX/SDK";


        // 建立联系
        FileSystem fs = FileSystem.get(config);
        fs.copyFromLocalFile(new Path(src), new Path(dest));
        System.out.println("Put :" + src  + "   To : " + dest);
        fs.close();
    }

    //递归列出所有文件夹
    private void lsr(String folder) throws IOException, URISyntaxException {
        Configuration config = getConf();
        //与hdfs建立联系
        FileSystem fs = FileSystem.get(config);
        Path path = new Path(folder);
        //得到该目录下的所有文件
        FileStatus[] fileList = fs.listStatus(path);
        for (FileStatus f : fileList) {
            System.out.printf("name: %s   |   folder: %s  |   size: %d\n", f.getPath(),  f.isDir() , f.getLen());
            try{
                FileStatus[] fileListR = fs.listStatus(f.getPath());
                for(FileStatus fr:fileListR){
                    System.out.printf("name: %s   |   folder: %s  |   size: %d\n", fr.getPath(),  fr.isDir() , fr.getLen());
                }
            }finally{
                continue;
            }
        }
        fs.close();
    }

    //列出所有文件夹
    private void ls(String folder) throws IOException, URISyntaxException {
        Configuration config = getConf();
        //与hdfs建立联系
//        FileSystem fs = FileSystem.get(new URI(HDFS),new Configuration());
        FileSystem fs = FileSystem.get(config);
        Path path = new Path(folder);
        //得到该目录下的所有文件
        FileStatus[] fileList = fs.listStatus(path);
        for (FileStatus f : fileList) {
            System.out.printf("name: %s   |   folder: %s  |   size: %d\n", f.getPath(),  f.isDir() , f.getLen());
        }
        fs.close();
    }

    //删除文件夹
    private void rmr(String folder) throws IOException, URISyntaxException {
        //与hdfs建立联系
        FileSystem fs = FileSystem.get(new URI(HDFS),new Configuration());
        Path path = new Path(folder);
        fs.delete(path);
        System.out.println("delete:" + folder);
        fs.close();
    }

    //创建文件夹
    public void mkdir(String folder) throws IOException, URISyntaxException {
        //与hdfs建立联系
        FileSystem fs = FileSystem.get(new URI(HDFS),new Configuration());
        Path path = new Path(folder);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
            System.out.println("Create: " + folder);
        }else{
            System.out.println("it is have exist:" + folder);
        }
        fs.close();
    }

    //判断某个文件夹是否存在
    private void isExist(String folder) throws IOException, URISyntaxException {
        //与hdfs建立联系
        FileSystem fs = FileSystem.get(new URI(HDFS),new Configuration());
        Path path = new Path(folder);
        if(fs.exists(path)){
            System.out.println("it is have exist:" + folder);
        }else{
            System.out.println("it is not exist:" + folder);
        }
        fs.close();
    }

    //文件系统连接到 hdfs的配置信息
    private static Configuration getConf(){
        Configuration conf = new Configuration();
        // 这句话很关键，这些信息就是hadoop配置文件中的信息
//        conf.set("mapred.job.tracker","master153:9001");
        conf.set("fs.default.name","hdfs://master153:8020");
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        return conf;
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        hdfsGYT hdfsgyt = new hdfsGYT();
//        String folder = HDFS + "mr/groom_system/small2.csv";
//        String local = "/home/thinkgamer/Java/hadoop_shizhan/src/user_thing_tuijian/small2.csv";
//        String local1 = "/home/thinkgamer/Java/hadoop_shizhan/src/user_thing_tuijian";
        //判断某个文件夹是否存在
        //hdfsgyt.isExist(folder);
        //创建文件夹
        //hdfsgyt.mkdir(folder);
        //删除文件夹
        //hdfsgyt.rmr(folder);
        //列出所有文件夹
//        hdfsgyt.ls("/hdfs/LX/MSC/");
        //递归列出所有文件夹
        hdfsgyt.lsr("/hdfs/LX/MSC/");
        //上传文件
//        hdfsgyt.put(local, folder);
        //下载文件
//        hdfsgyt.get(folder,local1);
        //删除文件
        //hdfsgyt.rm(folder);
        //显示文件
        //hdfsgyt.cat(folder);
    }

}
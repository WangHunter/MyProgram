package com.FTP;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import java.io.*;
import java.net.SocketException;
import java.util.Properties;

/**
 * Created by ding on 1/16/16.
 */
public class FTPClientUtils {
    private  static Logger logger = Logger.getLogger(FTPClientUtils.class);
    public static String FTPCONFIG= "config/ftpConfig.properties";
    private static  FTPClient ftpClient = null; //FTP 客户端代理

    //Get FTP config
    public static String getFTPSetting(String key){
        String value="";
        String path =FTPClientUtils.class.getClassLoader().getResource("").getPath() + FTPCONFIG;
        try {
            Properties properties = new Properties();
            InputStream in = new FileInputStream(path);
            BufferedReader bis = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            properties.load(in);
//            properties.load(bis);
            value = properties.getProperty(key);
        }catch (IOException e){
            logger.info(" ftp 配置文件解析error：",e);
            e.printStackTrace();
        }
        return value;
    }



    //open FTP connection,return true=success ,false=failure
    public static  boolean openConnectFTPService() {
        boolean flag = true;
        try {
            ftpClient = new FTPClient();
//        FTPClientConfig config = new FTPClientConfig();
//        config.setXXX(YYY); // change required options
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setDefaultPort(Integer.parseInt(getFTPSetting("port")));
            ftpClient.connect(getFTPSetting("ip"),Integer.parseInt(getFTPSetting("port")));
            ftpClient.login(getFTPSetting("username"), getFTPSetting("password"));
            int reply = ftpClient.getReplyCode();
            ftpClient.setDataTimeout(Integer.parseInt(getFTPSetting("dataTimeOut")));
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                logger.info("FTP服务器拒绝连接！");
                flag = false;
            }
            System.err.println("FTP server  connection success.");
        } catch (SocketException e){
            logger.info("登陆FTP服务器ip:"+getFTPSetting("ip")+"失败，连接超时.",e);
            flag = false;
            e.printStackTrace();
        }catch (IOException e){
            flag = false;
            logger.info("登陆FTP服务器ip:"+getFTPSetting("ip")+"失败，FTP服务器无法打开！",e);
            e.printStackTrace();
        }
        return flag;
    }
    //close FTP connection
    public  static void closeConnection(){
        try {
            if (ftpClient!=null){
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }catch (IOException e){
            logger.info("FTP service close exception：",e);
            e.printStackTrace();
        }

    }


    /**
     * 列出服务器上所有的文件和目录
     * @return
     */
    public static void ListNamesAll(){
        try{
            String[] files = ftpClient.listNames();
            if (files ==null||files.length==0){
                System.out.println("没有任何文件");
            }else {
                for( int i = 0 ; i<files.length;i++){
                    System.out.println(files[i]);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    /**
     * 删除一个文件
     */
    public static boolean deleteFile(String filename) {
        boolean flag = true;
        try {
            flag = ftpClient.deleteFile(filename);
            if (flag) {
                System.out.println("删除文件成功！");
            } else {
                System.out.println("删除文件失败！");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    /**
     * 待定：有问题
     * 删除FTP目录(包括此目录中的所有文件)
     */
    public static void deleteDirectory(String pathname) {
        try {
            openConnectFTPService();
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.changeToParentDirectory();//上一层目录
            String[] files = ftpClient.listNames();
            for( int i = 0 ; i<files.length;i++){
                System.out.println(files[i]);
            }
            ftpClient.removeDirectory(pathname);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 删除空目录
     */
    public static void deleteEmptyDirectory(String pathname) {
        try {
            openConnectFTPService();
            ftpClient.removeDirectory(pathname);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 进入到服务器的某个目录下
     *
     * @param directory
     */
    public static void changeWorkingDirectory(String directory) {
        try {
            openConnectFTPService();
            ftpClient.changeWorkingDirectory(directory);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 返回到上一层目录
     */
    public static void changeToParentDirectory() {
        try {
            openConnectFTPService();
            ftpClient.changeToParentDirectory();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 重命名文件
     *
     * @param oldFileName --原文件名
     * @param newFileName --新文件名
     */
    public static void renameFile(String oldFileName, String newFileName) {
        try {
            openConnectFTPService();
            ftpClient.rename(oldFileName, newFileName);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * 在服务器上创建一个目录,当当前路径不存在依次创建子目录(文件夹)
     * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>...
     */
    public static boolean makeDirectory(String dir) {
        openConnectFTPService();
        boolean flag = true;
        try {
            File file = new File(dir);
            if (!file.isDirectory()){
                String[] str = dir.split("/");
                System.out.println(str.length);
                String dirPath = "";
                for (int i=0;i<str.length;i++) {
                    dirPath += "/"+str[i];
                    boolean directory = ftpClient.changeWorkingDirectory(dirPath);
                    if(!directory){//当directory为false,则创建FTP当前路径
                        flag = ftpClient.makeDirectory(dirPath);
                    }
                }
                if (flag) {
                    System.out.println("make Directory " + dir + " succeed");
                } else {
                    System.out.println("make Directory " + dir + " false");
                }
            }else{
                System.out.println(dir+"不是一个目录");
            }

        }catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码
     *
     * @param obj
     * @return ""
     */
    private static String iso8859togbk(Object obj) {
        try {
            if (obj == null)
                return "";
            else
                return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 设置传输文件的类型[文本文件或者二进制文件]
     * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE
     */
    public static void setFileType(int fileType) {
        try {
            ftpClient.setFileType(fileType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFileName             --服务器上的文件名
     * @param localFileName--本地文件名
     * @return true 下载成功，false 下载失败
     */
    public static boolean downloadFile(String remoteFileName, String localFileName) {
        boolean flag = true;
//        openConnectFTPService();
        // 下载文件
        BufferedOutputStream buffOut = null;
        try {
            flag = ftpClient.retrieveFile(remoteFileName, buffOut);
            buffOut = new BufferedOutputStream(new FileOutputStream(localFileName));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("本地文件下载失败！", e);
        } finally {
            try {
                if (buffOut != null)
                    buffOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 上传单个文件，并重命名
     * @param localFile--本地文件路径
     * @param remoteFileName--新的文件名,可以命名为空""
     * @param saveRemoteFolderPath--FTP服务器上传保存文件夹的路径
     * @return true 上传成功，false 上传失败
     */
    public static boolean uploadFile(File localFile,String remoteFileName,String saveRemoteFolderPath) {
        boolean flag = true;
        try {
            InputStream input = new FileInputStream(localFile);
            if (input == null) {
                System.out.println("本地文件"+localFile.getPath()+"不存在!");
            }
            openConnectFTPService();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            //进入FTP当前文件夹，当文件夹不存在则false
            boolean remoteFolder =ftpClient.changeWorkingDirectory(saveRemoteFolderPath);
            if (!remoteFolder){
                boolean IsCreatedFolder = makeDirectory(saveRemoteFolderPath);
                if(!IsCreatedFolder){
                    logger.info(saveRemoteFolderPath+"路径在FTP服务器上面不存在");
                    System.out.println("路径在FTP服务器上面不存在！");
                }
                ftpClient.changeWorkingDirectory(saveRemoteFolderPath);
            }
            flag = ftpClient.storeFile(remoteFileName, input);
            if (flag) {
                System.out.println("上传文件成功！");
            } else {
                System.out.println("上传文件失败！");
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("本地文件上传失败！", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 上传单个文件，并重命名
     * @param input--文件流
     * @param remoteFileName--新的文件名,可以命名为空""
     * @param saveRemoteFolderPath--FTP服务器上传保存文件夹的路径
     * @return true 上传成功，false 上传失败
     */
    public static boolean inputStreamUploadFile(InputStream input,String remoteFileName,String saveRemoteFolderPath) {
        boolean flag = true;
        try {
            if (input == null) {
                System.out.println("本地文件不存在!");
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            //进入FTP当前文件夹，当文件夹不存在则false
            boolean remoteFolder =ftpClient.changeWorkingDirectory(saveRemoteFolderPath);
            if(!remoteFolder){
                boolean IsCreatedFolder = makeDirectory(saveRemoteFolderPath);
                if(!IsCreatedFolder){
                    logger.info(saveRemoteFolderPath+"路径在FTP服务器上面不存在");
                    System.out.println("路径在FTP服务器上面不存在！");
                }
                ftpClient.changeWorkingDirectory(saveRemoteFolderPath);
            }
            flag = ftpClient.storeFile(remoteFileName, input);
            if (flag) {
                System.out.println("上传文件成功！");
            } else {
                System.out.println("上传文件失败！");
            }
            input.close();
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
            logger.debug("本地文件上传失败！", e);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}
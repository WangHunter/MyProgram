package com.SFTP;

/**
 * Created by Administrator on 2017/4/6.
 */

import com.jcraft.jsch.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;


public class SFTPAccesser {

    private static Logger log = Logger.getRootLogger();


    public static SFTPParameter connectPara() {

        SFTPParameter para = new SFTPParameter();
        para.hostName ="200.200.6.17";
        para.userName = "root";
        para.passWord = "*IK<0okm";
        para.port = 22;
        para.downloadPath = "/home/data/client/";
        para.savePath ="/home/data/sdk/";
        para.uploadPath = "/home/cfiusr/test";
        File file = new File("D:/appl/cmd.txt");
        String delFile = "/home/cfiusr/test/cmd.txt";
        return para;
    }


    /**
     * connect to SFTP Server
     * If can't connect to SFTP , it will re-try
     *
     * @param para
     * @param count connect retry count
     * @return isConnected
     * @throws InterruptedException
     * @throws JSchException
     */
    public static Boolean connectSFTP(SFTPParameter para, Integer count) throws InterruptedException, JSchException {
        System.out.println("开始连接远程SFTP");
        Boolean ret = false;
        if (para == null)
            return ret;
        if (count == 1) {
            int interval = para.checkInterval();
            Thread.sleep(interval);
        }
        if (count == 0)
            count = 1;
        if (para.isConnected)
            return true;
        if (StringUtils.isEmpty(para.hostName) || para.port == 0
                || para.userName == null || para.passWord == null) {
            log.error("SFTP connect parameter error !");
            para.release();
            return ret;
        }

        ChannelSftp csftp = null;
        Session session = null;
        Channel channel = null;
        JSch jsch = new JSch();
        //if key file is not null
        if (StringUtils.isNotBlank(para.sftpKeyFile)) {
            if (StringUtils.isBlank(para.passWord)) {
                jsch.addIdentity(para.sftpKeyFile);
            } else {
                jsch.addIdentity(para.sftpKeyFile, para.passWord);
            }
        }
        //skip key checking
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session = jsch.getSession(para.userName, para.hostName, para.port);
        session.setConfig(config);
        if (StringUtils.isBlank(para.sftpKeyFile)) {
            session.setPassword(para.passWord);
        }

        int i = 0;
        while (true) {
            i++;
            try {
                session.connect();
                channel = session.openChannel("sftp");
                channel.connect();
                csftp = (ChannelSftp) channel;
                ret = true;
                para.isConnected = true;
                para.sftp = csftp;
                log.info("Successfully connect to " + para.hostName + ":" + para.port + " with " + para.userName);
                break;
            } catch (Exception ex) {
                log.error(ex);
                para.isConnected = false;
                if (i >= count) {
                    log.info(para.toString());
                    log.error(
                            "Can't connect SFTP Server, have already tried" + i
                                    + "times!");
                    try {
                        int interval = para.checkInterval();
                        Thread.sleep(interval);
                    } catch (Exception e) {
                        log.error(ex);
                    }
                    break;
                } else {
                    try {
                        int interval = para.checkInterval();
                        log.error(
                                "Can't connect SFTP Server，waiting for "
                                        + interval + "seconds try again!");
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        log.error(e);
                        break;
                    }
                }

            }

        }// end of while
        return ret;
    }

    /**
     * get SFTP Server file list .
     *
     * @param para
     * @return List<ChannelSftp.LsEntry>
     * @throws SftpException
     * @throws JSchException
     * @throws InterruptedException
     */
    public static List<ChannelSftp.LsEntry> getFiles(SFTPParameter para) throws InterruptedException, JSchException, SftpException {
        List<ChannelSftp.LsEntry> files = new ArrayList<ChannelSftp.LsEntry>();
        if (para != null) {
            if (connectSFTP(para, para.conFTPTryCount)) {
                ChannelSftp cs = para.sftp;
                Vector<ChannelSftp.LsEntry> fs = null;
                fs = cs.ls(para.downloadPath);

                for (ChannelSftp.LsEntry entry : fs) {
                    if (!entry.getAttrs().isDir()) {
                        files.add(entry);
                    }
                }
            }

        }
        return files;
    }

    /**
     * download files from SFTP
     *
     * @param para
     * @return
     * @throws InterruptedException
     * @throws JSchException
     * @throws SftpException
     * @throws IOException
     */
    public static Boolean downloadFiles(SFTPParameter para) throws InterruptedException, JSchException, SftpException {
        System.out.println("开始下载文件");
        Boolean ret = true;
        List<ChannelSftp.LsEntry> files = getFiles(para);
        if (!para.isConnected) connectSFTP(para, para.conFTPTryCount);
        ChannelSftp cs = para.sftp;
        for (ChannelSftp.LsEntry file : files) {
            if (!file.getFilename().endsWith(".old")) {
                if (!para.downloadPath.endsWith("/")) para.downloadPath = para.downloadPath + "/";
                if (!para.savePath.endsWith("/")) para.savePath = para.savePath + "/";
                String fromFile = para.downloadPath + file.getFilename();
                String toFile = para.savePath + file.getFilename();
                cs.get(fromFile,toFile);
                cs.rename(fromFile, fromFile + ".old");   //重命名
            }
        }
        cs.disconnect();
        return ret;
    }

    /**
     * upload file to SFTP
     *
     * @param para
     * @param file
     * @return
     * @throws InterruptedException
     * @throws JSchException
     * @throws SftpException
     */
    public static Boolean uploadFile(SFTPParameter para, File file) throws InterruptedException, JSchException, SftpException {
        Boolean ret = false;
        if (!file.exists()) return ret;
        if (connectSFTP(para, para.conFTPTryCount)) {
            ChannelSftp cs = para.sftp;
            if (!para.uploadPath.endsWith("/")) para.uploadPath = para.uploadPath + "/";
            cs.put(file.getAbsolutePath(), para.uploadPath + file.getName());
            ret = true;
        }
        return ret;
    }

    /**
     * upload files to SFTP server .
     *
     * @param para
     * @param files
     * @return
     * @throws SftpException
     * @throws JSchException
     * @throws InterruptedException
     */
    public static Boolean uploadFiles(SFTPParameter para, List<File> files) throws InterruptedException, JSchException, SftpException {
        Boolean ret = true;
        if (files == null || files.size() <= 0) return ret;
        int i = 0;
        for (File file : files) {
            if (!uploadFile(para, file)) i++;
        }
        if (i > 0) {
            log.error(i + " items were uploaded failed!");
            ret = false;
        }
        return ret;
    }


    /**
     * delete FTP server file .
     *
     * @param para
     * @param filepath file path must be /home/user/text.xxx
     * @return true or false
     * @throws SftpException
     * @throws JSchException
     * @throws InterruptedException
     */
    public static Boolean deleteFile(SFTPParameter para, String filepath) throws InterruptedException, JSchException, SftpException {
        Boolean ret = false;
        if (connectSFTP(para, para.conFTPTryCount)) {
            para.sftp.rm(filepath);
            ret = true;
        }
        return ret;
    }


    /**
     * delete SFTP  files .
     *
     * @param para
     * @return true or false
     * @throws SftpException
     * @throws JSchException
     * @throws InterruptedException
     */
    public static Boolean deleteFiles(SFTPParameter para, List<String> list) throws InterruptedException, JSchException, SftpException {
        Boolean ret = true;
        if (list == null || list.size() <= 0) ret = false;
        int i = 0;
        for (String s : list) {

            if (!deleteFile(para, s)) i++;
        }
        if (i > 0) {
            log.equals(i + " items were deleted failed!");
            ret = false;
        }
        return ret;
    }

    /**
     * is file exist in SFTP
     *
     * @param para
     * @param path
     * @param file
     * @return
     * @throws InterruptedException
     * @throws JSchException
     * @throws SftpException
     */
    public static boolean isFileExist(SFTPParameter para, String path, String file) throws InterruptedException, JSchException, SftpException {
        if (connectSFTP(para, para.conFTPTryCount)) {
            Vector<ChannelSftp.LsEntry> files = null;
            ChannelSftp cs = para.sftp;
            files = cs.ls(path);
            for (ChannelSftp.LsEntry entry : files) {
                if (!entry.getAttrs().isDir()
                        && entry.getFilename().equals(file)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * is file exist in SFTP
     *
     * @param para
     * @param path
     * @param dirname
     * @return
     * @throws InterruptedException
     * @throws JSchException
     * @throws SftpException
     */
    public static boolean isDirExist(SFTPParameter para, String path, String dirname) throws InterruptedException, JSchException, SftpException {
        if (connectSFTP(para, para.conFTPTryCount)) {
            Vector<ChannelSftp.LsEntry> files = null;
            ChannelSftp cs = para.sftp;
            files = cs.ls(path);
            for (ChannelSftp.LsEntry entry : files) {
                if (entry.getAttrs().isDir()
                        && entry.getFilename().equals(dirname)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * make dir
     *
     * @param para
     * @param makeDir like /home/ccc/bbb
     * @throws InterruptedException
     * @throws JSchException
     * @throws SftpException
     */
    public static void makeDir(SFTPParameter para, String makeDir) throws InterruptedException, JSchException, SftpException {
        if ("".equals(makeDir) || makeDir == null) return;
        connectSFTP(para, para.conFTPTryCount);
        ChannelSftp cs = para.sftp;
        String[] makedirs = makeDir.split("\\/");
        String ls = "/";
        for (String dir : makedirs) {
            // if dir is not null and empty
            if (!"".equals(dir) && !isDirExist(para, ls, dir)) {
                ls += dir + "/";
                cs.mkdir(dir);
                cs.cd(dir);
            } else if (!"".equals(dir)) {
                ls += dir + "/";
                cs.cd(ls);
            }

        }
    }

    public static void main(String[] args) {

        SFTPParameter client = SFTPAccesser.connectPara();

      /*  SFTPParameter para = new SFTPParameter() ;
        para.hostName ="200.200.6.17" ;
        para.userName = "root" ;
        para.passWord = "*IK<0okm" ;
        para.port = 22;
        para.downloadPath = "/home/data/client/" ;
        para.savePath ="/home/test/";
        para.uploadPath ="/home/cfiusr/test";
        File file = new File("D:/appl/cmd.txt");
        String delFile = "/home/cfiusr/test/cmd.txt";*/


        try {
//            String makeDir = "/home/cfiusr/D/E/F/G/H/J/kk";
//            makeDir(para,makeDir) ;
            downloadFiles(client);
//            uploadFile(para, file);
            //deleteFile(para, delFile);
            client.release();
            System.exit(1);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


}

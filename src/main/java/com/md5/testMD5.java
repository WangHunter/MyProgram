package com.md5;

/**
 * Created by Administrator on 2017/11/10.
 */

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * 小文件校验
 */
public class testMD5 {

    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void main(String[] args) throws IOException {

        String path = "D:\\Desktop\\recommandData-1.0-SNAPSHOT-bin.tar.gz";

        String v = getMd5ByFile(new File(path));
        System.out.println("MD5:" + v.toUpperCase());

        FileInputStream fis = new FileInputStream(path);
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        IOUtils.closeQuietly(fis);
        System.out.println("MD5:" + md5);

        //System.out.println("MD5:"+DigestUtils.md5Hex("WANGQIUYUN"));
    }

}


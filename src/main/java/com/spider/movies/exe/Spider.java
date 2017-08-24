package com.spider.movies.exe;

/**
 * Created by Administrator on 2017/5/5.
 */
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class Spider {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 相当于打开浏览器
        HttpClient httpClient = new HttpClient();
        // 相当于在浏览器中输入网址
        GetMethod getMethod = new GetMethod("http://www.baidu.com");
        try {
            // 返回HTTP状态码，在后面用到。
            int statusCode = httpClient.executeMethod(getMethod);
            // 此处输出的是html语言
            System.out.println("response=" + getMethod.getResponseBodyAsString());
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 关闭网络连接，防止造成资源浪费
            getMethod.releaseConnection();
        }
    }

}

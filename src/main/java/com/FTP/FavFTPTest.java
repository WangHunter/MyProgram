package com.FTP;

import junit.framework.TestCase;


public class FavFTPTest extends TestCase {

    public void testFavFTPUtil(){
        String hostname = "127.0.0.1";
        int port = 21;
        String username = "business";
        String password = "business";
        String pathname = "business/ebook";
        String filename = "big.rar";
        String originfilename = "C:\\Users\\Downloads\\Downloads.rar";
        FavFTPUtil.uploadFileFromProduction(hostname, port, username, password, pathname, filename, originfilename);
//   String localpath = "D:/";

//   FavFTPUtil.downloadFile(hostname, port, username, password, pathname, filename, localpath);
    }

}

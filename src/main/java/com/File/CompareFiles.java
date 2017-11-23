package com.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */
public class CompareFiles {

    public static List file2List(String path) throws IOException {
        List list = new ArrayList();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        String str = null;
        while ((str = br.readLine()) != null) {
            list.add(str);
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        List list2 = file2List("D:\\Desktop\\mongodb.txt");
        List list1 = file2List("D:\\Desktop\\redis.txt");
        list1.removeAll(list2);

        Iterator<String> it = list1.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}

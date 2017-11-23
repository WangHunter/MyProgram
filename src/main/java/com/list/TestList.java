package com.list;

/**
 * Created by Administrator on 2017/3/20.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 测试集合的交并差
 */
public class TestList {

    public static void main(String[] args) {
        List list1 = new ArrayList();
//        list1.add("1111");
//        list1.add("2222");
//        list1.add("3333");
//        list1.add("4444");

        List list2 = new ArrayList();
//        list2.add("3333");
//        list2.add("4444");
//        list2.add("5555");
        list2.add("00");
        list2.add("01");

        for (int i = 00; i < 24; i++) {
            if (String.valueOf(i).length() == 1) {
                list1.add("0" + String.valueOf(i));
            } else {
                list1.add(String.valueOf(i));
            }
        }

        //并集
//        list1.addAll(list2);
        //交集
//        list1.retainAll(list2);
        //差集
        list1.removeAll(list2);
        //无重复并集
//        list2.removeAll(list1);
//        list1.addAll(list2);

        Iterator<String> it = list1.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

        }

        //System.out.println("-----------------------------------\n");
        //printStr(list1);

    }

    public static void printStr(List list1) {
        for (int i = 0; i < list1.size(); i++) {
//            System.out.println(list1.get(i));
        }
    }


}

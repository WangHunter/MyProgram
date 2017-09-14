package com.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/19.
 */
public class testDay {

    @Test
    public void getPass5Day() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(new Date());//设置当前日期
        calendar.add(Calendar.DAY_OF_YEAR, -5);//月份减一
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }

    @Test
    public void testDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String str1 = "2017123123";
        Date str = sdf.parse(str1);
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(str);//设置当前日期
        calendar.add(Calendar.HOUR_OF_DAY, 1);//小时加一
        String t = sdf.format(calendar.getTime());
        System.out.println(t);
    }

    /**
     * 得到当前时间
     */
    @Test
    public void currentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(new Date());//设置当前日期
        System.out.println(sdf.format(calendar.getTime()));
    }

    @Test
    public void test() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        cl.setTime(sdf.parse("2017-01-01"));
        int week = cl.get(Calendar.WEEK_OF_YEAR);
        System.out.println(week);
    }


    public  String getWeekOfYear(String strDate) {
        String retWeek = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = sdf.parse(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            System.out.println(cal.getTime());
            retWeek = strDate.substring(0, 4) + (String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)).length() < 2
                    ? "0" + String.valueOf(cal.get(Calendar.WEEK_OF_YEAR))
                    : String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retWeek;
    }


    /**
     * 计算日期的相隔天数
     * @throws ParseException
     */
    @Test
    public void getTimelag() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(new Date());//设置当前日期
        Date beginDate = simpleDateFormat.parse("20170701");
        Date endDate = simpleDateFormat.parse("20170702.00");
        long days = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        System.out.println("相隔天数为："+days);
    }

    /**
     * double 转int
     */
    @Test
    public void double2Integer(){
        Double old = 2.0170908E7;
        int now = old.intValue();
        System.out.println(now);
    }

    public static void main(String[] args) {
        testDay testDay = new testDay();
        System.out.println(testDay.getWeekOfYear("20161228"));
    }
}

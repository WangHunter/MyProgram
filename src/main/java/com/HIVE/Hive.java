package com.HIVE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */
@Component("hive")
public class Hive {

    @Autowired
    private JdbcTemplate hiveJdbcTemplate;

    public void run(){
        String sql  = "select count(*) cnt from lx_app";
        List<Map<String, Object>> list = hiveJdbcTemplate.queryForList(sql);
        System.out.println(list.get(0).get("cnt"));
    }
}

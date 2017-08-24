package com.json.msc;

import java.io.Serializable;

/**
 * Created by chenhan on 2017/4/14.
 */
public class OsNode implements Serializable {

    private String os_sys;

    public String getOs_sys() {
        return os_sys;
    }

    public void setOs_sys(String os_sys) {
        this.os_sys = os_sys;
    }

    @Override
    public String toString() {
        return "OsNode{" +
                "os_sys='" + os_sys + '\'' +
                '}';
    }
}

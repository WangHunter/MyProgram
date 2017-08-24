package com.json.msc;

import java.io.Serializable;

/**
 * Created by chenhan on 2017/4/14.
 */
public class NetNode implements Serializable {

    private String n_lau;

    private String n_lrs;

    private String n_sok;

    private String n_ssb;

    public String getN_lau() {
        return n_lau;
    }

    public void setN_lau(String n_lau) {
        this.n_lau = n_lau;
    }

    public String getN_lrs() {
        return n_lrs;
    }

    public void setN_lrs(String n_lrs) {
        this.n_lrs = n_lrs;
    }

    public String getN_sok() {
        return n_sok;
    }

    public void setN_sok(String n_sok) {
        this.n_sok = n_sok;
    }

    public String getN_ssb() {
        return n_ssb;
    }

    public void setN_ssb(String n_ssb) {
        this.n_ssb = n_ssb;
    }

    @Override
    public String toString() {
        return "NetNode{" +
                "n_lau='" + n_lau + '\'' +
                ", n_lrs='" + n_lrs + '\'' +
                ", n_sok='" + n_sok + '\'' +
                ", n_ssb='" + n_ssb + '\'' +
                '}';
    }
}

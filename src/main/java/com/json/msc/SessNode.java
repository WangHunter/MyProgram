package com.json.msc;

import java.io.Serializable;

/**
 * Created by chenhan on 2017/4/14.
 */
public class SessNode implements Serializable {

    private String cver;

    private String uid;

    private String appid;

    private String sub;

    private String ntt;

    private String ntt_sub;

    private String sip;

    private String stime;

    private String csid;

    private String sid;

    private String des;

    private String ret;

    private String efun;

    private String etime;

    public String getCver() {
        return cver;
    }

    public void setCver(String cver) {
        this.cver = cver;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getNtt() {
        return ntt;
    }

    public void setNtt(String ntt) {
        this.ntt = ntt;
    }

    public String getNtt_sub() {
        return ntt_sub;
    }

    public void setNtt_sub(String ntt_sub) {
        this.ntt_sub = ntt_sub;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getCsid() {
        return csid;
    }

    public void setCsid(String csid) {
        this.csid = csid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getEfun() {
        return efun;
    }

    public void setEfun(String efun) {
        this.efun = efun;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    @Override
    public String toString() {
        return "SessNode{" +
                "cver='" + cver + '\'' +
                ", uid='" + uid + '\'' +
                ", appid='" + appid + '\'' +
                ", sub='" + sub + '\'' +
                ", ntt='" + ntt + '\'' +
                ", ntt_sub='" + ntt_sub + '\'' +
                ", sip='" + sip + '\'' +
                ", stime='" + stime + '\'' +
                ", csid='" + csid + '\'' +
                ", sid='" + sid + '\'' +
                ", des='" + des + '\'' +
                ", ret='" + ret + '\'' +
                ", efun='" + efun + '\'' +
                ", etime='" + etime + '\'' +
                '}';
    }
}

package com.json.msc;


import java.io.Serializable;


/**
 * Created by ChenHan on 2017/4/14.
 */
public class ToPlatformMscCollDay implements Serializable {

    private String tmIntrvlCd;

    private String sessionId;

    private String usrId;

    private String appId;

    private String busiType;

    private String errorCode;

    private String sessionBeginTime;

    private String sessionEndTime;

    private String loadDate;

    public String getTmIntrvlCd() {
        return tmIntrvlCd;
    }

    public void setTmIntrvlCd(String tmIntrvlCd) {
        this.tmIntrvlCd = tmIntrvlCd;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSessionBeginTime() {
        return sessionBeginTime;
    }

    public void setSessionBeginTime(String sessionBeginTime) {
        this.sessionBeginTime = sessionBeginTime;
    }

    public String getSessionEndTime() {
        return sessionEndTime;
    }

    public void setSessionEndTime(String sessionEndTime) {
        this.sessionEndTime = sessionEndTime;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    @Override
    public String toString() {
        return "ToPlatformMscCollDay{" +
                "tmIntrvlCd=" + tmIntrvlCd +
                ", sessionId='" + sessionId + '\'' +
                ", usrId='" + usrId + '\'' +
                ", appId='" + appId + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", sessionBeginTime=" + sessionBeginTime +
                ", sessionEndTime=" + sessionEndTime +
                ", loadDate=" + loadDate +
                '}';
    }
}

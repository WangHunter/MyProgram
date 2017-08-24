package com.json.msc;

import java.io.Serializable;

/**
 * Created by chenhan on 2017/4/14.
 */
public class MSCLog implements Serializable {

    private SessNode sess;

    private NetNode net;

    private OsNode os;

    public SessNode getSess() {
        return sess;
    }

    public void setSess(SessNode sess) {
        this.sess = sess;
    }

    public NetNode getNet() {
        return net;
    }

    public void setNet(NetNode net) {
        this.net = net;
    }

    public OsNode getOs() {
        return os;
    }

    public void setOs(OsNode os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return "MSCLog{" +
                "sess=" + sess +
                ", net=" + net +
                ", os=" + os +
                '}';
    }
}

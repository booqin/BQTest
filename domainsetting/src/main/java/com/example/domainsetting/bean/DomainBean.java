package com.example.domainsetting.bean;

/**
 * TODO
 * Created by Boqin on 2017/2/16.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainBean {

    private String mIp;
    private String mPort;
    private boolean mChecked;

    public String getIp() {
        return mIp;
    }

    public void setIp(String ip) {
        mIp = ip;
    }

    public String getPort() {
        return mPort;
    }

    public void setPort(String port) {
        mPort = port;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public String getDomian(){
        return mIp+":"+mPort;
    }
}

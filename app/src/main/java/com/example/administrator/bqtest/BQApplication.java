package com.example.administrator.bqtest;

import android.app.Application;
import android.content.Context;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.example.domainsetting.DomainSetting;

/**
 * TODO
 * Created by Boqin on 2017/2/8.
 * Modified by Boqin
 *
 * @Version
 */
public class BQApplication extends Application{


    public static String DOMAIN = "192.168.1.172:8080";

    private static boolean IS_DEBUG = true;

    /** 应用上下文 */
    private static Context AppContext;

    private static BQApplication MissApp;


    /** 官方默认的key */
    final String APP_KEY = "23015524";

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = this;
        MissApp = this;

//        Fresco.initialize(this, FrescoConfig.getConfig(this));

        //初始化域名配置模块
        DOMAIN = DomainSetting.initialize(this, DOMAIN, IS_DEBUG);

        initOpenIM();
    }

    private void initOpenIM() {
        //必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if(SysUtil.isTCMSServiceProcess(this)){
            return;
        }
        //第一个参数是Application Context
        //这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if(SysUtil.isMainProcess()){
            YWAPI.init(this, APP_KEY);
        }
    }

}

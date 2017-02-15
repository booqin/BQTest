package com.example.administrator.bqtest;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;
import android.content.Context;

/**
 * TODO
 * Created by Boqin on 2017/2/8.
 * Modified by Boqin
 *
 * @Version
 */
public class BQApplication extends Application{

    /** 官方默认的key */
    final String APP_KEY = "23015524";
    /** 产品版本 */
    public static String APP_VERSION;
    /** 设备ID */
    public static String DEVICE_ID;
    /** 通道 */
    public static String CHANNEL;

    /** 应用上下文 */
    private static Context AppContext;

    private static BQApplication MissApp;

    /**
     * 获取应用Context
     *
     * @description: Created by lenghuo on 2016/11/3 20:23
     */
    public static Context getAppContext() {
        return AppContext;
    }

    /**
     * 获取App实例
     *
     * @description: Created by lenghuo on 2016/11/5 10:49
     */
    public static BQApplication getAppInstance() {
        return MissApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = this;
        MissApp = this;

        Fresco.initialize(this, FrescoConfig.getConfig(this));

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

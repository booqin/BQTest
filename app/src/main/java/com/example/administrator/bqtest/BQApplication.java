package com.example.administrator.bqtest;

import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * TODO
 * Created by Boqin on 2017/2/8.
 * Modified by Boqin
 *
 * @Version
 */
public class BQApplication extends Application{
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
    }


}

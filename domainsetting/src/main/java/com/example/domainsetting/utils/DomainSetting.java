package com.example.domainsetting.utils;

import com.example.domainsetting.DomainSettingActivity;

import android.content.Context;

/**
 * 域名设置类，需要初始化
 * Created by Boqin on 2017/2/16.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainSetting {

    private static Context mAppContent;
    private static boolean IS_DEBUG;

    /**
     * 初始化工作
     * @param context 上下文
     * @param defaultDomain 默认域名
     * @param isDebug 是否调试模式
     */
    public static String initialize(Context context, String defaultDomain, boolean isDebug){
        return initialize(context, defaultDomain, isDebug, null);
    }

    /**
     * 初始化工作
     * @param context 上下文
     * @param defaultDomain 默认域名
     * @param isDebug 是否调试模式
     */
    public static String initialize(Context context, String defaultDomain, boolean isDebug, DomainSettingActivity.CheckedListener checkedListener){
        if(isDebug){
            mAppContent = context;
            IS_DEBUG = isDebug;
            if (!ORMUtil.isExistCheckedDomain()) {
                ORMUtil.insertDomain(defaultDomain);
                ORMUtil.updateDomainChecked(defaultDomain);
            }
            if (checkedListener!=null) {
                DomainSettingActivity.setDomainCheckedListener(checkedListener);
            }
            return ORMUtil.getCheckedDomain();
        }else {
            return defaultDomain;
        }
    }

    public static Context getAppContent(){
        return mAppContent;
    }

    public static boolean isDebug(){
        return IS_DEBUG;
    }

}

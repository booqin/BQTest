package com.example.domainsetting.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.example.domainsetting.DomainSetting;
import com.example.domainsetting.bean.DomainBean;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.ArraySet;

/**
 * 数据持久化工具类
 * Created by Boqin on 2017/2/17.
 * Modified by Boqin
 *
 * @Version
 */
public class ORMUtil {

    /** 域名列表SP名 */
    private static final String SP_NAME = "DM";
    /** 选中域名的SP名 */
    private static final String SP_NAME_CHECKED = "DM_CHECKED";
    /** SP中的key */
    private static final String SP_NAME_KEY = "DM_KEY";

    /**
     * 新增域名
     */
    public static void insertDomain(String domian){
        SharedPreferences sharedPreferences = DomainSetting.getAppContent().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> stringSet = sharedPreferences.getStringSet(SP_NAME_KEY, new ArraySet<String>());
        stringSet.add(domian);
        editor.remove(SP_NAME_KEY);
        editor.putStringSet(SP_NAME_KEY, stringSet);
        editor.commit();
    }
    /**
     * 删除域名
     */
    public static void deleteDomain(String domian){
        SharedPreferences sharedPreferences = DomainSetting.getAppContent().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> stringSet = sharedPreferences.getStringSet(SP_NAME_KEY, new ArraySet<String>());
        stringSet.remove(domian);
        editor.putStringSet(SP_NAME_KEY, stringSet);
        editor.commit();
    }

    /**
     * 更新域名
     */
    public static void updateDomain(){

    }

    /**
     * 更新选中项
     */
    public static void updateDomainChecked(String domian){
        SharedPreferences sharedPreferences = DomainSetting.getAppContent().getSharedPreferences(SP_NAME_CHECKED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SP_NAME_KEY, domian);
        editor.commit();
    }

//    /**
//     * 更新选中项
//     */
//    public Set<String> getDomainSet(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SP_NAME_CHECKED, Context.MODE_PRIVATE);
//        Set<String> stringSet = sharedPreferences.getStringSet(SP_NAME_KEY, new ArraySet<String>());
//        return stringSet;
//    }

    /**
     *
     */
    public static List<DomainBean> getDomainSet(){
        List<DomainBean> list = new ArrayList<>();
        SharedPreferences sharedPreferences = DomainSetting.getAppContent().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        Set<String> stringSet = sharedPreferences.getStringSet(SP_NAME_KEY, new ArraySet<String>());
        String checkedDomain = getCheckedDomain();
        Iterator it = stringSet.iterator();
        while (it.hasNext()){
            DomainBean domainBean = new DomainBean();
            String domain = (String) it.next();
            domainBean.setDomain(domain);
            if (domain.equals(checkedDomain)) {
                domainBean.setChecked(true);
            }
            list.add(domainBean);
        }
        return list;
    }

    /**
     * 更新选中项
     */
    public static String getCheckedDomain(){
        SharedPreferences sharedPreferences = DomainSetting.getAppContent().getSharedPreferences(SP_NAME_CHECKED, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SP_NAME_KEY, null);
    }

    /**
     * 是否存在选中域名
     */
    public static boolean isExistCheckedDomain(){
        SharedPreferences sharedPreferences = DomainSetting.getAppContent().getSharedPreferences(SP_NAME_CHECKED, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SP_NAME_KEY, null)==null;
    }


}

package com.example.openimtest.customim;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;
import com.example.openimtest.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 自定义会话
 * Created by Boqin on 2017/3/6.
 * Modified by Boqin
 *
 * @Version
 */
public class ConversationListUICustom extends IMConversationListUI {

    public ConversationListUICustom(Pointcut pointcut) {
        super(pointcut);
    }

    /** 
     * 自定义标题栏
     */
    @Override
    public View getCustomConversationListTitle(Fragment fragment, Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_title_view, null);
//        return super.getCustomConversationListTitle(fragment, context, inflater);
        return view;
    }

    /** 
     * 是否隐藏标题栏
     */
    @Override
    public boolean needHideTitleView(Fragment fragment) {
        //是否隐藏标题
        return super.needHideTitleView(fragment);
    }
}

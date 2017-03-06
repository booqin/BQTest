package com.example.openimtest.customim;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWMessage;
import com.example.openimtest.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义聊天页面
 * Created by Boqin on 2017/3/6.
 * Modified by Boqin
 *
 * @Version
 */
public class ChattingPageUICustom extends IMChattingPageUI {

    public ChattingPageUICustom(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 自定义标题栏
     */
    @Override
    public View getCustomTitleView(Fragment fragment, Context context, LayoutInflater inflater, YWConversation conversation) {
        View view = inflater.inflate(R.layout.custom_title_view, null);
        ((TextView) view.findViewById(R.id.title)).setText("聊天页面");
        //        return super.getCustomConversationListTitle(fragment, context, inflater);
        return view;
    }

    /**
     * 设置消息气泡背景图，需要.9图
     * @param conversation 当前消息所在会话
     * @param message      需要设置背景的消息
     * @param self         是否是自己发送的消息，true：自己发送的消息， false：别人发送的消息
     * @return  0: 默认背景 －1:透明背景（无背景） >0:使用用户设置的背景图
     */
    @Override
    public int getMsgBackgroundResId(YWConversation conversation, YWMessage message, boolean self) {
//        if (true)
//            return super.getMsgBackgroundResId(conversation, message, self);
        int msgType = message.getSubType();
        //对应文本框的背景
        if (msgType == YWMessage.SUB_MSG_TYPE.IM_TEXT || msgType == YWMessage.SUB_MSG_TYPE.IM_AUDIO){
            if (self){
                return R.drawable.demo_talk_pop_r_bg;
            } else {
                return R.drawable.demo_talk_pop_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_IMAGE){ //对应图片框的背景
            if (self){
                return -1;
            } else {
                return -1;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_VIDEO){ //视频框的背景
            if (self){
                return -1;
            } else {
                return -1;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_GEO){   //地图框的背景
            if (self){
                return R.drawable.aliwx_comment_r_bg;
            } else {
                return R.drawable.aliwx_comment_l_bg;
            }
        } else if (msgType == YWMessage.SUB_MSG_TYPE.IM_P2P_CUS || msgType == YWMessage.SUB_MSG_TYPE.IM_TRIBE_CUS){
            if (self){
                return -1;
            } else {
                return -1;
            }
        }
        return super.getMsgBackgroundResId(conversation, message, self);
    }
}

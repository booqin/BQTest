package com.example.openimtest;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContactHeadClickCallback;
import com.alibaba.mobileim.contact.IYWContactService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *
 * Created by Boqin on 2017/2/14.
 * Modified by Boqin
 *
 * @Version
 */
public class OpenIMTestActivity extends AppCompatActivity {

    private final String APP_KEY = "23634322";

    private final String userid = "bqin";
    private final String password = "123456";

    private final String target = "testpro17"; //消息接收者ID
    private final String appkey = "23015524"; //消息接收者appKey

    private YWIMKit mIMKit;
    private Button mLoginBt;
    private Button mSessionBt;
    private Button mChatBt;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //此对象获取到后，保存为全局对象，供APP使用
        //此对象跟用户相关，如果切换了用户，需要重新获取
        mIMKit = YWAPI.getIMKitInstance(userid, APP_KEY);

        setContentView(R.layout.oim_activity_main);

        mLoginBt = (Button) findViewById(R.id.login);
        mSessionBt = (Button) findViewById(R.id.session);
        mChatBt = (Button) findViewById(R.id.chat);

        mLoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(mIMKit);
            }
        });

        mSessionBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mIMKit.getConversationActivityIntent();
                startActivity(intent);
            }
        });

        mChatBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mIMKit.getChattingActivityIntent(target, appkey);
                startActivity(intent);
            }
        });

        initKit();
    }

    private void initKit() {
        final IYWContactService contactService = mIMKit.getContactService();
        //头像点击的回调（开发者可以按需设置）
        contactService.setContactHeadClickCallback(new IYWContactHeadClickCallback() {
            @Override
            public Intent onShowProfileActivity(String userId, String appKey) {
                //需要开发者返回一个Intent
                Toast.makeText(OpenIMTestActivity.this, "头像被点击了", Toast.LENGTH_SHORT).show();
                return null;
            }

            @Override
            public Intent onDisposeProfileHeadClick(Context context, String s, String s1) {
                return null;
            }
        });
    }

    private void doLogin(YWIMKit mIMKit) {
        //开始登录
        IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                Toast.makeText(OpenIMTestActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                Toast.makeText(OpenIMTestActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doRegister(){
        mIMKit.getTribeService();
    }

}
package com.example.domainsetting;

import com.example.domainsetting.utils.ORMUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 域名添加Activity
 * Created by Boqin on 2017/2/17.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainAddActivity extends AppCompatActivity{

    private EditText mIPEditText;
    private EditText mPortEditText;
    private Button mButton;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_domain);
        mIPEditText = (EditText) findViewById(R.id.et_ip);
        mPortEditText = (EditText) findViewById(R.id.et_port);
        mButton = (Button) findViewById(R.id.bt_save);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = mIPEditText.getText().toString();
                String port = mPortEditText.getText().toString();
                if(ip.trim().isEmpty()||port.trim().isEmpty()){
                    Toast.makeText(DomainAddActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    ORMUtil.insertDomain(ip+":"+port);
                    onBackPressed();
                }
            }
        });

    }
}

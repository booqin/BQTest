package com.example.administrator.bqtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.domainsetting.DomainSettingActivity;
import com.example.openimtest.OpenIMTestActivity;

public class MainActivity extends AppCompatActivity {

    public static final String DOMIAN_PREFIX = "当前地址：";
    private TextView mDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.fresco_bt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), BQMainActivity.class);
//                startActivity(intent);
//            }
//        });

        findViewById(R.id.status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StatusActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.oim_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OpenIMTestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.dm_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DomainSettingActivity.startActivity(v.getContext(), new DomainSettingActivity.CheckedListener() {
                    @Override
                    public void onChecked(String domain) {
                        Toast.makeText(MainActivity.this, domain, Toast.LENGTH_SHORT).show();
                        BQApplication.DOMAIN = domain;
                    }
                });
            }
        });

        mDomain = (TextView) findViewById(R.id.domain_tv);
        mDomain.setText(DOMIAN_PREFIX +BQApplication.DOMAIN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDomain!=null) {
            mDomain.setText(DOMIAN_PREFIX +BQApplication.DOMAIN);
        }
    }
}

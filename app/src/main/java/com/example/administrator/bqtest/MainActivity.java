package com.example.administrator.bqtest;

import org.w3c.dom.Text;

import com.example.domainsetting.DomainSettingActivity;
import com.example.frescotest.BQMainActivity;
import com.example.openimtest.OpenIMTestActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fresco_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BQMainActivity.class);
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
        mDomain.setText(BQApplication.DOMAIN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDomain!=null) {
            mDomain.setText(BQApplication.DOMAIN);
        }
    }
}

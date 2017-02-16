package com.example.administrator.bqtest;

import com.example.domainsetting.DomainSettingActivity;
import com.example.frescotest.BQMainActivity;
import com.example.openimtest.OpenIMTestActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


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
                Intent intent = new Intent(v.getContext(), DomainSettingActivity.class);
                startActivity(intent);
            }
        });
    }


}

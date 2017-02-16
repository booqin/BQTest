package com.example.domainsetting;

import java.util.ArrayList;
import java.util.List;

import com.example.domainsetting.adapter.DomainAdapter;
import com.example.domainsetting.bean.DomainBean;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * 域名设置界面
 * Created by Boqin on 2017/2/16.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainSettingActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private DomainAdapter mAdapter;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //代码设置主题，无标题栏，使用toolbar替代
        setTheme(R.style.NoActionAppTheme);

        setContentView(R.layout.dsomainsetting_activity_main);

        //设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("域名设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DomainAdapter();
        mRecyclerView.setAdapter(mAdapter);
        getDataset();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataset() {
        List<DomainBean> domainBeanList = new ArrayList<>();
        for (int i = 0; i<10; i++){
            DomainBean domainBean = new DomainBean();
            domainBean.setIp(""+100*Math.random()+i);
            domainBean.setPort("8808");
            domainBean.setChecked(i==7);
            domainBeanList.add(domainBean);
        }
        mAdapter.updateDataSet(domainBeanList);
    }
}

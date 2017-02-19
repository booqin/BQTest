package com.example.domainsetting;

import java.util.List;

import com.example.domainsetting.adapter.DomainAdapter;
import com.example.domainsetting.bean.DomainBean;
import com.example.domainsetting.utils.ORMUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 域名设置界面
 * Created by Boqin on 2017/2/16.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainSettingActivity extends AppCompatActivity{

    private static CheckedListener CHECKED_LISTENER;

    private final int REQUEST_CODE = 1;
    private RecyclerView mRecyclerView;
    private DomainAdapter mAdapter;

    /**
     * 跳到本页面
     *
     */
    public static void startActivity(Context context, CheckedListener checkedListener) {
        Intent intent = new Intent(context, DomainSettingActivity.class);
        context.startActivity(intent);
        if (checkedListener!=null) {
            setDomainCheckedListener(checkedListener);
        }
    }
    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //非DEBUG模式下直接finish
        if(!DomainSetting.isDebug()){
            onBackPressed();
            return;
        }
        //代码设置主题，无标题栏，使用toolbar替代
        setTheme(R.style.NoActionAppTheme);

        setContentView(R.layout.dsomainsetting_activity_main);

        //设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("域名设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {//回退按钮
            onBackPressed();
        } else if (id == R.id.action_add) {//新增按钮
            Intent intent = new Intent(this, DomainAddActivity.class);
            startActivityForResult(intent, REQUEST_CODE);

        } else if (id == R.id.action_settings) {//未开放
            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

    public List<DomainBean> getDataset() {
        return ORMUtil.getDomainSet();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE) {
            mAdapter.updateDataSet(getDataset());
        }
    }
    /**
     * 配置接口
     */
    public static void setDomainCheckedListener(CheckedListener checkedListener){
        CHECKED_LISTENER = checkedListener;
    }

    /**
     * 接口回调
     */
    public static void onChecked(String domain){
        if (CHECKED_LISTENER!=null) {
            CHECKED_LISTENER.onChecked(domain);
        }
    }
    /**
     * 选中接口回调，在域名发生变化时回调
     */
    public interface CheckedListener{
        void onChecked(String domain);
    }

    /**
     * 初始化View
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DomainAdapter();
        mRecyclerView.setAdapter(mAdapter);
        //更新数据集
        mAdapter.updateDataSet(getDataset());
    }
}

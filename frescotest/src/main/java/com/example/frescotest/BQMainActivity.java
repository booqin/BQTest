package com.example.frescotest;


import com.example.frescotest.adapter.RVAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class BQMainActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RVAdapter mAdapter;

    private final String[] mDates = {"http://ocvkuozgf.bkt.clouddn.com/14599544138261.png","http://ocvkuozgf.bkt.clouddn.com/2.3%E6%94%B6%E8%97%8F%E5%BA%97%E9%93%BA.png",
                                    "http://ocvkuozgf.bkt.clouddn.com/20170104154326.png","http://ocvkuozgf.bkt.clouddn.com/6163057f-354c-47c6-bae5-5d094ff9cb40.png",
                                    "http://ocvkuozgf.bkt.clouddn.com/AutoLayout.png","http://ocvkuozgf.bkt.clouddn.com/AutoLayout.png",
                                    "http://ocvkuozgf.bkt.clouddn.com/message_list.png","http://ocvkuozgf.bkt.clouddn.com/recyclerview_video.png"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fre_activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RVAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setDateSet(mDates);
//                        mAdapter.reduceDateset();
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }


}

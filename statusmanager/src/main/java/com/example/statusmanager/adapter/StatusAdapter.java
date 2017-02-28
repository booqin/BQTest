package com.example.statusmanager.adapter;

import com.example.statusmanager.bean.StateBean;

import android.support.v7.widget.RecyclerView;

/**
 * TODO
 * Created by Boqin on 2017/2/28.
 * Modified by Boqin
 *
 * @Version
 */
public abstract class StatusAdapter {

    public abstract int getCount();

    public abstract String getUserId();

    public abstract int getHeaderCount();

    public abstract void onUpdate(int type, int position, StateBean stateBean);

    public abstract void notifyItemChanged(int position);
}

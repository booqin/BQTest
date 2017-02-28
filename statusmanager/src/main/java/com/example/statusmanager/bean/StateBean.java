package com.example.statusmanager.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页中关注和赞事件返回Bean，用于Rxjava的传递
 * Created by Boqin on 2016/11/29.
 * Modified by Boqin
 *
 * @Version
 */
public class StateBean {
    /** item对象id */
    private String mId;
    /** 是否选中 */
    private boolean mChecked;
    /** 喜欢数量 */
    private int mAmount;
    /** 在列表显示的ViewHolder位置 */
    private List<Integer> mViewHoldPosition;
    /** 对应的DataSet位置 */
    private List<Integer> mUpdatePosition;

    public StateBean(String id){
        mId = id;
        mViewHoldPosition = new ArrayList<>();
        mUpdatePosition = new ArrayList<>();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        this.mChecked = checked;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        this.mAmount = amount;
    }

    public List<Integer> getUpdatePosition() {
        return mUpdatePosition;
    }

    public void addUpdatePosition(int updatePosition) {
        mUpdatePosition.add(updatePosition);
    }

    public List<Integer> getViewHoldPosition() {
        return mViewHoldPosition;
    }

    public void addViewHoldPosition(int updateViewHoldPosition) {
        mViewHoldPosition.add(updateViewHoldPosition);
    }
}

package com.example.statusmanager.bean;

import java.util.ArrayList;
import java.util.List;

import com.example.statusmanager.interfaces.IStatusBean;

/**
 * 包裹类
 * Created by Boqin on 2017/3/1.
 * Modified by Boqin
 *
 * @Version
 */
public class StatusWrapper<T extends IStatusBean>{

    private T wrapBean;

    private String id;

    private int type;

    /** 在列表显示的ViewHolder位置 */
    private List<Integer> mViewHoldPosition;
    /** 对应的DataSet位置 */
    private List<Integer> mUpdatePosition;

    public StatusWrapper(T statusBean){
        setBean(statusBean);
        setId(statusBean.getId());
        setType(statusBean.getType());

        mViewHoldPosition = new ArrayList<>();
        mUpdatePosition = new ArrayList<>();

    }

    public T getBean(){
        return wrapBean;
    }

    public void setBean(T o){
        wrapBean = o;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

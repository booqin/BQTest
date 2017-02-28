package com.example.statusmanager.bean;

/**
 * 关注变化事件事件
 * Created by Boqin on 2016/12/8.
 * Modified by Boqin
 *
 * @Version
 */
public class EventStateChangeBean {

    /** item对象id */
    private String mId;
    /** 是否选中 */
    private boolean mChecked;
    /** 喜欢数量 */
    private int mLikeAmount;
    /** 类型字段 */
    private int mType;

    /**
     * EventBus用于关注和喜欢
     *
     * @param likeAmount 喜欢用， 关注时为-1
     * @description: Created by Boqin on 2016/12/16 21:39
     */
    public EventStateChangeBean(String id, boolean checked, int likeAmount, int type) {
        mId = id;
        mChecked = checked;
        mLikeAmount = likeAmount;
        mType = type;
    }

    /**
     * 获取id
     */
    public String getId() {
        return mId;
    }

    /**
     * 是否被选中
     */
    public boolean isChecked() {
        return mChecked;
    }

    /**
     * 赞数量
     */
    public int getLikeAmount() {
        return mLikeAmount;
    }

    /**
     * 获取类型
     */
    public int getType() {
        return mType;
    }
}

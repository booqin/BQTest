package com.example.statusmanager.interfaces;

import com.example.statusmanager.bean.EventStateChangeBean;

/**
 * TODO
 * Created by Boqin on 2017/2/28.
 * Modified by Boqin
 *
 * @Version
 */
public interface IStatusManager {

    /** 关注类型 */
    int FOLLOW = 0;
    /** 赞类型 */
    int LIKE = 1;
    /** 评论类型 */
    int COMMENT = 2;
    /** 浏览量类型 */
    int BROWSE = 3;

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(EventStateChangeBean eventStateChangeBean);
}

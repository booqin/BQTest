package com.example.statusmanager.interfaces;

/**
 * 状态管理工具，<p>用于赞，关注等需要同步的状态 <p>
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

    /**
     * 注册事件
     */
    void register();

    /**
     * 取消注册，避免内存泄露
     */
    void unregister();

}

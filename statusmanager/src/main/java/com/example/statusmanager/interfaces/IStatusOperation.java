package com.example.statusmanager.interfaces;

import com.example.statusmanager.bean.StateBean;

/**
 * 关注，赞，收藏状态操作接口
 * Created by Boqin on 2016/11/30.
 * Modified by Boqin
 *
 * @Version
 */
public interface IStatusOperation {

    /**
     * 帖子Id
     *
     * @description: Created by Boqin on 2016/12/7 12:55
     */
    String getId();

    /**
     * 用户Id
     *
     * @description: Created by Boqin on 2016/12/7 12:56
     */
    String getUserId();

    /**
     * 更新喜欢操作
     *
     * @description: Created by Boqin on 2016/12/7 12:56
     */
    void onUpdateLike(StateBean rxStateBean);

    /**
     * 更新关注操作
     *
     * @description: Created by Boqin on 2016/12/7 12:56
     */
    void onUpdateFollow(StateBean rxStateBean);

    /**
     * 更新评论操作
     *
     * @description: Created by Boqin on 2016/12/7 12:56
     */
    void onUpdateComment(StateBean rxStateBean);

    /**
     * 更新浏览量操作
     *
     * @description: Created by Boqin on 2016/12/7 12:56
     */
    void onUpdateBrowseAmount(StateBean rxStateBean);
}

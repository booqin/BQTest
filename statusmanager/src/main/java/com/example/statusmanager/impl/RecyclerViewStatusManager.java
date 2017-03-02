package com.example.statusmanager.impl;

import java.util.Iterator;
import java.util.List;

import com.example.statusmanager.BaseStatusManager;
import com.example.statusmanager.bean.StatusWrapper;
import com.example.statusmanager.interfaces.IStatusAdapter;
import com.example.statusmanager.interfaces.IStatusHolder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 适用于RecyclerView的状态管理器
 * Created by Boqin on 2017/2/28.
 * Modified by Boqin
 *
 * @Version
 */
public class RecyclerViewStatusManager extends BaseStatusManager{

    private RecyclerView mRecyclerView;

    private IStatusAdapter mAdapter;

    public RecyclerViewStatusManager(RecyclerView recyclerView){
        super();
        mRecyclerView = recyclerView;
    }

    public void setAdapter(IStatusAdapter adapter){
        mAdapter = adapter;
    }

//    @Override
//    public Subscription doSubscribe(int type, StateBean stateBean) {
//        if (mAdapter == null) {
//            return null;
//        }
//        Subscription subscribe = null;
//        switch (type) {
//            case FOLLOW:
//                subscribe = getFollowSubscription(stateBean);
//                break;
//            case LIKE:
//                subscribe = getLikeSubscription(stateBean);
//                break;
//            case COMMENT:
//                subscribe = getCommentSubscription(stateBean);
//                break;
//            case BROWSE:
//                subscribe = getBrowseAmountSubscription(stateBean);
//                break;
//            default:
//                break;
//        }
//
//        return subscribe;
//    }

    @Override
    public Subscription doSubscribe(StatusWrapper stateBean) {
        if (mAdapter == null ) {
            return null;
        }
        Subscription subscribe = null;
        switch (stateBean.getType()) {
            case FOLLOW:
                subscribe = getFollowSubscription(stateBean);
                break;
//            case LIKE:
//                subscribe = getLikeSubscription(stateBean);
//                break;
//            case COMMENT:
//                subscribe = getCommentSubscription(stateBean);
//                break;
//            case BROWSE:
//                subscribe = getBrowseAmountSubscription(stateBean);
//                break;
            default:
                break;
        }

        return subscribe;
    }

    /**
     * 设置关注事件
     *
     * @description: Created by Boqin on 2016/12/16 21:46
     */
    private Subscription getFollowSubscription(StatusWrapper bean) {
        return Observable.just(bean)
                .observeOn(Schedulers.newThread())
                .map(new Func1<StatusWrapper, StatusWrapper>() {
                    @Override
                    public StatusWrapper call(StatusWrapper likeFollowBean) {
                        String userId = likeFollowBean.getId();
                        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

                            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                                if (mAdapter.getId(i)
                                        .equals(likeFollowBean.getId())) {
                                    likeFollowBean.addUpdatePosition(i);
                                }
                            }
                            for (int i = linearLayoutManager.findFirstVisibleItemPosition(); i <= linearLayoutManager.findLastVisibleItemPosition();
                                    i++) {
                                if (mRecyclerView.findViewHolderForAdapterPosition(i) instanceof IStatusHolder) {
                                    IStatusHolder statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(i);
                                    if (statusOperation != null && userId != null && userId.equals(statusOperation.getId())) {
                                        //因为存在banner
                                        //移除需要更新的ItemView的位置，直接通过VH更新的位置添加
                                        likeFollowBean.addViewHoldPosition(i - mAdapter.getHeaderCount());
                                        List list = likeFollowBean.getUpdatePosition();
                                        list.remove(list.indexOf(i - mAdapter.getHeaderCount()));
                                    }
                                }
                            }
                        }
                        return likeFollowBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<StatusWrapper>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(StatusWrapper likeFollowBean) {
                        if (likeFollowBean == null) {
                            return;
                        }
                        IStatusHolder statusOperation;
                        Iterator<Integer> iterator = likeFollowBean.getViewHoldPosition().iterator();
                        while (iterator.hasNext()){
                            Integer integer = iterator.next();
                            statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(integer + mAdapter.getHeaderCount());
                            if (statusOperation != null) {
                                statusOperation.onUpdateFollow(likeFollowBean);
                                //                                mDataSet.get(integer).getUserInfo().setFollow(likeFollowBean.isChecked());
                                mAdapter.onUpdate(FOLLOW, integer, likeFollowBean);
                            }
                        }
//                        for (Integer integer : likeFollowBean.getViewHoldPosition()) {
//                            statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(integer + mAdapter.getHeaderCount());
//                            if (statusOperation != null) {
//                                statusOperation.onUpdateFollow(likeFollowBean.getBean());
////                                mDataSet.get(integer).getUserInfo().setFollow(likeFollowBean.isChecked());
//                                mAdapter.onUpdate(FOLLOW, integer, likeFollowBean.getBean());
//                            }
//                        }
                        //更新其他同用户id的数据并通知更新
                        for (int i = 0; i < likeFollowBean.getUpdatePosition().size(); i++) {
//                            if (mDataSet.get(likeFollowBean.getUpdatePosition().get(i)).getUserInfo() != null) {
//                                mDataSet.get(likeFollowBean.getUpdatePosition().get(i)).getUserInfo().setFollow(likeFollowBean.isChecked());
//
//                            }
                            mAdapter.onUpdate(FOLLOW, (Integer) likeFollowBean.getUpdatePosition().get(i), likeFollowBean);
//                            mRecyclerView.notifyItemChanged(likeFollowBean.getUpdatePosition().get(i) + getHeaderCount());
                            mAdapter.notifyItemViewChanged((Integer)likeFollowBean.getUpdatePosition().get(i) + mAdapter.getHeaderCount());
                        }

                    }
                });
    }

//    /**
//     * 设置关注事件
//     *
//     * @description: Created by Boqin on 2016/12/16 21:46
//     */
//    private Subscription getFollowSubscription(StateBean bean) {
//        return Observable.just(bean)
//                .observeOn(Schedulers.newThread())
//                .map(new Func1<StateBean, StateBean>() {
//                    @Override
//                    public StateBean call(StateBean likeFollowBean) {
//                        String userId = likeFollowBean.getId();
//                        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//
//                            for (int i = 0; i < mAdapter.getItemCount(); i++) {
//                                if (mAdapter.getId(i)
//                                        .equals(likeFollowBean.getId())) {
//                                    likeFollowBean.addUpdatePosition(i);
//                                }
//                            }
//                            for (int i = linearLayoutManager.findFirstVisibleItemPosition(); i <= linearLayoutManager.findLastVisibleItemPosition();
//                                    i++) {
//                                if (mRecyclerView.findViewHolderForAdapterPosition(i) instanceof IStatusHolder) {
//                                    IStatusHolder statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(i);
//                                    if (statusOperation != null && userId != null && userId.equals(statusOperation.getUserId())) {
//                                        //因为存在banner
//                                        //移除需要更新的ItemView的位置，直接通过VH更新的位置添加
//                                        likeFollowBean.addViewHoldPosition(i - mAdapter.getHeaderCount());
//                                        List list = likeFollowBean.getUpdatePosition();
//                                        list.remove(list.indexOf(i - mAdapter.getHeaderCount()));
//                                    }
//                                }
//                            }
//                        }
//                        return likeFollowBean;
//                    }
//                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<StateBean>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(StateBean likeFollowBean) {
//                        if (likeFollowBean == null) {
//                            return;
//                        }
//                        IStatusHolder statusOperation;
//                        for (Integer integer : likeFollowBean.getViewHoldPosition()) {
//                            statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(integer + mAdapter.getHeaderCount());
//                            if (statusOperation != null) {
//                                statusOperation.onUpdateFollow(likeFollowBean);
////                                mDataSet.get(integer).getUserInfo().setFollow(likeFollowBean.isChecked());
//                                mAdapter.onUpdate(FOLLOW, integer, likeFollowBean);
//                            }
//                        }
//                        //更新其他同用户id的数据并通知更新
//                        for (int i = 0; i < likeFollowBean.getUpdatePosition().size(); i++) {
////                            if (mDataSet.get(likeFollowBean.getUpdatePosition().get(i)).getUserInfo() != null) {
////                                mDataSet.get(likeFollowBean.getUpdatePosition().get(i)).getUserInfo().setFollow(likeFollowBean.isChecked());
////
////                            }
//                            mAdapter.onUpdate(FOLLOW, likeFollowBean.getUpdatePosition().get(i), likeFollowBean);
////                            mRecyclerView.notifyItemChanged(likeFollowBean.getUpdatePosition().get(i) + getHeaderCount());
//                            mAdapter.notifyItemViewChanged(likeFollowBean.getUpdatePosition().get(i) + mAdapter.getHeaderCount());
//                        }
//
//                    }
//                });
//    }

//    /**
//     * 设置喜欢事件
//     *
//     * @description: Created by Boqin on 2016/12/16 21:47
//     */
//    private Subscription getLikeSubscription(StateBean bean) {
//        return Observable.just(bean)
//                .observeOn(Schedulers.newThread())
//                .map(new Func1<StateBean, StateBean>() {
//                    @Override
//                    public StateBean call(StateBean likeFollowBean) {
//                        String id = likeFollowBean.getId();
//                        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//                            for (int i = linearLayoutManager.findFirstVisibleItemPosition(); i <= linearLayoutManager.findLastVisibleItemPosition();
//                                    i++) {
//                                if (mRecyclerView.findViewHolderForAdapterPosition(i) instanceof IStatusHolder) {
//                                    IStatusHolder statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(i);
//                                    if (statusOperation != null && id != null && id.equals(statusOperation.getId())) {
//                                        likeFollowBean.addViewHoldPosition(i - mAdapter.getHeaderCount());
//                                        return likeFollowBean;
//                                    }
//                                }
//                            }
//                            for (int i = 0; i < mAdapter.getItemCount(); i++) {
//                                if (mAdapter.getId(i).equals(likeFollowBean.getId())) {
//                                    likeFollowBean.addUpdatePosition(i);
//                                }
//                            }
//                        }
//                        return likeFollowBean;
//                    }
//                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<StateBean>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(StateBean likeFollowBean) {
//                        if (likeFollowBean == null) {
//                            return;
//                        }
//                        IStatusHolder statusOperation;
//                        for (Integer integer : likeFollowBean.getViewHoldPosition()) {
//                            statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(integer + mAdapter.getHeaderCount());
//                            if (statusOperation != null) {
//                                statusOperation.onUpdateLike(likeFollowBean);
////                                mDataSet.get(integer).setLike(likeFollowBean.isChecked());
////                                mDataSet.get(integer).setLikeAmount(likeFollowBean.getAmount());
//                                mAdapter.onUpdate(LIKE, integer, likeFollowBean);
//                            }
//                        }
//                        for (int i = 0; i < likeFollowBean.getUpdatePosition().size(); i++) {
////                            mDataSet.get(likeFollowBean.getUpdatePosition().get(i)).setLike(likeFollowBean.isChecked());
////                            mDataSet.get(likeFollowBean.getUpdatePosition().get(i)).setLikeAmount(likeFollowBean.getAmount());
//                            mAdapter.onUpdate(LIKE, likeFollowBean.getUpdatePosition().get(i), likeFollowBean);
////                            mRecyclerView.notifyItemChanged(likeFollowBean.getUpdatePosition().get(i) + getHeaderCount());
//                            mAdapter.notifyItemViewChanged(likeFollowBean.getUpdatePosition().get(i) + mAdapter.getHeaderCount());
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 设置评论数量
//     *
//     * @description: Created by Boqin on 2016/12/16 21:47
//     */
//    private Subscription getCommentSubscription(StateBean bean) {
//        return Observable.just(bean)
//                .observeOn(Schedulers.newThread())
//                .map(new Func1<StateBean, StateBean>() {
//                    @Override
//                    public StateBean call(StateBean likeFollowBean) {
//                        String id = likeFollowBean.getId();
//                        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//                            for (int i = linearLayoutManager.findFirstVisibleItemPosition(); i <= linearLayoutManager.findLastVisibleItemPosition();
//                                    i++) {
//                                if (mRecyclerView.findViewHolderForAdapterPosition(i) instanceof IStatusHolder) {
//                                    IStatusHolder statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(i);
//                                    if (statusOperation != null && id != null && id.equals(statusOperation.getId())) {
//                                        likeFollowBean.addViewHoldPosition(i - mAdapter.getHeaderCount());
//                                        return likeFollowBean;
//                                    }
//                                }
//                            }
//                            for (int i = 0; i < mAdapter.getItemCount(); i++) {
//                                if (mAdapter.getId(i).equals(likeFollowBean.getId())) {
//                                    likeFollowBean.addUpdatePosition(i);
//                                }
//                            }
//                        }
//                        return likeFollowBean;
//                    }
//                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<StateBean>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(StateBean likeFollowBean) {
//                        if (likeFollowBean == null) {
//                            return;
//                        }
//                        IStatusHolder statusOperation;
//                        for (Integer integer : likeFollowBean.getViewHoldPosition()) {
//                            statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(integer + mAdapter.getHeaderCount());
//                            if (statusOperation != null) {
//                                statusOperation.onUpdateComment(likeFollowBean);
////                                mDataSet.get(integer).setCmtAmount(likeFollowBean.getAmount());
//                                mAdapter.onUpdate(COMMENT, integer, likeFollowBean);
//                            }
//                        }
//                        for (int i = 0; i < likeFollowBean.getUpdatePosition().size(); i++) {
////                            mDataSet.get(likeFollowBean.getUpdatePosition().get(i)).setCmtAmount(likeFollowBean.getAmount());
//                            mAdapter.onUpdate(COMMENT, likeFollowBean.getUpdatePosition().get(i), likeFollowBean);
////                            mRecyclerView.notifyItemChanged(likeFollowBean.getUpdatePosition().get(i) + getHeaderCount());
//                            mAdapter.notifyItemViewChanged(likeFollowBean.getUpdatePosition().get(i) + mAdapter.getHeaderCount());
//
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 设置阅读数量
//     *
//     * @description: Created by Boqin on 2016/12/16 21:47
//     */
//    private Subscription getBrowseAmountSubscription(StateBean bean) {
//        return Observable.just(bean)
//                .observeOn(Schedulers.newThread())
//                .map(new Func1<StateBean, StateBean>() {
//                    @Override
//                    public StateBean call(StateBean browseBean) {
//                        String id = browseBean.getId();
//                        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//                            for (int i = linearLayoutManager.findFirstVisibleItemPosition(); i <= linearLayoutManager.findLastVisibleItemPosition();
//                                    i++) {
//                                if (mRecyclerView.findViewHolderForAdapterPosition(i) instanceof IStatusHolder) {
//                                    IStatusHolder statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(i);
//                                    if (statusOperation != null && id != null && id.equals(statusOperation.getId())) {
//                                        browseBean.addViewHoldPosition(i - mAdapter.getHeaderCount());
//                                        return browseBean;
//                                    }
//                                }
//                            }
//                            for (int i = 0; i < mAdapter.getItemCount(); i++) {
//                                if (mAdapter.getId(i).equals(browseBean.getId())) {
//                                    browseBean.addUpdatePosition(i);
//                                }
//                            }
//                        }
//                        return browseBean;
//                    }
//                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<StateBean>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(StateBean browseBean) {
//                        if (browseBean == null) {
//                            return;
//                        }
//                        IStatusHolder statusOperation;
//                        for (Integer integer : browseBean.getViewHoldPosition()) {
//                            statusOperation = (IStatusHolder) mRecyclerView.findViewHolderForAdapterPosition(integer + mAdapter.getHeaderCount());
//                            if (statusOperation != null) {
//                                statusOperation.onUpdateBrowseAmount(browseBean);
////                            mDataSet.get(integer).setBrowseAmount(browseBean.getAmount());
//                            }
//                        }
//                    }
//                });
//    }

}

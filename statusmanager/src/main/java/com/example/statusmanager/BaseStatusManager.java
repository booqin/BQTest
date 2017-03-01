package com.example.statusmanager;

import java.sql.Wrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.example.statusmanager.bean.EventStateChangeBean;
import com.example.statusmanager.bean.StateBean;
import com.example.statusmanager.interfaces.IStatusManager;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * TODO
 * Created by Boqin on 2017/2/28.
 * Modified by Boqin
 *
 * @Version
 */
public abstract class BaseStatusManager implements IStatusManager {

    /** 复合订阅，可取消订阅 */
    protected CompositeSubscription mSubscriptions;

    public BaseStatusManager(){
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void register() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregister() {
        EventBus.getDefault().unregister(this);
        mSubscriptions.unsubscribe();
    }

    public static void post(EventStateChangeBean eventStateChangeBean) {
        EventBus.getDefault().post(eventStateChangeBean);
    }

//    public static void post(EventStateChangeBean eventStateChangeBean) {
//        StateWrap stateWrap = new StateWrap(eventStateChangeBean);
//        EventBus.getDefault().post(stateWrap);
//    }

    /**
     * 监听状态更新入口
     */
//    @Subscribe
//    public void onEventMainThread(StateWrap change) {
//        Subscription subscribe;
////        StateBean rxStateBean = new StateBean(change.getId());
////        rxStateBean.setAmount(change.getLikeAmount());
////        rxStateBean.setChecked(change.isChecked());
//        subscribe = doSubscribe(change.getType(), change);
//        if (subscribe == null) {
//            return;
//        }
//        mSubscriptions.add(subscribe);
//    }

    /**
     * 监听状态更新入口
     */
    @Subscribe
    public void onEventMainThread(EventStateChangeBean change) {
        Subscription subscribe;
        StateBean rxStateBean = new StateBean(change.getId());
        rxStateBean.setAmount(change.getLikeAmount());
        rxStateBean.setChecked(change.isChecked());
        subscribe = doSubscribe(change.getType(), rxStateBean);
        if (subscribe == null) {
            return;
        }
        mSubscriptions.add(subscribe);
    }

    public abstract Subscription doSubscribe(int type, StateBean stateBean);

//    public abstract Subscription doSubscribe(int type, StateWrap stateBean);

}

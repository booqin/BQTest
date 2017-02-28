package com.example.statusmanager;

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
    public void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
        mSubscriptions.unsubscribe();
    }

    @Override
    public void post(EventStateChangeBean eventStateChangeBean) {
        EventBus.getDefault().post(eventStateChangeBean);
    }

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

    public CompositeSubscription getSubscriptions() {
        return mSubscriptions;
    }
}

package com.example.administrator.bqtest.adapter;

import java.util.List;

import com.example.administrator.bqtest.R;
import com.example.administrator.bqtest.bean.FollowBean;
import com.example.statusmanager.interfaces.IStatusAdapter;
import com.example.statusmanager.bean.EventStateChangeBean;
import com.example.statusmanager.bean.StateBean;
import com.example.statusmanager.impl.RecyclerViewStatusManager;
import com.example.statusmanager.interfaces.IStatusHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * TODO
 * Created by Boqin on 2017/3/1.
 * Modified by Boqin
 *
 * @Version
 */
public class StatusRVAdapter extends RecyclerView.Adapter implements IStatusAdapter {

    private List<FollowBean> mFollowBeanList;

    public StatusRVAdapter(List<FollowBean> list){
        mFollowBeanList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((StatusViewHolder) holder).render(mFollowBeanList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFollowBeanList.size();
    }

    @Override
    public String getId(int position) {
        return ""+mFollowBeanList.get(position).getUserId();
    }

    @Override
    public int getHeaderCount() {
        return 0;
    }

    @Override
    public void onUpdate(int type, int position, StateBean stateBean) {
        mFollowBeanList.get(position).setChecked(stateBean.isChecked());
    }

    @Override
    public void notifyItemViewChanged(int position) {
        notifyItemChanged(position);
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder implements IStatusHolder {


        private Button mFollowButton;
        private TextView mDetail;

        private FollowBean mFollowBean;

        public StatusViewHolder(View itemView) {
            super(itemView);
            mFollowButton = (Button) itemView.findViewById(R.id.follow);
            mDetail = (TextView) itemView.findViewById(R.id.detail);
            mFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventStateChangeBean changeBean = new EventStateChangeBean(""+mFollowBean.getUserId(), !mFollowBean.isChecked(), 0, RecyclerViewStatusManager.FOLLOW);
                    RecyclerViewStatusManager.post(changeBean);
                }
            });
        }

        public void render(FollowBean followBean){
            mFollowBean = followBean;
            mFollowButton.setBackgroundResource(mFollowBean.isChecked()?R.color.aliwx_common_text_color4:R.color.aliwx_bg_color_white);
            mDetail.setText("id:"+ mFollowBean.getId()+";  "+"userId:"+mFollowBean.getUserId());
        }

        @Override
        public String getId() {
            return ""+mFollowBean.getId();
        }

        @Override
        public String getUserId() {
            return ""+mFollowBean.getUserId();
        }

        @Override
        public void onUpdateLike(StateBean rxStateBean) {
            Log.d("BQ", "LIKE");
            mFollowBean.setChecked(rxStateBean.isChecked());
            mFollowButton.setBackgroundResource(rxStateBean.isChecked()?R.color.aliwx_common_text_color4:R.color.aliwx_bg_color_white);
        }

        @Override
        public void onUpdateFollow(StateBean rxStateBean) {
            Log.d("BQ", "Follow");
            mFollowBean.setChecked(rxStateBean.isChecked());
            mFollowButton.setBackgroundResource(rxStateBean.isChecked()?R.color.aliwx_common_text_color4:R.color.aliwx_bg_color_white);
        }

        @Override
        public void onUpdateComment(StateBean rxStateBean) {

        }

        @Override
        public void onUpdateBrowseAmount(StateBean rxStateBean) {

        }
    }
}

package com.example.domainsetting.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.domainsetting.DomainSettingActivity;
import com.example.domainsetting.R;
import com.example.domainsetting.bean.DomainBean;
import com.example.domainsetting.utils.ORMUtil;
import com.example.domainsetting.viewholder.DomainViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

/**
 * 域名设置适配器
 * Created by Boqin on 2017/2/16.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainAdapter extends RecyclerView.Adapter {

    /** 数据集 */
    private List<DomainBean> mDomainBeanList;

    /** 当前选中位子 */
    private int mCurrentSelectedPosition;

    public DomainAdapter() {
        mDomainBeanList = new ArrayList<>();
        mCurrentSelectedPosition = -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_domain, parent, false);
        return new DomainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DomainViewHolder) {
            ((DomainViewHolder) holder).onSelectedListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //选中后需要保存到表中，同时回调更新项目的接口
                        onChecked(position);
                    } else {
                        if (position == mCurrentSelectedPosition) {
                            buttonView.setChecked(true);
                            Toast.makeText(buttonView.getContext(), "至少需要一个地址", Toast.LENGTH_SHORT).show();
                        } else {
                            mDomainBeanList.get(mCurrentSelectedPosition).setChecked(false);
                        }
                    }
                }
            });
            ((DomainViewHolder) holder).render(mDomainBeanList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDomainBeanList.size();
    }

    /**
     * 更新数据集
     */
    public void updateDataSet(List<DomainBean> list) {
        mDomainBeanList.clear();
        mDomainBeanList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 被选中时的处理
     */
    private void onChecked(int position) {
        String checkedDomain = ORMUtil.getCheckedDomain();
        ORMUtil.updateDomainChecked(mDomainBeanList.get(position).getDomain());
        if (position != mCurrentSelectedPosition) {
            if (mCurrentSelectedPosition >= 0 && mCurrentSelectedPosition < mDomainBeanList.size()) {
                mDomainBeanList.get(mCurrentSelectedPosition).setChecked(false);
                notifyItemChanged(mCurrentSelectedPosition);
            }
        }
        mCurrentSelectedPosition = position;
        mDomainBeanList.get(mCurrentSelectedPosition).setChecked(true);
        //单选中的域名发生变化时
        if (!mDomainBeanList.get(position).getDomain().equals(checkedDomain)) {
            DomainSettingActivity.onChecked(mDomainBeanList.get(position).getDomain());
        }
    }
}

package com.example.domainsetting.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.domainsetting.R;
import com.example.domainsetting.bean.DomainBean;
import com.example.domainsetting.viewholder.DomainViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

/**
 * TODO
 * Created by Boqin on 2017/2/16.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainAdapter extends RecyclerView.Adapter{

    private List<DomainBean> mDomainBeanList;

    private int mCurrentSelectedPosition;

    public DomainAdapter(){
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
            ((DomainViewHolder)holder).render(mDomainBeanList.get(position));
            ((DomainViewHolder)holder).onSelectedListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (position != mCurrentSelectedPosition) {
                            if(mCurrentSelectedPosition>=0 && mCurrentSelectedPosition<mDomainBeanList.size()){
                                mDomainBeanList.get(mCurrentSelectedPosition).setChecked(false);
                                notifyItemChanged(mCurrentSelectedPosition);
                            }
                        }
                        Log.d("DA", "true-------position"+position+";  mCurrentSelectedPosition:"+mCurrentSelectedPosition);
                        mCurrentSelectedPosition = position;
                        mDomainBeanList.get(mCurrentSelectedPosition).setChecked(true);
                    }else {
                        Log.d("DA", "false-------position"+position+";  mCurrentSelectedPosition:"+mCurrentSelectedPosition);
                            if (position == mCurrentSelectedPosition) {
                                buttonView.setChecked(true);
                                Toast.makeText(buttonView.getContext(), "至少需要一个地址", Toast.LENGTH_SHORT).show();
                            }else {
                                mDomainBeanList.get(mCurrentSelectedPosition).setChecked(false);
                            }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDomainBeanList.size();
    }

    public void updateDataSet(List<DomainBean> list){
        mDomainBeanList.clear();
        mDomainBeanList.addAll(list);
        notifyDataSetChanged();
    }
}

package com.example.domainsetting.viewholder;

import com.example.domainsetting.R;
import com.example.domainsetting.bean.DomainBean;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * TODO
 * Created by Boqin on 2017/2/16.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainViewHolder extends RecyclerView.ViewHolder{

    private TextView mDomainTV;
    private CheckBox mSelected;

    public DomainViewHolder(View itemView) {
        super(itemView);
        mDomainTV = (TextView) itemView.findViewById(R.id.text_ip);
        mSelected = (CheckBox) itemView.findViewById(R.id.checkBox);
    }

    public void render(DomainBean d){
        mDomainTV.setText(d.getDomian());
        mSelected.setChecked(d.isChecked());
    }

    public void onSelectedListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener){
        mSelected.setOnCheckedChangeListener(onCheckedChangeListener);
    }
}

package com.example.domainsetting.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.domainsetting.R;
import com.example.domainsetting.bean.DomainBean;

/**
 * 域名VH
 * Created by Boqin on 2017/2/16.
 * Modified by Boqin
 *
 * @Version
 */
public class DomainViewHolder extends RecyclerView.ViewHolder{

    /** 域名编辑框 */
    private TextView mDomainTV;
    /** 选中框 */
    private CheckBox mSelected;

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    public DomainViewHolder(View itemView) {
        super(itemView);
        mDomainTV = (TextView) itemView.findViewById(R.id.text_ip);
        mSelected = (CheckBox) itemView.findViewById(R.id.checkBox);
    }

    public void render(DomainBean d){
        mDomainTV.setText(itemView.getContext().getString(R.string.domain, d.getDomain()));
        //在复用，checkbox更新同样状态时不会回调该接口，手动更新
        if (mSelected.isChecked()&&d.isChecked()&& mOnCheckedChangeListener !=null) {
            mOnCheckedChangeListener.onCheckedChanged(mSelected, true);
        }
        mSelected.setChecked(d.isChecked());
    }

    public void onSelectedListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener){
        mSelected.setOnCheckedChangeListener(onCheckedChangeListener);
        mOnCheckedChangeListener = onCheckedChangeListener;
    }
}

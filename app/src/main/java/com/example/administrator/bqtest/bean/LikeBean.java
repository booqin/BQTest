package com.example.administrator.bqtest.bean;

import com.example.statusmanager.interfaces.IStatusBean;
import com.example.statusmanager.interfaces.IStatusManager;

/**
 * 赞
 * Created by Boqin on 2017/3/2.
 * Modified by Boqin
 *
 * @Version
 */
public class LikeBean implements IStatusBean{

    private String id;

    private boolean checked;


    @Override
    public String getStatusKey() {
        return id;
    }

    @Override
    public int getStatusType() {
        return LIKE;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

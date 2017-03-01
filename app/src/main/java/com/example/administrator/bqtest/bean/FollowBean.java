package com.example.administrator.bqtest.bean;

/**
 * 需要传递的Bean
 * Created by Boqin on 2017/3/1.
 * Modified by Boqin
 *
 * @Version
 */
public class FollowBean {

    private int id;

    private int userId;

    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

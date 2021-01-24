package com.hongsam.famstrory.data;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class TimeLineFamily {
    Bitmap profile;
    String name;
    String nickName;
    String showMessage;
    String time;
    int ViewType;

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(String showMessage) {
        this.showMessage = showMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getViewType() {
        return ViewType;
    }

    public void setViewType(int viewType) {
        ViewType = viewType;
    }

    public TimeLineFamily(Bitmap profile, String name, String nickName, String showMessage, String time, int ViewType) {
        this.profile = profile;
        this.name = name;
        this.nickName = nickName;
        this.showMessage = showMessage;
        this.time = time;
        this.ViewType = ViewType;
    }


}

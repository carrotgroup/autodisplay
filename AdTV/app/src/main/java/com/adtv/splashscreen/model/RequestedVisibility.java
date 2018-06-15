package com.adtv.splashscreen.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestedVisibility {
    @SerializedName(".tag")
    @Expose
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

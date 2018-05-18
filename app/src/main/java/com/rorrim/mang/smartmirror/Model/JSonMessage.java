package com.rorrim.mang.smartmirror.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSonMessage {

    @SerializedName("msg")
    @Expose
    private final String msg;

    public JSonMessage(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Message [msg=" + msg;
    }

}

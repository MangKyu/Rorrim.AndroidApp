package com.rorrim.mang.smartmirror;

public class HttpConnectionException extends Exception {

    @Override
    public String getMessage(){
        return "Connection Error";
    }

}

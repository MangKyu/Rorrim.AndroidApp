package com.rorrim.mang.smartmirror.Exception;

public class HttpConnectionException extends Exception {

    @Override
    public String getMessage(){
        return "Connection Error";
    }

}

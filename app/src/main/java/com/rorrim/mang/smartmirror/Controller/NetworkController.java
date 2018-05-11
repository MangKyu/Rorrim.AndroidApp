package com.rorrim.mang.smartmirror.Controller;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rorrim.mang.smartmirror.Activity.MainActivity;
import com.rorrim.mang.smartmirror.Model.HttpConnection;

public class NetworkController {
    private ConnectivityManager cm;
    private NetworkInfo ni;
    private HttpConnection httpConnection;

    public NetworkController(MainActivity mainActivity){
        this.cm = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.ni = cm.getActiveNetworkInfo();
        this.httpConnection = HttpConnection.getInstance();
    }

    public boolean isConnected(){
        // Check whether Network is connected
        boolean isConnected = ni != null && ni.isConnectedOrConnecting();
        return isConnected;
    }

    public String getNetworkTypeName(){
        return ni.getTypeName();
    }

    private int getNetworkType(){
        return ni.getType();
    }

    public boolean checkWifi(){
        switch(getNetworkType()){
            case ConnectivityManager.TYPE_WIFI:
                return true;
            case ConnectivityManager.TYPE_MOBILE:
                return false;
            default:
                return false;
        }
    }

    public HttpConnection getHttpConnection() {
        return httpConnection;
    }
}
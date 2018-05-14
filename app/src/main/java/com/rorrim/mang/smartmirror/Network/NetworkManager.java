package com.rorrim.mang.smartmirror.Network;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.rorrim.mang.smartmirror.Activity.MainActivity;

public class NetworkManager {
    private ConnectivityManager cm;
    private NetworkInfo ni;

    public NetworkManager(MainActivity mainActivity){
        this.cm = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.ni = cm.getActiveNetworkInfo();
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
}
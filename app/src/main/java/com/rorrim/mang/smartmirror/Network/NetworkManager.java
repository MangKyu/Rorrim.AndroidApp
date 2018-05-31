package com.rorrim.mang.smartmirror.Network;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager {
    private static NetworkManager instance;
    private ConnectivityManager cm;
    private NetworkInfo ni;

    public NetworkManager(){

    }

    public NetworkManager(Context context){
        //this.cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //this.ni = cm.getActiveNetworkInfo();
    }

    public static NetworkManager getInstance() {
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    public boolean isConnected(){
        // Check whether Network is connected
        boolean isConnected = ni != null && ni.isConnectedOrConnecting();
        return isConnected;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
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
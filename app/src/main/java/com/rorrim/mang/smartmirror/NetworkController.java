package com.rorrim.mang.smartmirror;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkController {
    private ConnectivityManager cm;
    private NetworkInfo ni;
    private HttpConnection httpConnection;

    public NetworkController(MainActivity mainActivity){
        this.cm = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.ni = cm.getActiveNetworkInfo();
        this.httpConnection = new HttpConnection("http://hezo25.com/get_json.php");
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

    public void disconnect(){
        httpConnection.disconnect();
    }

    public HttpConnection getHttpConnection() {
        return httpConnection;
    }
}
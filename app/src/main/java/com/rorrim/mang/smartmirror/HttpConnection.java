package com.rorrim.mang.smartmirror;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HttpConnection extends Thread{

    private final int TIME_OUT = 20;
    private final String GET_METHOD = "GET";
    private String addr;
    private HttpURLConnection conn;
    private Data data;

    public HttpConnection(String addr){
            this.addr = addr;
    }

    @Override
    public void run(){
        reqHttp();
    }

    private void reqHttp(){
        try {
            URL url = new URL(addr);
            conn = (HttpURLConnection) url.openConnection();
            setRequest();

            conn.connect();

            int responseCode = conn.getResponseCode();

            if(responseCode < 200 || responseCode >= 300){
                throw new HttpConnectionException();
            }

        }catch(IOException e){
            e.printStackTrace();
        }catch(HttpConnectionException e){
            e.getMessage();
        }
    }

    private void setRequest(){
        // Connect Time out
        conn.setConnectTimeout(TIME_OUT * 1000);

        // Read Time out
        conn.setReadTimeout(TIME_OUT * 1000);

        // Choose Method
        try {
            conn.setRequestMethod(GET_METHOD);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    private String getMsg(){
        try {
            InputStream is = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            /*BufferedReader를 Close 하니까 2번째 접근시에 java.io.IOException: closed 발생 */
            //bufferedReader.close();
            return sb.toString().trim();

        }catch (IOException e){
            e.getStackTrace();
        }
        return null;
    }

    /* 나중에 JsonParser Class 만들어야 할 듯 */
    private Data parseJson(String jsonStr){
        try{
            JSONObject json = new JSONObject(jsonStr);
            data = new Data(json.getString("status"), json.getString("result"));
        }catch(JSONException e){
            e.getStackTrace();
        }
        return data;
    }

    public void disconnect(){
        conn.disconnect();
    }

    public void setData(){
        this.data = parseJson(getMsg());
    }

    public Data getData() {
        return data;
    }

}

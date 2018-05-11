package com.rorrim.mang.smartmirror.Model;

import android.net.Uri;
import android.util.Log;

import com.rorrim.mang.smartmirror.Exception.HttpConnectionException;
import com.rorrim.mang.smartmirror.Interface.Connectable;
import com.rorrim.mang.smartmirror.Model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.LinkedList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpConnection extends Thread implements Connectable {

    private static HttpConnection instance;
    private LinkedList<Data> dataList;

    public static HttpConnection getInstance(){
        if (instance == null) {
            synchronized (HttpConnection.class) {
                if (instance == null) {
                    instance = new HttpConnection();
                }
            }
        }
        return instance;
    }

    private HttpConnection(){
        try {
            HttpsURLConnection
                    .setDefaultHostnameVerifier(new NullHostNameVerifier());
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public HttpURLConnection createConnection(String url, String method) throws IOException {
        String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
        HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl).openConnection();
        conn.connect();
        setRequest(conn, method);
        int responseCode = conn.getResponseCode();

        if(responseCode < 200 || responseCode >= 300){
            try {
                throw new HttpConnectionException();
            } catch (HttpConnectionException e1) {
                e1.printStackTrace();
            }
        }

        return conn;
    }

    public InputStream getInputStream(String url) {
        InputStream is = null;

        try {
            HttpURLConnection conn = createConnection(url, GET_METHOD);
            is = conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    public OutputStream getOutputStream(HttpURLConnection conn, String url){
        OutputStream os = null;

        try{
            conn = createConnection(url, POST_METHOD);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            os = conn.getOutputStream();
        }catch(Exception e){
            e.printStackTrace();
        }
        return os;
    }

    private void setRequest(HttpURLConnection conn, String method){
        // Connect Time out
        conn.setConnectTimeout(connectTimeout);

        // Read Time out
        conn.setReadTimeout(readTimeout);

        // Choose Method
        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    private String getMsg(String url){
        try {
            InputStream is = getInputStream(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            //bufferedReader.close();
            return sb.toString().trim();

        }catch (IOException e){
            e.getStackTrace();
        }
        return null;
    }

    public String sendMsg(String url, String params) {
        String result = null;
        OutputStream os = null;
        InputStream is = null;
        BufferedReader br = null;
        HttpURLConnection conn = null;
        try {
            if (params != null) {
                os = getOutputStream(conn, url);
                DataOutputStream dos = new DataOutputStream(os);
                dos.write(params.getBytes(CHARSET));
                dos.flush();
                dos.close();
            }

            if(conn != null){
                // conn.setRequestProperty(field, newValue);//header
                conn.setRequestProperty("Content-Type", "application/json; charset=" + CHARSET);
                is = conn.getInputStream();
            }

            br = new BufferedReader(new InputStreamReader(is, CHARSET));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
            }
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }

        return result;
    }

    /* 나중에 JsonParser Class 만들어야 할 듯 */
    private LinkedList<Data> parseJson(String jsonStr){

        LinkedList<Data> dataList = new LinkedList<>();

        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dataList.add(new Data(jsonObject.getString("name"), jsonObject.getString("models")));
            }

        }catch(JSONException e){
            e.getStackTrace();
        }
        return dataList;
    }

    public void setDataList(){
        this.dataList = parseJson(getMsg("http://hezo25.com/get_json.php"));
    }

    public LinkedList<Data> getDataList() {
        return dataList;
    }

    /** Before Create Connection with server, verify whether it is trusty using x.509 certification**/
    private class NullHostNameVerifier implements HostnameVerifier {
        public NullHostNameVerifier() {

        }

        public boolean verify(String hostname, SSLSession session) {
            Log.i("RestUtilImpl", "Approving certificate for " + hostname);
            return true;
        }
    }

    private TrustManager[] trustAllCerts = { new X509TrustManager() {

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {

        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {

        }
    } };

}

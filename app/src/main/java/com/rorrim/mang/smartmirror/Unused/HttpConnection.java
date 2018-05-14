package com.rorrim.mang.smartmirror.Unused;

import android.net.Uri;
import android.util.Log;

import com.rorrim.mang.smartmirror.Exception.HttpConnectionException;
import com.rorrim.mang.smartmirror.Interface.Connectable;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpConnection implements Connectable {

    private String url;
    private static HttpConnection instance;
    private HttpURLConnection conn;

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

    public static HttpConnection getInstance(String url){
        if (instance == null) {
            synchronized (HttpConnection.class) {
                if (instance == null) {
                    instance = new HttpConnection();
                }
            }
        }
        instance.setUrl(url);
        return instance;
    }
    /*
    @Override
    public void run(){
        try {
            createConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    public void createConnection() throws IOException {
        String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
        this.conn = (HttpURLConnection) new URL(encodedUrl).openConnection();

        setRequest();

    }
    public InputStream getInputStream() {
        InputStream is = null;

        try {
            createConnection();
            conn.setRequestMethod(GET_METHOD);
            conn.connect();

            int responseCode = conn.getResponseCode();

            if(responseCode < 200 || responseCode >= 300){
                try {
                    throw new HttpConnectionException();
                } catch (HttpConnectionException e) {
                    e.printStackTrace();
                }
            }

            is = conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return is;
    }

    public OutputStream getOutputStream(){
        OutputStream os = null;

        try{
            createConnection();
            // conn.setRequestProperty(field, newValue);//header
            conn.setRequestMethod(POST_METHOD);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json; charset=" + CHARSET);

            /*
            conn.connect();

            int responseCode = conn.getResponseCode();

            if(responseCode < 200 || responseCode >= 300){
                try {
                    throw new HttpConnectionException();
                } catch (HttpConnectionException e) {
                    e.printStackTrace();
                }
            }
            */


            os = conn.getOutputStream();
        }catch(Exception e){
            e.printStackTrace();
        }
        return os;
    }

    private void setRequest(){
        // Connect Time out
        conn.setConnectTimeout(connectTimeout);

        // Read Time out
        conn.setReadTimeout(readTimeout);

    }

    public String getJson(){
        InputStream is = null;
        try {
                is = getInputStream();

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
        }finally {
            conn.disconnect();
        }
        return null;
    }

    public String sendJson(String jsonStr) {
        String result = "Error in sendJson";
        OutputStream os = null;
        InputStream is = null;
        BufferedReader br = null;

        try {
            if(jsonStr != null){

                os = getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.write(jsonStr.getBytes(CHARSET));
                dos.flush();
                dos.close();
                is = conn.getInputStream();
                if(is != null) {
                    result = inputStreamToString(is);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //conn.disconnect();
        }

        return result;
    }

    public void setUrl(String url){
        this.url = url;
    }

    private String inputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
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

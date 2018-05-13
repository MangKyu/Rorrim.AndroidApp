package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rorrim.mang.smartmirror.Controller.HttpConnection;
import com.rorrim.mang.smartmirror.Model.User;
import com.rorrim.mang.smartmirror.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    private HttpConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        conn = HttpConnection.getInstance("http://localhost:8080/login_android");
        //conn.start();
        new Thread(){
            public void run(){
                login();
            }
        }.start();
        //Intent intent = new Intent(LoginActivity.this, LoadingActivity.class);
        //startActivity(intent);
    }

    private void login(){
        User user = new User("1", "1", 1);
        String jsonStr;
        String sendPostResult;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("id", user.getId());
            jsonObject.accumulate("pw", user.getPw());
            jsonObject.accumulate("money", user.getMoney());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonStr = jsonObject.toString();

        sendPostResult = conn.sendJson(jsonStr);
        JSONArray json = null;
        try {
            json = new JSONArray(sendPostResult);
            Log.i("LoginActivity", json.toString(1));
            //Button btn = findViewById(R.id.login_btn);
            //btn.setText(json.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.login_btn:
                login();
                break;
            default:
                break;
        }

        if(intent != null){
            startActivity(intent);
        }

    }
}
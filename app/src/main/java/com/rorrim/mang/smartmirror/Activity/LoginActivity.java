package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rorrim.mang.smartmirror.Auth.AuthManager;
import com.rorrim.mang.smartmirror.R;

public class LoginActivity extends Activity {
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    /*
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
    */

    /*
    private Intent login(String id){    //RetrorifService Interface 생성
        RetrofitService retrofitService = RetrofitClient.getInstance().getRetrofit().create(RetrofitService.class);

        //
        Call<User> call = retrofitService.getUser(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // you  will get the reponse in the response parameter
                if(response.isSuccessful()) {
                    //mAdapter.updateAnswers(response.body().getItems());
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Login Activity", "error loading from login");
            }
        });


        return null;
    }

    final String[] myStr = new String[1];

        RetrofitService retrofitService = RetrofitClient.getInstance().getRetrofit().create(RetrofitService.class);

        Call<LinkedList<Data>> call = retrofitService.getData();

        call.enqueue(new Callback <LinkedList<Data>>() {
            @Override
            public void onResponse(Call <LinkedList<Data>> call, Response<LinkedList<Data>> response) {
                // you  will get the reponse in the response parameter
                if(response.isSuccessful()) {
                    LinkedList<Data> data = response.body();
                    Button btn = findViewById(R.id.login_login_btn);
                    btn.setText(data.get(0).toString());
                    //mAdapter.updateAnswers(response.body().getItems());
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code

                }
            }

            @Override
            public void onFailure(Call <LinkedList<Data>> call, Throwable t) {
                Log.d("Login Activity", "error loading from login");
            }
        });
        return myStr[0];

    */

    private void signUp(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void signIn(){
        AuthManager.getInstance().emailSignIn(this, "admin@gmail.com", "admin1234");
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_signIn_btn:
                signIn();
                break;
                /*
            case R.id.login_signUp_btn:
                signUp();
                break;
            */default:
                break;
        }
    }

}
package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rorrim.mang.smartmirror.Auth.AuthManager;
import com.rorrim.mang.smartmirror.Data.DataManager;
import com.rorrim.mang.smartmirror.Network.RetrofitClient;
import com.rorrim.mang.smartmirror.Network.RetrofitService;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityAlarmBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmActivity extends Activity {
    private ActivityAlarmBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm);
        binding.setActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.alarmMenuLayout.setSwitch();
    }

    public void sendCategoryName()   {
        sendCategory(AuthManager.getInstance().getUser().getUid(), binding.alarmCtgSpinner.getSelectedItem().toString());
    }

    private void sendCategory(String uid, String category) {
        RetrofitService retrofitService = RetrofitClient.getInstance().getRetrofit().create(RetrofitService.class);
        Call<ResponseBody> call = retrofitService.sendCategory(uid, category);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // you  will get the reponse in the response parameter
                if (response.isSuccessful()) {

                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MenuView", "Send Switch Status Failed");
            }
        });
    }
}
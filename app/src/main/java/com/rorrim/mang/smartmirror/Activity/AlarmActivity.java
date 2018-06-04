package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.Object;

import com.rorrim.mang.smartmirror.Auth.AuthManager;
import com.rorrim.mang.smartmirror.Network.RetrofitClient;
import com.rorrim.mang.smartmirror.Network.RetrofitService;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityAlarmBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmActivity extends Activity implements View.OnClickListener {
    private ActivityAlarmBinding binding;
    private String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm);
        binding.transBtn.setOnClickListener(this);
        Switch sw = findViewById(R.id.menu_switch);
        sw.setChecked(getState());

        Spinner s = binding.cateSpinner;
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v)   {
        sendCatergory(AuthManager.getInstance().getUser().getUid(), category);
    }
    public Context getContext() {
        return getApplication().getApplicationContext();
    }

    public Boolean getState() {
        boolean temp;
        SharedPreferences prefs = getSharedPreferences("AlarmState", MODE_PRIVATE);
        temp = prefs.getBoolean("myState", false);
        return temp;
    }

    private void sendCatergory(final String uid, final String category) {
        RetrofitService retrofitService = RetrofitClient.getInstance().getRetrofit().create(RetrofitService.class);

        //
        Call<ResponseBody> call = retrofitService.sendCategory(uid, category);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // you  will get the reponse in the response parameter
                if (response.isSuccessful()) {
                    Log.d("aaaaaaaa", category);
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
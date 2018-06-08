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
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.rorrim.mang.smartmirror.Auth.AuthManager;
import com.rorrim.mang.smartmirror.Data.DataManager;
import com.rorrim.mang.smartmirror.Network.RetrofitClient;
import com.rorrim.mang.smartmirror.Network.RetrofitService;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityNewsBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends Activity {
    private ActivityNewsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        binding.setActivity(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.newsMenuLayout.setSwitch();

    }

    public void sendCategoryName()   {
        if(binding.upRadioGroup.getCheckedRadioButtonId() == -1 && binding.downRadioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "관심분야를 하나 선택해주세요!", Toast.LENGTH_SHORT).show();
        }else if(binding.upRadioGroup.getCheckedRadioButtonId() != -1){
            RadioButton temp = (RadioButton) findViewById(binding.upRadioGroup.getCheckedRadioButtonId());
            sendCategory(AuthManager.getInstance().getUser().getUid(),temp.getText().toString());
            Toast.makeText(this,temp.getText().toString() + "를 선택하였습니다", Toast.LENGTH_SHORT).show();
        }else if(binding.downRadioGroup.getCheckedRadioButtonId() != -1){
            RadioButton temp = (RadioButton) findViewById(binding.downRadioGroup.getCheckedRadioButtonId());
            sendCategory(AuthManager.getInstance().getUser().getUid(), temp.getText().toString());
            Toast.makeText(this,temp.getText().toString() + "를 선택하였습니다", Toast.LENGTH_SHORT).show();
        }
    }

    public void downClear(){
        if(binding.downRadioGroup.getCheckedRadioButtonId() != -1)
            binding.downRadioGroup.clearCheck();
    }

    public void upClear(){
        if(binding.upRadioGroup.getCheckedRadioButtonId() != -1)
            binding.upRadioGroup.clearCheck();
    }

    private void sendCategory(String uid, String category) {
        RetrofitService retrofitService = RetrofitClient.getInstance().getRetrofit().create(RetrofitService.class);

        String mirrorUid = AuthManager.getInstance().getUser().getMirrorUid();
        Call<ResponseBody> call = retrofitService.sendCategory(uid, category, mirrorUid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // you  will get the reponse in the response parameter
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Success set category.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("NewsActivity", "Send Category Failed");
            }
        });
    }
}

package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityWeatherBinding;

public class WeatherActivity extends Activity {
    private ActivityWeatherBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
    }
}

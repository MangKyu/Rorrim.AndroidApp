package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.rorrim.mang.smartmirror.Data.DataManager;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityWeatherBinding;

public class WeatherActivity extends Activity implements View.OnClickListener {
    private ActivityWeatherBinding binding;
    public static int On = R.drawable.on_weather;
    public static int Off = R.drawable.off_weather;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        binding.weatherMenuLayout.setSwitch();
        Switch sw = findViewById(R.id.menu_switch);
        sw.setOnClickListener(this);
        setImage();
    }

    @Override
    public void onClick(View v)   {
        setImage();
    }

    public void setImage()  {
        Switch sw = findViewById(R.id.menu_switch);
        if(sw.isChecked())  {
            binding.wOn.setVisibility(View.VISIBLE);
            binding.wOff.setVisibility(View.INVISIBLE);
        }
        else    {
            binding.wOn.setVisibility(View.INVISIBLE);
            binding.wOff.setVisibility(View.VISIBLE);
        }
    }
}

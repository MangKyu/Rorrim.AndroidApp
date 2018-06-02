package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityWeatherBinding;

public class WeatherActivity extends Activity {
    private ActivityWeatherBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        Switch sw = findViewById(R.id.menu_switch);
        sw.setChecked(getState());
        if(sw.isChecked())  {
            binding.onWeather.setVisibility(View.VISIBLE);
        }
        else    {
            binding.offWeather.setVisibility(View.VISIBLE);

        }
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)   {
                    binding.onWeather.setVisibility(View.VISIBLE);
                    binding.onWeatherTxt.setVisibility(View.VISIBLE);
                    binding.offWeather.setVisibility(View.INVISIBLE);
                    binding.offWeatherTxt.setVisibility(View.INVISIBLE);

                }
                else    {
                    binding.offWeather.setVisibility(View.VISIBLE);
                    binding.offWeatherTxt.setVisibility(View.VISIBLE);
                    binding.onWeather.setVisibility(View.INVISIBLE);
                    binding.onWeatherTxt.setVisibility(View.INVISIBLE);

                }
            }
        });
    }

    public Boolean getState()   {
        boolean temp;
        SharedPreferences prefs = getSharedPreferences("State", MODE_PRIVATE);
        temp = prefs.getBoolean("myState", false);
        return temp;
    }

    public void onStop()  {
        super.onStop();
        saveState();
    }

    public void saveState() {
        SharedPreferences prefs = getSharedPreferences("State", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Switch sw = findViewById(R.id.menu_switch);
        editor.putBoolean("myState", sw.isChecked());
        editor.commit();
    }
}

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
import com.rorrim.mang.smartmirror.databinding.ActivityPathBinding;

public class PathActivity extends Activity implements View.OnClickListener  {
    private ActivityPathBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_path);
        Switch sw = findViewById(R.id.menu_switch);
        sw.setChecked(getState());
        sw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)   {
        setImage();
    }

    public Boolean getState() {
        boolean temp;
        SharedPreferences prefs = getSharedPreferences("Activity.PathActivity", MODE_PRIVATE);
        temp = prefs.getBoolean("myState", false);
        return temp;
    }

    public void setImage()  {
        Switch sw = findViewById(R.id.menu_switch);
        if(sw.isChecked())  {
        }
        else    {
        }
    }
}
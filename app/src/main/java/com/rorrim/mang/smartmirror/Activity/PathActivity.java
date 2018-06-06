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

import com.rorrim.mang.smartmirror.Data.DataManager;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityPathBinding;

public class PathActivity extends Activity implements View.OnClickListener{
    private ActivityPathBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_path);
        binding.pathMenuLayout.setSwitch();
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
            binding.pOn.setVisibility(View.VISIBLE);
            binding.pOff.setVisibility(View.INVISIBLE);
        }
        else    {
            binding.pOn.setVisibility(View.INVISIBLE);
            binding.pOff.setVisibility(View.VISIBLE);
        }
    }

}
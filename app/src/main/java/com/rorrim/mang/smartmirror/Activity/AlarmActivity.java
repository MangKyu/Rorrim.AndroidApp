package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import java.lang.Object;

import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityAlarmBinding;

public class AlarmActivity extends Activity {
    private ActivityAlarmBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm);
        Switch sw = findViewById(R.id.menu_switch);
        sw.setChecked(getState());
    }
    public Context getContext() {
        return getApplication().getApplicationContext();
    }
    public Boolean getState()   {
        boolean temp;
        SharedPreferences prefs = getSharedPreferences("AlarmState", MODE_PRIVATE);
        temp = prefs.getBoolean("myState", false);
        return temp;
    }

    public void onStop()  {
        super.onStop();
        saveState();
    }

    public void saveState() {
        SharedPreferences prefs = getSharedPreferences("AlarmState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Switch sw = findViewById(R.id.menu_switch);
        editor.putBoolean("myState", sw.isChecked());
        editor.commit();
    }
}

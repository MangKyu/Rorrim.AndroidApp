package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import java.lang.Object;
import java.net.URI;

import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityAlarmBinding;

import static android.support.constraint.Constraints.TAG;

public class AlarmActivity extends Activity {
    private ActivityAlarmBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm);
        getAlarms();
    }

    private void getAlarms() {
        AlarmManager alarmManager = ( AlarmManager)getSystemService(ALARM_SERVICE);
        for (AlarmManager.AlarmClockInfo aci = alarmManager.getNextAlarmClock();
             aci != null;
             aci = alarmManager.getNextAlarmClock()) {
            Log.d(TAG, aci.getShowIntent().toString());
            Log.d(TAG, String.format("Trigger time: %d", aci.getTriggerTime()));
        }
    }

/*
    public void getAlarm(){
        RingtoneManager.TYPE_NOTIFICATION
        RingtoneManager.TYPE_RINGTONE
        RingtoneManager.TYPE_ALARM

        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_ALARM);

        Log.d(TAG, "currentnotification : " + uri);

        if(uri != null ){
            Cursor c = getContentResolver().query(uri, null, null, null, null);
            c.moveToFirst();
            String title = c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE));
            Log.d(TAG, "ringtone title : " + title);
        }
        else {
            Log.d(TAG, "무음 또는 설정되지 않았음");
        }
    }*/
}

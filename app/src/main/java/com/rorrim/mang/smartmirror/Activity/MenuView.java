package com.rorrim.mang.smartmirror.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.rorrim.mang.smartmirror.Network.RetrofitClient;
import com.rorrim.mang.smartmirror.Network.RetrofitService;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityWeatherBinding;
import com.rorrim.mang.smartmirror.databinding.MenuLayoutBinding;

import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MenuView extends RelativeLayout implements CompoundButton.OnCheckedChangeListener{
    //private RelativeLayout menuLayout;
    private MenuLayoutBinding binding;

    public MenuView(Context context) {
        super(context);
        initView(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.menu_layout, this, true);
        binding.setActivity(this);
        binding.menuSwitch.setOnCheckedChangeListener(this);
    }

    public String getActivityName()    {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = manager.getRunningTasks(1);
        ComponentName cn = info.get(0).topActivity;
        return cn.getShortClassName().substring(1);
    }

    public void gotoMyPage(){
        String activityName = getActivityName();
        if(!activityName.equals("Activity.MyPageActivity")) {
            Intent intent = new Intent(getContext(), MyPageActivity.class);
            getContext().startActivity(intent);
        }
    }

    public void gotoAlarm(){
        String activityName = getActivityName();
        if(!activityName.equals("Activity.AlarmActivity")) {
            Intent intent = new Intent(getContext(), AlarmActivity.class);
            getContext().startActivity(intent);
        }
    }
    public void gotoCalendar(){
        String activityName = getActivityName();
        if(!activityName.equals("Activity.CalendarActivity")) {
            Intent intent = new Intent(getContext(), CalendarActivity.class);
            getContext().startActivity(intent);
        }
    }
    public void gotoPath(){
        String activityName = getActivityName();
        if(!activityName.equals("Activity.PathActivity")) {
            Intent intent = new Intent(getContext(), PathActivity.class);
            getContext().startActivity(intent);
        }
    }
    public void gotoMusic(){
        String activityName = getActivityName();
        if(!activityName.equals("Activity.MusicActivity")) {
            Intent intent = new Intent(getContext(), MusicActivity.class);
            getContext().startActivity(intent);
        }
    }
    public void gotoWeather(){
        String activityName = getActivityName();
        if(!activityName.equals("Activity.WeatherActivity")) {
            Intent intent = new Intent(getContext(), WeatherActivity.class);
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String activityName = getActivityName();
        sendSwitchStatus(activityName, isChecked);

    }

    public void saveWeatherState()  {
        SharedPreferences prefs = getContext().getSharedPreferences("WeatherState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("myState", binding.menuSwitch.isChecked());
        editor.commit();
    }
    public void savePathState() {
        SharedPreferences prefs = getContext().getSharedPreferences("PathState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("myState", binding.menuSwitch.isChecked());
        editor.commit();
    }
    public void saveCalendarState() {
        SharedPreferences prefs = getContext().getSharedPreferences("CalendarState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("myState", binding.menuSwitch.isChecked());
        editor.commit();
    }
    public void saveAlarmState()    {
        SharedPreferences prefs = getContext().getSharedPreferences("AlarmState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("myState", binding.menuSwitch.isChecked());
        editor.commit();
    }
    public void saveMusicState()    {
        SharedPreferences prefs = getContext().getSharedPreferences("MusicState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("myState", binding.menuSwitch.isChecked());
        editor.commit();
    }

    private void sendSwitchStatus(final String activityName, boolean isChecked) {
        RetrofitService retrofitService = RetrofitClient.getInstance().getRetrofit().create(RetrofitService.class);

        //
        Call<ResponseBody> call = retrofitService.sendSwitchStatus(activityName, isChecked);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // you  will get the reponse in the response parameter
                if(response.isSuccessful()) {
                    switch(activityName)    {
                        case "Activity.AlarmActivity":
                            saveAlarmState();
                            break;
                        case "Activity.CalendarActivity":
                            saveCalendarState();
                            break;
                        case "Activity.PathActivity":
                            savePathState();
                            break;
                        case "Activity.MusicActivity":
                            saveMusicState();
                            break;
                        case "Activity.WeatherActivity":
                            saveWeatherState();
                            break;
                        default:
                            break;
                    }
                }else {
                    int statusCode  = response.code();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("MenuView", "Send Switch Status Failed");
            }
        });
    }
}


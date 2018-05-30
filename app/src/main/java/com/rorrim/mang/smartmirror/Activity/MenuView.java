package com.rorrim.mang.smartmirror.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.MenuLayoutBinding;

public class MenuView extends RelativeLayout {
    //private RelativeLayout menuLayout;
    private MenuLayoutBinding binding;

    public MenuView(Context context) {
        super(context);
        initView();
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //String infService = Context.LAYOUT_INFLATER_SERVICE;
        //LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        // View v = li.inflate(R.layout.menu_layout, this, false);
        //addView(v);
        inflate(getContext(), R.layout.menu_layout, this);
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = MenuLayoutBinding.inflate(inflater);
        binding.setActivity(this);
        //menuLayout = (RelativeLayout) findViewById(R.id.menu_layout);
    }

    public void showProfile(View view){
        Toast.makeText(view.getContext(), "Show Profile", Toast.LENGTH_SHORT).show();
    }

    public void showMenu(View view){
        Toast.makeText(view.getContext(), "Show Menu", Toast.LENGTH_SHORT).show();
    }

    public void gotoAlarm(View view){
        Intent intent = new Intent(view.getContext(), AlarmActivity.class);
        view.getContext().startActivity(intent);
    }
    public void gotoCalendar(View view){
        Intent intent = new Intent(view.getContext(), CalendarActivity.class);
        view.getContext().startActivity(intent);
    }
    public void gotoPath(View view){
        Intent intent = new Intent(view.getContext(), PathActivity.class);
        view.getContext().startActivity(intent);
    }
    public void gotoMusic(View view){
        Intent intent = new Intent(view.getContext(), MusicActivity.class);
        view.getContext().startActivity(intent);
    }
    public void gotoWeather(View view){
        Intent intent = new Intent(view.getContext(), WeatherActivity.class);
        view.getContext().startActivity(intent);
    }

}


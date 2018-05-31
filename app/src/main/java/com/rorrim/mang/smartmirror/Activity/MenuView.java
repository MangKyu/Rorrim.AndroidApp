package com.rorrim.mang.smartmirror.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
        //String infService = Context.LAYOUT_INFLATER_SERVICE;
        //LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        // View v = li.inflate(R.layout.menu_layout, this, false);
        //addView(v);
        //inflate(getContext(), R.layout.menu_layout, this);
        //LayoutInflater inflater = (LayoutInflater)
//                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  //      binding = MenuLayoutBinding.inflate(inflater);
    //    binding.setActivity(this);
        //LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.menu_layout, this, true);
        binding.setActivity(this);
        //menuLayout = (RelativeLayout) findViewById(R.id.menu_layout);
    }

    public void showProfile(View view){
        Intent intent = new Intent(view.getContext(), MyPageActivity.class);
        view.getContext().startActivity(intent);
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


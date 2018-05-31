package com.rorrim.mang.smartmirror.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rorrim.mang.smartmirror.Activity.CalendarActivity;
import com.rorrim.mang.smartmirror.BR;
import com.rorrim.mang.smartmirror.Model.Calendar;

import com.rorrim.mang.smartmirror.Model.Music;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.MusicListLowBinding;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<Calendar.MyViewHolder> {
    private List<Calendar> calendarList;
    private LayoutInflater inflater;
    private Activity activity;

    public CalendarAdapter(){
        this.calendarList = new ArrayList<>();
    }

    public CalendarAdapter(Activity activity, List<Calendar> calendarList) {
        this.calendarList = calendarList;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItem(List<Calendar> calendarList) {
        if (calendarList == null) {
            return;
        }
        this.calendarList = calendarList;
        notifyDataSetChanged();
    }


    @Override
    public CalendarAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MusicListLowBinding binding = MusicListLowBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CalendarAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CalendarAdapter.MyViewHolder holder, final int position) {
        Calendar calendar = calendarList.get(position);
        holder.bind(calendar);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CalendarListLowBinding binding;

        public MyViewHolder(CalendarListLowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Music music){
            binding.setVariable(BR.music, music);
        }

    }

}

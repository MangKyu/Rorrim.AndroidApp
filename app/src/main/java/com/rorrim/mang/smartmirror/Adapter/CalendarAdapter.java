package com.rorrim.mang.smartmirror.Adapter;

import com.google.api.services.calendar.model.CalendarList;
import com.rorrim.mang.smartmirror.BR;
import com.rorrim.mang.smartmirror.Model.Calendar;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.CalendarListLowBinding;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {
    @NonNull
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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarListLowBinding binding = CalendarListLowBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.MyViewHolder holder, int position) {
        Calendar calendar = calendarList.get(position);
        holder.bind(calendar);

    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    public void setItem(List<Calendar> calendarList) {
        if (calendarList == null) {
            return;
        }
        this.calendarList = calendarList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CalendarListLowBinding binding;

        public MyViewHolder(CalendarListLowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Calendar calendar){
            binding.setVariable(BR.calendar, calendar);
        }

    }
}

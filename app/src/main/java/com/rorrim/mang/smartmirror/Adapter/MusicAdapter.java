package com.rorrim.mang.smartmirror.Adapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rorrim.mang.smartmirror.BR;
import com.rorrim.mang.smartmirror.Model.Music;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.MusicListLowBinding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private static final BitmapFactory.Options options = new BitmapFactory.Options();
    private List<Music> musicList;
    private LayoutInflater inflater;
    private Activity activity;

    public MusicAdapter(){
        this.musicList = new ArrayList<>();
    }


    public MusicAdapter(Activity activity, List<Music> musicList) {
        this.musicList = musicList;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MusicListLowBinding binding = MusicListLowBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MusicAdapter.MyViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.bind(music);
    }

    public void setItem(List<Music> musicList) {
        if (musicList == null) {
            return;
        }
        this.musicList = musicList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private MusicListLowBinding binding;

        public MyViewHolder(MusicListLowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Music music){
            binding.setVariable(BR.music, music);
        }
    }

}
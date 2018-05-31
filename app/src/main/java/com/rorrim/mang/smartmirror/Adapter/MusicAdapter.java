package com.rorrim.mang.smartmirror.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.rorrim.mang.smartmirror.Activity.MusicActivity;
import com.rorrim.mang.smartmirror.Auth.AuthManager;
import com.rorrim.mang.smartmirror.BR;
import com.rorrim.mang.smartmirror.File.FileManager;
import com.rorrim.mang.smartmirror.Model.Music;
import com.rorrim.mang.smartmirror.Network.RetrofitClient;
import com.rorrim.mang.smartmirror.Network.RetrofitService;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.MusicListLowBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
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
    public void onBindViewHolder(MusicAdapter.MyViewHolder holder,final int position) {
        Music music = musicList.get(position);
        holder.bind(music);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    FileManager.getInstance().uploadMusic(view.getContext(), musicList.get(position));
                    //SendMusicTask sendMusicTask = new SendMusicTask();
                    //sendMusicTask.execute(position);
                }
                catch (Exception e) {
                    Log.e("SimplePlayer", e.getMessage());
                }
            }
        });

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

    private class SendMusicTask extends AsyncTask<Integer, Void, Void> {

        private ProgressDialog asyncDialog = new ProgressDialog(activity);
        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            int position = params[0];
            FileManager.getInstance().uploadMusic(activity, musicList.get(position));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            asyncDialog.dismiss();
        }
    }

}
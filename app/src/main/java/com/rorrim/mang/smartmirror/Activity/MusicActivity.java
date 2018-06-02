package com.rorrim.mang.smartmirror.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import com.rorrim.mang.smartmirror.Adapter.MusicAdapter;
import com.rorrim.mang.smartmirror.File.FileManager;
import com.rorrim.mang.smartmirror.Listener.RecyclerViewClickListener;
import com.rorrim.mang.smartmirror.Model.Music;
import com.rorrim.mang.smartmirror.R;
import com.rorrim.mang.smartmirror.databinding.ActivityMusicBinding;


public class MusicActivity extends Activity {

    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private ActivityMusicBinding binding;
    private ObservableArrayList<Music> musicList;
    private MusicAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music);
        Switch sw = findViewById(R.id.menu_switch);
        sw.setChecked(getState());
        musicList = new ObservableArrayList<>();

        mAdapter = new MusicAdapter(this, musicList, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(Music music) {
                sendMusicFile(music);
            }
        });
        binding.musicMusicRv.setAdapter(mAdapter);
        binding.setMusicList(musicList);
        requestMusicList();
    }

    public void sendMusicFile(final Music music){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_NEGATIVE: {
                        //Yes 버튼을 클릭했을때 처리
                        try {
                            FileManager.getInstance().uploadMusic(getContext(), music);
                        }
                        catch (Exception e) {
                            Log.e("SimplePlayer", e.getMessage());
                        }
                        break;
                    }
                    case DialogInterface.BUTTON_POSITIVE:
                        //No 버튼을 클릭했을때 처리
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("정말?").setPositiveButton("아니?", dialogClickListener)
                .setNegativeButton("그래!", dialogClickListener).show();

    }

    public void sendMusic(String albumId){
        Toast.makeText(MusicActivity.this, albumId,
                Toast.LENGTH_SHORT).show();
    }


    private void requestMusicList(){
        int permissionReadStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionReadStorage == PackageManager.PERMISSION_DENIED || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        } else {
            getMusicList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            getMusicList();
                        } else {
                            Toast.makeText(MusicActivity.this, "Permission Denined.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    private void getMusicList(){
        //가져오고 싶은 컬럼 명을 나열합니다. 음악의 아이디, 앰블럼 아이디, 제목, 아스티스트 정보를 가져옵니다.
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        while(cursor.moveToNext()){
            Music music = new Music();
            music.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            music.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            music.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            music.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            musicList.add(music);
        }
        cursor.close();
    }

    public Boolean getState()   {
        boolean temp;
        SharedPreferences prefs = getSharedPreferences("MusicState", MODE_PRIVATE);
        temp = prefs.getBoolean("myState", false);
        return temp;
    }

    public void onStop()  {
        super.onStop();
        saveState();
    }

    public void saveState() {
        SharedPreferences prefs = getSharedPreferences("MusicState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Switch sw = findViewById(R.id.menu_switch);
        editor.putBoolean("myState", sw.isChecked());
        editor.commit();
    }

    public Context getContext(){
        return this;
    }

}

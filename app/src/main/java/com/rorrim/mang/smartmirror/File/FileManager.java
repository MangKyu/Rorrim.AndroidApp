package com.rorrim.mang.smartmirror.File;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rorrim.mang.smartmirror.Auth.AuthManager;
import com.rorrim.mang.smartmirror.Data.DataManager;
import com.rorrim.mang.smartmirror.Model.Music;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileManager {
    private FirebaseStorage storage;
    private StorageReference musicRef;
    private static FileManager instance;

    public FileManager(){
        storage = FirebaseStorage.getInstance("gs://smartmirror-75b89.appspot.com");
        //musicRef = storage.getReference().child("Audio/");//.child("Audio"); //
    }

    public static FileManager getInstance() {
        if(instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    private StorageReference getReference(String uid, String fileName){
        musicRef = storage.getReference().child(uid+"/"+fileName);
        return musicRef;
    }

    public void uploadMusic(final Context context,final Music music){

        String id = music.getId();
        Uri musicURI = Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ""+id);
        //String filePath = getRealPathFromURI(context, musicURI);
        String fileName = music.getArtist()+ "- "+music.getTitle();
        //File file = new File(filePath);
        //String fileName = filePath.substring(filePath.lastIndexOf('/'));



        //String filePath = getRealPathFromURI(context, musicURI);
        //File file = new File(filePath);
        try {
            //InputStream stream = new FileInputStream(file);
            //UploadTask uploadTask = musicRef.putStream(stream);
            UploadTask uploadTask = getReference(AuthManager.getInstance().getUser().getUid(), fileName).putFile(musicURI);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //Toast.makeText(context, "File Manager Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    DataManager.getInstance().uploadAudio(music.getArtist(), music.getTitle());
                    //Toast.makeText(context, "File Manager Success", Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            //Toast.makeText(context, "File Manager" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

}

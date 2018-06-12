package com.rorrim.mang.smartmirror.File;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rorrim.mang.smartmirror.Activity.MyPageActivity;
import com.rorrim.mang.smartmirror.Auth.AuthManager;
import com.rorrim.mang.smartmirror.Data.DataManager;
import com.rorrim.mang.smartmirror.Model.Music;
import com.rorrim.mang.smartmirror.Network.RetrofitClient;
import com.rorrim.mang.smartmirror.Network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileManager {
    private FirebaseStorage storage;
    private StorageReference musicRef;
    private static FileManager instance;

    public FileManager(){
        storage = FirebaseStorage.getInstance("gs://smartmirror-75b89.appspot.com");
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
        String filePath = getRealPathFromURI(context, musicURI);
        String fileExt = filePath.substring(filePath.lastIndexOf("."));
        final String fileName = music.getArtist()+ "- "+music.getTitle() + fileExt;
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Sending File...");

        try {
            UploadTask uploadTask = getReference(AuthManager.getInstance().getUser().getUid(), fileName).putFile(musicURI);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.show();
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    //System.out.println("Upload is paused");
                }
            });

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    dialog.dismiss();
                    Toast.makeText(context, "File Manager Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    DataManager.getInstance().uploadAudio(music.getArtist(), music.getTitle(), fileName);
                    Toast.makeText(context, "File Manager Success", Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally{
            dialog.dismiss();
        }

    }

    public void sendMusicName(Context context, final Music music, String uid, String mirrorUid)    {
        RetrofitService retrofitService = RetrofitClient.getInstance().getRetrofit().create(RetrofitService.class);
        String id = music.getId();
        Uri musicURI = Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ""+id);
        String filePath = getRealPathFromURI(context, musicURI);
        String fileExt = filePath.substring(filePath.lastIndexOf("."));
        final String fileName = music.getArtist()+ "- "+music.getTitle() + fileExt;
        Call<String> call = retrofitService.pushMusic(uid, mirrorUid, fileName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // you  will get the reponse in the response parameter
                if (response.isSuccessful()) {
                } else {
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Location", "Send Location Failed");
            }
        });

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

package com.rorrim.mang.smartmirror.Data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rorrim.mang.smartmirror.Auth.AuthManager;

import static android.content.Context.MODE_PRIVATE;

public class DataManager {
    private static DataManager instance;
    private DatabaseReference databaseReference;

    public DataManager() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        /*imageReference= databaseReference.child("image").child(AuthManager.
                getInstance().getUser().getUid());
        addImageReferenceListener();*/
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void uploadCalander(String date, String time, String contents) {
        String uid = AuthManager.getInstance().getUser().getUid();
        databaseReference.child("calendar").child(uid).child(date).child(time)
                .setValue(contents);
    }

    public void uploadAudio(String artist, String title, String fileName) {
        String uid = AuthManager.getInstance().getUser().getUid();
        databaseReference.child("audio").child(uid).child(artist).child(title).setValue(fileName);
    }

    public void saveStatus(Context context, String activityName, boolean status){
        SharedPreferences prefs = context.getSharedPreferences(activityName, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(activityName, status);
        //editor.apply();
        editor.commit();
    }

    public Boolean getState(Context context, String activityName) {
        boolean temp;
        SharedPreferences prefs = context.getSharedPreferences(activityName, MODE_PRIVATE);
        temp = prefs.getBoolean(activityName, false);
        return temp;
    }

}

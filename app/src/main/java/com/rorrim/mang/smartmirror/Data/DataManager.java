package com.rorrim.mang.smartmirror.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rorrim.mang.smartmirror.Auth.AuthManager;

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

}

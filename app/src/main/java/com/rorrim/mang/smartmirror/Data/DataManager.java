package com.rorrim.mang.smartmirror.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rorrim.mang.smartmirror.Auth.AuthManager;

import java.util.List;

public class DataManager {
    private static DataManager instance;
    private DatabaseReference databaseReference;

    public DataManager(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public void uploadCalander(List<String> Date, List<String> Time, List<String> Contents) {
        String uid = AuthManager.getInstance().getUser().getUid();
        for(int i = 0; i < Time.size(); i++) {
            databaseReference.child("calendar").child(uid).child(Date.get(i)).child(Time.get(i))
                    .setValue(Contents.get(i));
        }
    }

    public void uploadAudio(String artist, String title){
        String uid = AuthManager.getInstance().getUser().getUid();
        databaseReference.child("audio").child(uid).child(artist).setValue(title);
    }

}

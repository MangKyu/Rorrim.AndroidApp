package com.rorrim.mang.smartmirror.Data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rorrim.mang.smartmirror.Auth.AuthManager;

import static android.support.constraint.Constraints.TAG;

public class DataManager {
    private static DataManager instance;
    private DatabaseReference databaseReference;

    public DataManager(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        /*imageReference= databaseReference.child("image").child(AuthManager.
                getInstance().getUser().getUid());
        addImageReferenceListener();*/
    }

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public void uploadCalander(String date, String time, String contents) {
        String uid = AuthManager.getInstance().getUser().getUid();
        databaseReference.child("calendar").child(uid).child(date).child(time)
                    .setValue(contents);
    }

    public void uploadAudio(String artist, String title, String fileName){
        String uid = AuthManager.getInstance().getUser().getUid();
        databaseReference.child("audio").child(uid).child(artist).child(title).setValue(fileName);
    }


    /*
    public void addImageReferenceListener()  {
        imageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageUrl= new String(dataSnapshot.getValue().toString());
                AuthManager.getInstance().getUser().setProfileUrl(imageUrl);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "onCancelled: " + error.getMessage());
            }
        });
    }
    */
}

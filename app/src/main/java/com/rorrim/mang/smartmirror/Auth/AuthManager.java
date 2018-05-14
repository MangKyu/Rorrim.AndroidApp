package com.rorrim.mang.smartmirror.Auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rorrim.mang.smartmirror.Activity.MainActivity;

public class AuthManager {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private static AuthManager instance;
    private boolean flag;

    public AuthManager(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    public static AuthManager getInstance(){
        if(instance == null){
            instance = new AuthManager();
        }
        return instance;
    }

    public boolean signUp(Activity curActivity, String email, String pw){
        final boolean sFlag = false;
        auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(curActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Auth Manager", "signUpWithEmail:success");
                            //User user = new User(email);
                            //databaseReference.child("users").child(email).setValue(user);
                           // sFlag = true;
                        }
                        //보통 이메일이 이미 존재하거나, 이메일 형식이아니거나, 비밀번호가 6자리 이상이 아닐 때 발생 


                    }
                });
            /*
                .addOnCompleteListener(task->{
            if(task.isSuccessful()){
                FirebaseUser firebaseUser = task.getResult().getUser();
                User user = new User(firebaseUser.getEmail());
                databaseReference.child("users").child(firebaseUser.getUid()).setValue(user);
            }
        })
        .addOnFailureListener(e ->{
        });
*/

        return sFlag;
    }

    public boolean emailSignIn(final Activity curActivity, String email, String pw) {

        auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(curActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(curActivity, MainActivity.class);
                            curActivity.startActivity(intent);
                        }else{
                            Log.d("Auth Manager", "signInWithEmail:fail");
                        }
                    }
                });

        return flag;
    }

    public boolean googleSignIn(){
        return false;
    }

    public FirebaseAuth getAuth(){
        return auth;
    }

    public void signOut(){
        auth.signOut();
    }

}

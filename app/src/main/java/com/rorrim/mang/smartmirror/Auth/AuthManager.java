package com.rorrim.mang.smartmirror.Auth;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rorrim.mang.smartmirror.Interface.Connectable;
import com.rorrim.mang.smartmirror.Model.User;
import com.rorrim.mang.smartmirror.R;

import java.util.List;

public class AuthManager extends Application implements Connectable {
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private static AuthManager instance;
    private FirebaseAuth.AuthStateListener authListener;
    private SharedPreferences sharedPreferences;
    private User user;

    public AuthManager(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //로그인 또는 로그아웃 이후에 실행된다.
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    setUser();
                    //User user = new User(auth.getCurrentUser().getUid(), auth.getCurrentUser().getEmail());
                    //setUser(user);
                }
                //saveUserData();
            }
        };
        auth.addAuthStateListener(authListener);
    }

    public void write(List<String> Date, List<String> Time, List<String> Contents) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String user;
        user = AuthManager.getInstance().getUser().getUid();
        for(int i = 0; i < Time.size(); i++) {
            databaseReference.child("calendar").child(user).child(Date.get(i)).child(Time.get(i))
                    .setValue(Contents.get(i));
        }
    }

    public static AuthManager getInstance(){
        if(instance == null){
            instance = new AuthManager();
        }
        return instance;
    }
    /*
    public void emailSignUp(final Activity curActivity, String email, String pw){
        auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(curActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(curActivity, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Log.d("Auth Manager", "CreateUserWithEmail:fail");
                        }
                    }
                });
    }

    public void emailSignIn(final Activity curActivity, String email, String pw) {

        auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(curActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(curActivity, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Log.d("Auth Manager", "signInWithEmail:fail");
                        }
                    }
                });
    }

    */

    public FirebaseAuth getAuth(){
        return auth;
    }

    public void signOut(){
        auth.signOut();
    }

    private void saveUserData(){
        sharedPreferences = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", user.getEmail());
            editor.putString("phone", user.getPhoneNumber());
            editor.putString("uid", user.getUid());
        }
    }

    public void setUser(){
        User user = new User(auth.getCurrentUser().getUid(), auth.getCurrentUser().getEmail());
        this.user = user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

}

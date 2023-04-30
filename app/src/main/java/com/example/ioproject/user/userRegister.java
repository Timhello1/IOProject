package com.example.ioproject.user;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class userRegister {
    private final FirebaseAuth userRegister;

    public userRegister() {
        this.userRegister = FirebaseAuth.getInstance();
    }


    public void registerUser(String email, String password, Activity activity){

        userRegister.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "LogIn failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

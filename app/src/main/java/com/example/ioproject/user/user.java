package com.example.ioproject.user;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user {
    private final FirebaseAuth userConnection;

    public user() {
        this.userConnection = FirebaseAuth.getInstance();
    }


    public void registerUser(String email, String password, Activity activity) {
        if (!(email.isEmpty() || password.isEmpty())) {
            userConnection.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
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

    public void loginUser(String email, String password, Activity activity) {
        if (!(email.isEmpty() || password.isEmpty())) {
            userConnection.signInWithEmailAndPassword(email, password).
                    addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = userConnection.getCurrentUser();
                                Toast.makeText(activity, "LogIn succesfull", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(activity, "LogIn failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
            ;
        }
    }
}

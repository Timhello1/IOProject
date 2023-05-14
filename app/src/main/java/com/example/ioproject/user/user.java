package com.example.ioproject.user;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ioproject.shop.ShopActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class user {
    private final FirebaseAuth userConnection;
    private static final String TAG = "user";

    public user() {
        this.userConnection = FirebaseAuth.getInstance();
    }


    public void registerUser(String email, String password, String login,String code, Activity activity) {
        if (!(email.isEmpty() || password.isEmpty())) {
            userConnection.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (code.equals("SerialCodeUwU")){
                                        // TODO POPRAWIC
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String, Object> admin = new HashMap<>();
                                    admin.put("email",email);
                                    admin.put("password",password);
                                    admin.put("login",login);
                                    db.collection("admins")
                                            .add(admin)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });
                                }else {
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("email",email);
                                    user.put("password",password);
                                    user.put("login",login);
                                    db.collection("users")
                                            .add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });
                                }
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
                                Intent intent = new Intent(activity, ShopActivity.class);
                                activity.startActivity(intent);
                            } else {
                                Toast.makeText(activity, "LogIn failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
            ;
        }
    }
}

package com.example.ioproject.user;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ioproject.shop.AdminActivity;
import com.example.ioproject.shop.ShopActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

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
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                CollectionReference adminsRef = db.collection("admins");
                                Query query = adminsRef.whereEqualTo("email",email);
                                query.get().addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        for(QueryDocumentSnapshot documentSnapshot : task1.getResult()){
                                            String admin = documentSnapshot.getString("email");

                                            if(admin != null && admin.equals(email)){
                                                Log.d(TAG, "User Exists");
                                                Toast.makeText(activity, "User exists", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(activity, AdminActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                activity.startActivity(intent);
                                            }
                                        }
                                    }
                                });

                                CollectionReference usersRef = db.collection("users");
                                Query query1 = usersRef.whereEqualTo("email",email);
                                query1.get().addOnCompleteListener(task12 -> {
                                    if (task12.isSuccessful()){
                                        for(QueryDocumentSnapshot documentSnapshot : task12.getResult()){
                                            String user = documentSnapshot.getString("email");

                                            if(user != null && user.equals(email)){
                                                Log.d(TAG,"User Exists");
                                                Toast.makeText(activity, "User Exists", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(activity, ShopActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                activity.startActivity(intent);
                                            }
                                        }
                                    }
                                });
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

    public void deleteUser(Activity activity) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid(); // Get the user ID

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Delete user document from 'users' collection
            CollectionReference usersRef = db.collection("users");
            Query userQuery = usersRef.whereEqualTo("email", user.getEmail());
            userQuery.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        documentSnapshot.getReference().delete();
                    }
                }
            });

            // Delete user document from 'admins' collection
            CollectionReference adminsRef = db.collection("admins");
            Query adminQuery = adminsRef.whereEqualTo("email", user.getEmail());
            adminQuery.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        documentSnapshot.getReference().delete();
                    }
                }
            });

            // Delete the Firebase user account
            user.delete()
                    .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(activity, "Account deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, "Account deletion failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}

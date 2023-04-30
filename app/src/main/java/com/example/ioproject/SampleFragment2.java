package com.example.ioproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SampleFragment2 extends Fragment {

    private FirebaseAuth firebaseAuth;
    private EditText emailfield;
    private EditText passwordfield;
    private Button logInButton;
    private TextView errorTextView;

        public SampleFragment2(){

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();

        View root = inflater.inflate(R.layout.fragment_sample2,container,false);

//        emailfield = root.findViewById(R.id.editTextTextPersonName);
//        passwordfield = root.findViewById(R.id.editTextTextPassword);
//        logInButton = root.findViewById(R.id.button);
//        errorTextView = root.findViewById(R.id.editTextTextPersonName4);
//
//        logInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = emailfield.getText().toString().trim();
//                String password = passwordfield.getText().toString().trim();
//
//                firebaseAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                                    Toast.makeText(getActivity(), "LogIn succesfull", Toast.LENGTH_SHORT).show();
//
//                                    // User login successful
//                                } else {
//                                    // User login failed
//                                    Toast.makeText(getActivity(), "LogIn failed", Toast.LENGTH_SHORT).show();
//                                    errorTextView.setText(task.getException().getMessage());
//                                }
//                            }
//                        });
//            }
//        });

        return root;
    }
}
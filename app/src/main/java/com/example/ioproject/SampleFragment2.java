package com.example.ioproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ioproject.shop.ShopActivity;
import com.example.ioproject.user.user;

/**
 * Fragment dla logowania
 */
//TODO ZMIENIĆ NAZWĘ FRAGMENTU
public class SampleFragment2 extends Fragment {

    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;

    public SampleFragment2() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sample2, container, false);

        emailField = root.findViewById(R.id.login_userLoginInput);
        passwordField = root.findViewById(R.id.login_userPasswordInput);
        loginButton = root.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                user user = new user();
                user.loginUser(email, password, getActivity());
            }
        });
        return root;
    }
}
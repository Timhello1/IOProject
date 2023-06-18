package com.example.ioproject.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ioproject.R;
import com.example.ioproject.fragmentsForFutureUse.Basket;
import com.example.ioproject.fragmentsForFutureUse.OrderMake;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        replaceFragment(new HomeFragment());


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        MenuItem shop  = bottomNavigationView.getMenu().findItem(R.id.shop);
        shop.setTitle("Menu Admina");
        MenuItem discounts = bottomNavigationView.getMenu().findItem(R.id.discounts);
        discounts.setTitle("In dev.");
        MenuItem settings = bottomNavigationView.getMenu().findItem(R.id.settings);
        settings.setTitle("In dev.");
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.shop:
                    replaceFragment(new AdminMenu());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
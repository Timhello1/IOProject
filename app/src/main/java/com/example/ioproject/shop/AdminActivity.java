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
        shop.setTitle("Dodaj produkt");
        MenuItem discounts = bottomNavigationView.getMenu().findItem(R.id.discounts);
        discounts.setTitle("Zamówienie");
        MenuItem settings = bottomNavigationView.getMenu().findItem(R.id.settings);
        settings.setTitle("Koszyk");
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new AddOfferFragment());
                    break;
                case R.id.shop:
                    replaceFragment(new AddProductFragment());
                    break;
                case R.id.discounts:
                    replaceFragment(new OrderMake());
                    break;
                case R.id.settings:
                    replaceFragment(new Basket());
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
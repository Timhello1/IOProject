package com.example.ioproject.shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ioproject.R;
import com.example.ioproject.fragmentsForFutureUse.DeleteOffer;
import com.example.ioproject.fragmentsForFutureUse.DeleteProduct;
import com.example.ioproject.fragmentsForFutureUse.UpdateOffer;
import com.example.ioproject.fragmentsForFutureUse.UpdateProduct;

public class AdminMenu extends Fragment {

    public AdminMenu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_menu, container, false);

        Button addProductButton = view.findViewById(R.id.add_product_button);
        Button removeProductButton = view.findViewById(R.id.remove_product_button);
        Button addOfferButton = view.findViewById(R.id.add_offer_button);
        Button removeOfferButton = view.findViewById(R.id.remove_offer_button);
        Button updateProductButton = view.findViewById(R.id.update_product_button);
        Button updateOfferButton = view.findViewById(R.id.update_offer_button);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Przełącz się do innego fragmentu po kliknięciu przycisku "DODAJ PRODUKT"
                Fragment fragment = new AddProductFragment();
                replaceFragment(fragment);
            }
        });

        removeProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Przełącz się do innego fragmentu po kliknięciu przycisku "USUŃ PRODUKT"
                Fragment fragment = new DeleteProduct();
                replaceFragment(fragment);
            }
        });

        addOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Przełącz się do innego fragmentu po kliknięciu przycisku "DODAJ OFERTĘ"
                Fragment fragment = new AddOfferFragment();
                replaceFragment(fragment);
            }
        });

        removeOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Przełącz się do innego fragmentu po kliknięciu przycisku "USUŃ OFERTĘ"
                Fragment fragment = new DeleteOffer();
                replaceFragment(fragment);
            }
        });

        updateProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Przełącz się do innego fragmentu po kliknięciu przycisku "AKTUALIZACJA PRODUKTU"
                Fragment fragment = new UpdateProduct();
                replaceFragment(fragment);
            }
        });

        updateOfferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Przełącz się do innego fragmentu po kliknięciu przycisku "AKTUALIZACJA OFERTY"
                Fragment fragment = new UpdateOffer();
                replaceFragment(fragment);
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}

package com.example.ioproject.shop;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ioproject.R;
import com.example.ioproject.database.DatabaseFetch;
import com.example.ioproject.product.Product;
import com.example.ioproject.product.SpecialOfferProduct;
import com.example.ioproject.utils.CustomItemDecoration;
import com.example.ioproject.utils.FeaturedItemsAdapter;
import com.example.ioproject.utils.SpecialOfferAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fragment przedstawiający stronę startową po przejściu do sklepu.
 */
public class HomeFragment extends Fragment {

    private RecyclerView featuredTypes, specialOffersView;
    private FirebaseFirestore firestore;
    private DatabaseFetch databaseFetch;

    public HomeFragment() {
        databaseFetch = new DatabaseFetch();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void fetchSpecialOffers() {
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("products")
                .whereEqualTo("Type", "Smartfon")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String productId = document.getId();
                            String productName = document.getString("Name");

                            Log.d("Product", "ID: " + productId + ", Name: " + productName);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        CustomItemDecoration itemDecoration = new CustomItemDecoration(50);

        // Wyświetlenie różnych typów produktów
        databaseFetch.fetchProductTypes().addOnSuccessListener(types -> {
            List<Product> productTypes = new ArrayList<>();

            for (String type : types) {
                productTypes.add(new Product(type));
            }

            featuredTypes = view.findViewById(R.id.featured_products_recycler_view);
            featuredTypes.setAdapter(new FeaturedItemsAdapter(productTypes));
            featuredTypes.addItemDecoration(itemDecoration);
        });


        List<SpecialOfferProduct> specialOffers = Arrays.asList(
                new SpecialOfferProduct("iPhone XR", "3600 zł", "2900 zł",
                        R.drawable.image_iphone_commercial),
                new SpecialOfferProduct("Lenovo IdeaPad", "6230 zł", "5400 zł",
                        R.drawable.image_laptop_commercial),
                new SpecialOfferProduct("Marshall KT-800", "679 zł", "439 zł",
                        R.drawable.image_headphones_commercial),
                new SpecialOfferProduct("Nvidia RTX 2080", "1869 zł", "1769 zł",
                        R.drawable.image_nvidia_commercial)
        );


        specialOffersView = view.findViewById(R.id.promotions_recycler_view);
        specialOffersView.setAdapter(new SpecialOfferAdapter(specialOffers));
        specialOffersView.addItemDecoration(itemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        specialOffersView.setLayoutManager(linearLayoutManager);

        fetchSpecialOffers();

        return view;
    }
}
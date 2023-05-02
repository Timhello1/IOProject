package com.example.ioproject.shop;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ioproject.R;
import com.example.ioproject.product.Product;
import com.example.ioproject.product.SpecialOfferProduct;
import com.example.ioproject.utils.CustomItemDecoration;
import com.example.ioproject.utils.FeaturedItemsAdapter;
import com.example.ioproject.utils.SpecialOfferAdapter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

private RecyclerView featuredTypes, specialOffersView;
    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {

        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        List<Product> items = Arrays.asList(
                new Product("PC"),
                new Product("GPU"),
                new Product("Monitors"),
                new Product("Keyboard"),
                new Product("Mice"),
                new Product("Headsets")
        );



        List<SpecialOfferProduct> specialOffers = Arrays.asList(
                new SpecialOfferProduct("iPhone XR","3600 zł", "2900 zł",
                        R.drawable.image_iphone_commercial),
                new SpecialOfferProduct("Lenovo IdeaPad","6230 zł", "5400 zł",
                        R.drawable.image_laptop_commercial),
                new SpecialOfferProduct("Marshall KT-800","679 zł", "439 zł",
                        R.drawable.image_headphones_commercial),
                new SpecialOfferProduct("Nvidia RTX 2080","1869 zł", "1769 zł",
                        R.drawable.image_nvidia_commercial)
        );

        CustomItemDecoration itemDecoration = new CustomItemDecoration(50);

        featuredTypes = view.findViewById(R.id.featured_products_recycler_view);
        featuredTypes.setAdapter(new FeaturedItemsAdapter(items));
        featuredTypes.addItemDecoration(itemDecoration);

        specialOffersView = view.findViewById(R.id.promotions_recycler_view);
        specialOffersView.setAdapter(new SpecialOfferAdapter(specialOffers));
        specialOffersView.addItemDecoration(itemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        specialOffersView.setLayoutManager(linearLayoutManager);

        return view;
    }
}
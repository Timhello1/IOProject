package com.example.ioproject.shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ioproject.R;
import com.example.ioproject.database.DatabaseFetch;
import com.example.ioproject.product.Product;
import com.example.ioproject.utils.ShopOfferAdapter;

import java.util.ArrayList;
import java.util.List;

/**
Fragment wyświetlający pełny asortyment sklepu
 */
public class ShopFragment extends Fragment {

    private RecyclerView productTypes_rv;
    private DatabaseFetch databaseFetch;

    public ShopFragment() {
       databaseFetch = new DatabaseFetch();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop,container,false);
        databaseFetch.fetchProductTypes().addOnSuccessListener(types -> {
            List<Product> productTypes = new ArrayList<>();
            for (String type: types){
                productTypes.add(new Product(type));
            }
            productTypes_rv = view.findViewById(R.id.productTypes_rv);
            productTypes_rv.setAdapter(new ShopOfferAdapter(productTypes,getContext()));
        });


        return view;
    }
}
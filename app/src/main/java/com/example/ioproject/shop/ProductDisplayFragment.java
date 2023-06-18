package com.example.ioproject.shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ioproject.R;
import com.example.ioproject.database.DatabaseFetch;
import com.example.ioproject.product.Product;
import com.example.ioproject.utils.CustomItemDecoration;
import com.example.ioproject.utils.ProductsAdapter;
import com.example.ioproject.utils.VerticalDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment do wyświetlania produktów z bazy danych
 */
public class ProductDisplayFragment extends Fragment {
    private RecyclerView productTypes;
    private DatabaseFetch databaseFetch;
    private ProductsAdapter productsAdapter;

    public ProductDisplayFragment() {
        databaseFetch = new DatabaseFetch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_display, container, false);
        //VerticalDecoration itemDecoration = new VerticalDecoration(50);

        // Odczytanie przekazanej wartości typu produktu
        Bundle bundle = getArguments();
        if (bundle != null) {
            String productType = bundle.getString("productType");
            databaseFetch.fetchProductsByType(productType)
                    .addOnSuccessListener(products -> {
                        productsAdapter = new ProductsAdapter(products);
                        productTypes = view.findViewById(R.id.productTypes_recycler_view);
                        productTypes.setLayoutManager(new LinearLayoutManager(getActivity()));
                        //productTypes.addItemDecoration(itemDecoration);
                        productTypes.setAdapter(productsAdapter);
                    });
            TextView title = view.findViewById(R.id.item_group);
            title.setText(bundle.getString("productType"));
        }

        return view;
    }
}


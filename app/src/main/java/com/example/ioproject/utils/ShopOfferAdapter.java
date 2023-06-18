package com.example.ioproject.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ioproject.R;
import com.example.ioproject.product.Product;
import com.example.ioproject.shop.ProductDisplayFragment;


import java.util.List;

public class ShopOfferAdapter extends RecyclerView.Adapter<ShopOfferAdapter.ViewHolder> {
    private List<Product> productList;
    private static Context context;

    public ShopOfferAdapter(List<Product> productList,Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_title_display, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOfferAdapter.ViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Product product) {
            TextView titles = itemView.findViewById(R.id.product_title_tv);
            titles.setText(product.getProductType());
            itemView.setOnClickListener(view -> {
                String productType = titles.getText().toString();
                Fragment fragment = new ProductDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("productType",productType);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager =
                        ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.
                        popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            });

        }
    }
}

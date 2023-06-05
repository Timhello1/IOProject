package com.example.ioproject.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ioproject.R;
import com.example.ioproject.product.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private List<Product> products;

    public ProductsAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void bind(Product product){
            TextView productTitle = itemView.findViewById(R.id.product_text_title);
            productTitle.setText(product.getName());

            TextView productPrice = itemView.findViewById(R.id.product_price);
            productPrice.setText(product.getPrice());
            itemView.setOnClickListener(view -> {
                // TODO przeniesienie do wybranego produktu
            });
        }

    }
}

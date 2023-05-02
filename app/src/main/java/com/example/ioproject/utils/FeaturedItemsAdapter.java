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

public class FeaturedItemsAdapter extends RecyclerView.Adapter<FeaturedItemsAdapter.ViewHolder> {
    private List<Product> products;

    public FeaturedItemsAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public FeaturedItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.home_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedItemsAdapter.ViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Product product) {
            TextView textView = itemView.findViewById(R.id.home_product_textView);
            textView.setText(product.getProductType());
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //TODO Przeniesienie do produktów
//                }
//            });
        }
    }
}

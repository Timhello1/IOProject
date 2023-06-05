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

public class FeaturedItemsAdapter extends RecyclerView.Adapter<FeaturedItemsAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;

    public FeaturedItemsAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
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
            itemView.setOnClickListener(view -> {
                String productType = textView.getText().toString();

                // Przeniesienie do ProductDisplayFragment i przekazanie informacji
                Fragment fragment = new ProductDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("productType", productType);
                fragment.setArguments(bundle);

                // Usunięcie poprzedniego fragmentu
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                // Wywołanie przeniesienia do fragmentu
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });
        }

    }
}

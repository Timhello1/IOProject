package com.example.ioproject.utils;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ioproject.R;
import com.example.ioproject.product.Product;
import com.example.ioproject.product.SpecialOfferProduct;

import java.util.List;

/**
 * Adapter do wyświetlania ofer specjalnych (promocji) w HomeFragment
 */
public class SpecialOfferAdapter extends RecyclerView.Adapter<SpecialOfferAdapter.ViewHolder> {
    private List<SpecialOfferProduct> products;

    public SpecialOfferAdapter(List<SpecialOfferProduct> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public SpecialOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.special_offers_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialOfferAdapter.ViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,newPrice,oldPrice;
        private ImageView promotionView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.home_promotions_itemName);
            newPrice = itemView.findViewById(R.id.home_promotions_newPrice);
            oldPrice = itemView.findViewById(R.id.home_promotions_oldPrice);
            promotionView = itemView.findViewById(R.id.home_product_image);
        }
        public void bind(SpecialOfferProduct product) {

            name.setText(product.getName());
            newPrice.setText(product.getNewPrice());

            oldPrice.setText(product.getPrice());
            oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            Drawable imageDrawable = ContextCompat.getDrawable(itemView.getContext(), product.getImage());
            promotionView.setImageDrawable(imageDrawable);
        }
    }

}


package com.example.ioproject.product;

import android.graphics.drawable.Drawable;

public class SpecialOfferProduct extends Product{
    private String newPrice;
    private int image;

    public SpecialOfferProduct(String name, String oldPrice, String newPrice, int image){
        super(name,oldPrice);
        this.newPrice = newPrice;
        this.image = image;

    }

    public SpecialOfferProduct(int id, String name, String productType, String description,
                               String price, int stock,String newPrice) {
        super(id, name, productType, description, price, stock);
        this.newPrice = newPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

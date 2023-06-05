package com.example.ioproject.product;

public class Product {

    private int id;
    private String productType;
    private String name;
    private String description;
    private String price;
    private int stock;
    private int image;

    // private double rating;
    // TODO rating co 0.5f, więc trzeba napisać jakąś funkcje, że jak podajesz rating to zaokrągla w dół lub górę

    public Product(String productType) {
        this.productType = productType;
    }
    public Product(String name,String price){
        this.name = name;
        this.price = price;
    }


    public Product(String name, String description, String price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(int id, String productType, String name, String description, String price) {
        this.id = id;
        this.productType = productType;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(int id, String name, String productType, String description, String price, int stock) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
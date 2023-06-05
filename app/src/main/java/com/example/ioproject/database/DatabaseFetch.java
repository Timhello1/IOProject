package com.example.ioproject.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ioproject.product.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseFetch {
    private FirebaseFirestore firestore;

    public DatabaseFetch() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Metoda do przeszukiwania wszystkich typów ofert
     *
     * @return typy ofert
     */
    public Task<List<String>> fetchProductTypes() {
        Task<QuerySnapshot> task = firestore.collection("products").get();

        return task.continueWith(task1 -> {
            if (task1.isSuccessful()) {
                List<DocumentSnapshot> documents = task1.getResult().getDocuments();
                List<String> types = new ArrayList<>();

                for (DocumentSnapshot document : documents) {
                    String productType = document.getString("Type");
                    if (!types.contains(productType)) {
                        types.add(productType);
                    }
                }

                return types;
            } else {
                Exception exception = task1.getException();
                Log.e("DatabaseFetch", "Error fetching product types", exception);
                throw exception;
            }
        });
    }

    /**
     * Metoda do przeszukania wszystkich produktów danego typu
     * @param type typ produktu
     * @return lista produktów
     */
    public Task<List<Product>> fetchProductsByType(String type) {
        Task<QuerySnapshot> task = firestore.collection("products")
                .whereEqualTo("Type", type)
                .get();

        return task.continueWith(task1 -> {
            if (task1.isSuccessful()) {
                List<DocumentSnapshot> documents = task1.getResult().getDocuments();
                List<Product> products = new ArrayList<>();

                for (DocumentSnapshot document : documents) {
                    String productName = document.getString("Name");
                    String productDescription = document.getString("Description");
                    String productPrice = document.getString("Price");
                    int productStock = Integer.parseInt(
                            Objects.requireNonNull(document.getString("Stock")));

                    products.add(new Product(productName,productDescription,
                            productPrice,productStock));
                }

                return products;
            } else {
                Exception exception = task1.getException();
                Log.e("DatabaseFetch", "Error fetching products by type: " + type, exception);
                throw exception;
            }
        });
    }
}

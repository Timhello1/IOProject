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

public class DatabaseFetch {
    private FirebaseFirestore firestore;

    public DatabaseFetch() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Metoda do przeszukiwania wszystkich typów ofert
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

    public Task<List<Product>> fetchProductsByType(String type){
        Task<QuerySnapshot> task = firestore.collection("products").get();
//        return task.continueWith(task1 -> {
//            QuerySnapshot querySnapshot = task1.getResult();
//            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
//        })

        return null;
    }

}

package com.example.ioproject.fragmentsForFutureUse;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ioproject.R;
import com.example.ioproject.databinding.FragmentOrderMakeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

public class OrderMake extends Fragment {

    private FragmentOrderMakeBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderMakeBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Nullable
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button11.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();

            String name = binding.editTextTextPersonName13.getText().toString();
            String code = binding.editTextTextPersonName19.getText().toString();
            String userEmail = user.getEmail();

            db.collection("products")
                    .whereEqualTo("Name", name)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Name checker
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String price = document.getString("Price");

                                if (code.isEmpty()) {
                                    // Add order with normal price if code is empty
                                    Map<String, Object> order = new HashMap<>();
                                    order.put("Name", name);
                                    order.put("Code", "");
                                    order.put("Email", userEmail);
                                    order.put("Price", price);

                                    db.collection("orders")
                                            .add(order)
                                            .addOnSuccessListener(documentReference -> {
                                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                Toast.makeText(requireContext(), "Dodane do zamówienia", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                                } else {
                                    // Check for valid coupon code
                                    db.collection("offers")
                                            .whereEqualTo("Code", code)
                                            .get()
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    // Offer checker
                                                    boolean couponFound = false;
                                                    for (QueryDocumentSnapshot documentSnapshot : task1.getResult()) {
                                                        String price1 = documentSnapshot.getString("Price");
                                                        String expiryDate = documentSnapshot.getString("Date");

                                                        if (isExpired(expiryDate)) {
                                                            Toast.makeText(requireContext(), "Coupon code has expired", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            couponFound = true;
                                                            Map<String, Object> order1 = new HashMap<>();
                                                            order1.put("Name", name);
                                                            order1.put("Code", code);
                                                            order1.put("Email", userEmail);
                                                            order1.put("Price", price1);

                                                            db.collection("orders")
                                                                    .add(order1)
                                                                    .addOnSuccessListener(documentReference1 -> {
                                                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference1.getId());
                                                                        Toast.makeText(requireContext(), "Dodane do zamówienia", Toast.LENGTH_SHORT).show();
                                                                    })
                                                                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

                                                            break; // Exit the loop since the coupon was found
                                                        }
                                                    }
                                                    if (!couponFound) {
                                                        // Handle case where coupon was not found
                                                        Toast.makeText(requireContext(), "Podany kod kuponu nie istnieje lub wygasł", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    // Handle offer checker failure
                                                }
                                            });
                                }
                                return;
                            }
                            Toast.makeText(requireContext(), "Podany produkt nie istnieje", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Podany produkt nie istnieje", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private boolean isExpired(String expiryDate) {
        if (expiryDate == null) {
            Toast.makeText(requireContext(), "Expiry date is null", Toast.LENGTH_SHORT).show();
            return true;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date();

        try {
            Date expiry = dateFormat.parse(expiryDate);
            return expiry.before(currentDate);
        } catch (ParseException e) {
            Toast.makeText(requireContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}

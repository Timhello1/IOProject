package com.example.ioproject.shop;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ioproject.R;
import com.example.ioproject.databinding.FragmentAddOfferBinding;
import com.example.ioproject.databinding.FragmentAddProductBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


public class AddOfferFragment extends Fragment {

    private FragmentAddOfferBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddOfferBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        binding.button2.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> offer = new HashMap<>();
            offer.put("Name",binding.OfferName.getText().toString());
            offer.put("Price",binding.OfferPrice.getText().toString());
            offer.put("Description",binding.OfferDescription.getText().toString());
            offer.put("Code",binding.OfferCode.getText().toString());
            offer.put("Date",binding.OfferDate.getText().toString());

            db.collection("offers")
                    .add(offer)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
            Toast.makeText(requireContext(),"oferta dodany",Toast.LENGTH_SHORT).show();
        });
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
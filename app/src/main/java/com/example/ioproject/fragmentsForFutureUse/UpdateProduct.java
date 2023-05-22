package com.example.ioproject.fragmentsForFutureUse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ioproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class UpdateProduct extends Fragment {

    private EditText nameEditText, descEditText, priceEditText, stockEditText, typeEditText;
    private Button updateButton;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_product, container, false);
        nameEditText = view.findViewById(R.id.editTextTextPersonName6);
        descEditText = view.findViewById(R.id.editTextTextPersonName7);
        priceEditText = view.findViewById(R.id.editTextTextPersonName8);
        stockEditText = view.findViewById(R.id.editTextTextPersonName9);
        typeEditText = view.findViewById(R.id.editTextTextPersonName10);
        updateButton = view.findViewById(R.id.button8);
        firestore = FirebaseFirestore.getInstance();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDocument();
            }
        });

        return view;
    }

    private void updateDocument() {
        String documentName = nameEditText.getText().toString().trim();
        String updatedDesc = descEditText.getText().toString().trim();
        String updatedPrice = priceEditText.getText().toString().trim();
        String updatedStock = stockEditText.getText().toString().trim();
        String updatedType = typeEditText.getText().toString().trim();

        if (!documentName.isEmpty()) {
            DocumentReference documentReference = firestore.collection("product").document(documentName);

            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("Description", updatedDesc);
            updatedData.put("Price", updatedPrice);
            updatedData.put("Stock", updatedStock);
            updatedData.put("Type", updatedType);

            documentReference.update(updatedData)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Document updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed to update document", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Please enter a document name", Toast.LENGTH_SHORT).show();
        }
    }
}
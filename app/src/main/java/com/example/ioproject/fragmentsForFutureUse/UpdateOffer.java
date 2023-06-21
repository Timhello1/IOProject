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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class UpdateOffer extends Fragment {

    private EditText codeEditText, nameEditText, dateEditText, descEditText, priceEditText;
    private Button updateButton;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_offer, container, false);

        codeEditText = view.findViewById(R.id.editTextTextPersonName12);
        nameEditText = view.findViewById(R.id.editTextTextPersonName15);
        dateEditText = view.findViewById(R.id.editTextTextPersonName16);
        descEditText = view.findViewById(R.id.editTextTextPersonName14);
        priceEditText = view.findViewById(R.id.editTextTextPersonName17);
        updateButton = view.findViewById(R.id.button9);
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
        String code = codeEditText.getText().toString().trim();
        String updatedName = nameEditText.getText().toString().trim();
        String updatedDate = dateEditText.getText().toString().trim();
        String updatedDesc = descEditText.getText().toString().trim();
        String updatedPrice = priceEditText.getText().toString().trim();

        if (!code.isEmpty()) {
            Query query = firestore.collection("offers").whereEqualTo("Code",code);
            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null && !querySnapshot.isEmpty()){
                                    DocumentReference documentReference = querySnapshot.getDocuments().get(0).getReference();

                                    Map<String, Object> updatedData = new HashMap<>();
                                    updatedData.put("Name", updatedName);
                                    updatedData.put("Date", updatedDate);
                                    updatedData.put("Description", updatedDesc);
                                    updatedData.put("Price", updatedPrice);

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
                                }else {
                                    Toast.makeText(getActivity(), "No document found", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getActivity(), "Error while querying document", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Please enter a code", Toast.LENGTH_SHORT).show();
        }
    }
}
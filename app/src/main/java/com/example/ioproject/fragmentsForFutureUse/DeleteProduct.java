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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteProduct extends Fragment {

    private EditText documentNameEditText;
    private Button deleteButton;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_product, container, false);
        documentNameEditText = view.findViewById(R.id.editTextTextPersonName3);
        deleteButton = view.findViewById(R.id.button6);
        firestore = FirebaseFirestore.getInstance();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDocument();
            }
        });

        return view;
    }

    private void deleteDocument() {
        final String documentName = documentNameEditText.getText().toString().trim();
        if (!documentName.isEmpty()) {
            Query query = firestore.collection("products").whereEqualTo("Name", documentName);
            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                    DocumentReference documentReference = querySnapshot.getDocuments().get(0).getReference();
                                    documentReference.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getActivity(), "Dokument usunięty", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(getActivity(), "Nie udało się usunąć", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    if (e instanceof FirebaseFirestoreException) {
                                                        FirebaseFirestoreException firebaseFirestoreException = (FirebaseFirestoreException) e;
                                                        Toast.makeText(getActivity(), "Error: " + firebaseFirestoreException.getCode(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(getActivity(), "Nie znaleziono dokumentu", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Błąd podczas wyszukiwania dokumentu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseFirestoreException) {
                                FirebaseFirestoreException firebaseFirestoreException = (FirebaseFirestoreException) e;
                                Toast.makeText(getActivity(), "Error: " + firebaseFirestoreException.getCode(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Podaj nazwę produktu", Toast.LENGTH_SHORT).show();
        }
    }
}

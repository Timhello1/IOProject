package com.example.ioproject.fragmentsForFutureUse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ioproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Basket extends Fragment {



    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<String> ordersList;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String currentUserEmail;
    private TextView sumTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket2, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        sumTextView = view.findViewById(R.id.sumTextView);
        int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        recyclerView.addItemDecoration(new RecyclerViewSpacingDecoration(spacing));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersList = new ArrayList<>();
        orderAdapter = new OrderAdapter(ordersList);
        // Adjust the spacing as desired



        recyclerView.setAdapter(orderAdapter);
        db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            currentUserEmail = currentUser.getEmail();
            fetchOrders();
        }

        Button navigateButton= view.findViewById(R.id.button12);
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_basketChoices basketChoices = new Fragment_basketChoices();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(container.getId(), basketChoices); // Replace "R.id.fragment_container" with the ID of the container in your activity where you want to replace the fragment
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit(); // Commit the transaction
            }
        });


        return view;
    }

    private void fetchOrders() {
        CollectionReference ordersRef = db.collection("orders");
        Query query = ordersRef.whereEqualTo("Email", currentUserEmail);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ordersList.clear();
                    int sum = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Assuming each order document has a "Name" and "Price" field
                        String name = document.getString("Name");
                        String price = document.getString("Price");

                        ordersList.add(name + ": " + price + " zl");
                        sum += Double.parseDouble(price);
                    }
                    orderAdapter.notifyDataSetChanged();
                    sumTextView.setText("Total: " + sum + " zl");
                } else {
                    // Handle error
                }
            }
        });
    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
        private List<String> orders;

        public OrderAdapter(List<String> orders) {
            this.orders = orders;
        }

        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
            return new OrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
            String order = orders.get(position);
            holder.orderTextView.setText(order);

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String orderName = order.split(":")[0].trim();
                    deleteOrderFromDatabase(orderName);
                }
            });
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public class OrderViewHolder extends RecyclerView.ViewHolder {
            private TextView orderTextView;
            private Button deleteButton;

            public OrderViewHolder(@NonNull View itemView) {
                super(itemView);
                orderTextView = itemView.findViewById(R.id.order_text_view);
                deleteButton = itemView.findViewById(R.id.button18);
            }
        }
        private void deleteOrderFromDatabase(String orderName){
            CollectionReference ordersRef = db.collection("orders");
            Query query = ordersRef.whereEqualTo("Email", currentUserEmail).whereEqualTo("Name", orderName);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            documentSnapshot.getReference().delete();
                        }
                        fetchOrders();
                    }
                }
            });
        }
    }
}
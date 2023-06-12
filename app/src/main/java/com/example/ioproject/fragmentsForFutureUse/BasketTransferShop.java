package com.example.ioproject.fragmentsForFutureUse;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ioproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TrackOpens;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BasketTransferShop extends Fragment {

    private Button orderButton;

    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String currentUserEmail;

    private void fetchOrders(FirebaseFirestore db, String currentUserEmail, final EmailTaskCallback callback) {
        CollectionReference ordersRef = db.collection("orders");
        Query query = ordersRef.whereEqualTo("Email", currentUserEmail);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> ordersList = new ArrayList<>();
                int sum = 0;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String name = document.getString("Name");
                    String price = document.getString("Price");

                    ordersList.add(name + ": zl" + price);
                    sum += Double.parseDouble(price);
                }

                String ordersString = String.join("\n", ordersList);
                callback.onOrdersFetched(ordersString, sum);
            } else {
                callback.onOrdersFetched("", 0);
            }
        });
    }

    private void sendEmail(String orders, int sum) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();

        AsyncTask<Void, Void, Void> emailTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    ClientOptions options = ClientOptions.builder()
                            .apiKey("d77fd20a54f5449cb935f4b9590f8a29")
                            .apiSecretKey("73bf298e91ff71f5a4ced9f482930552")
                            .build();

                    MailjetClient client = new MailjetClient(options);

                    TransactionalEmail message1 = TransactionalEmail
                            .builder()
                            .to(new SendContact(userEmail, "Recipient Name"))
                            .from(new SendContact("calkatym@gmail.com", "Tymu$"))
                            .htmlPart("<h1>Proszę opłacić swój rachunek</h1>" +
                                    "<p>Przelew na konto 123214236245748548233521</p>" +
                                    "<p>Pod tytułem przelej mi</p>" +
                                    "<p>Orders:</p>" +
                                    "<p>" + orders + "</p>" +
                                    "<p>Total: zl" + sum + "</p>" +
                                    "<p> Po opłaceniu zamówienie zostanie dostarczone do sklepu na ulicy Robotniczej 76 w Wrocławiu</p>")
                            .subject("Receipt")
                            .trackOpens(TrackOpens.ENABLED)
                            .header("test-header-key", "test-value")
                            .customID("custom-id-value")
                            .build();

                    SendEmailsRequest request = SendEmailsRequest
                            .builder()
                            .message(message1) // you can add up to 50 messages per request
                            .build();

                    // Send the request
                    SendEmailsResponse response = request.sendWith(client);

                    // Handle the response

                } catch (MailjetException e) {
                    e.printStackTrace();
                    // Exception occurred while sending email
                    // You can add your exception handling code here
                }
                return null;
            }
        };

        emailTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket_transfer_shop, container, false);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserEmail = currentUser.getEmail();
        }

        orderButton = view.findViewById(R.id.button15);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform any necessary validations or checks here

                if (currentUser != null) {
                    fetchOrders(db, currentUserEmail, new EmailTaskCallback() {
                        @Override
                        public void onOrdersFetched(String orders, int sum) {
                            sendEmail(orders, sum);
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "User is not authenticated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private interface EmailTaskCallback {
        void onOrdersFetched(String orders, int sum);
    }
}

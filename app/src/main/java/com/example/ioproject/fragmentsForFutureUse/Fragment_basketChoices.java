package com.example.ioproject.fragmentsForFutureUse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.example.ioproject.R;


public class Fragment_basketChoices extends Fragment {


    private Switch deliverySwitch;
    private Button orderButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket_choices, container, false);


        deliverySwitch = view.findViewById(R.id.switch3);
        orderButton = view.findViewById(R.id.button13);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDeliveryOn = deliverySwitch.isChecked();

                if (isDeliveryOn) {
                    // Both switches are ON
                    // Perform action for payment and delivery
                    BasketTransferShop basketTransferShop = new BasketTransferShop();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(container.getId(), basketTransferShop); // Replace "R.id.fragment_container" with the ID of the container in your activity where you want to replace the fragment
                    transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    transaction.commit(); // Commit the transaction
                } else {
                    // Only payment switch is ON
                    // Perform action for payment
                    BasketTransferAddress basketTransferAddress = new BasketTransferAddress();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(container.getId(), basketTransferAddress); // Replace "R.id.fragment_container" with the ID of the container in your activity where you want to replace the fragment
                    transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    transaction.commit(); // Commit the transaction
                }
            }
        });

        return view;
    }
}
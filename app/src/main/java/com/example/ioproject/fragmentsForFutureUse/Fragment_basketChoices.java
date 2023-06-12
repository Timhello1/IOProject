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

    private Switch paymentSwitch;
    private Switch deliverySwitch;
    private Button orderButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket_choices, container, false);

        paymentSwitch = view.findViewById(R.id.switch1);
        deliverySwitch = view.findViewById(R.id.switch3);
        orderButton = view.findViewById(R.id.button13);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPaymentOn = paymentSwitch.isChecked();
                boolean isDeliveryOn = deliverySwitch.isChecked();

                if (isPaymentOn && isDeliveryOn) {
                    // Both switches are ON
                    // Perform action for payment and delivery
                    BasketTransferShop basketTransferShop = new BasketTransferShop();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(container.getId(), basketTransferShop); // Replace "R.id.fragment_container" with the ID of the container in your activity where you want to replace the fragment
                    transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    transaction.commit(); // Commit the transaction
                } else if (isPaymentOn) {
                    // Only payment switch is ON
                    // Perform action for payment
                    BasketTransferAddress basketTransferAddress = new BasketTransferAddress();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(container.getId(), basketTransferAddress); // Replace "R.id.fragment_container" with the ID of the container in your activity where you want to replace the fragment
                    transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    transaction.commit(); // Commit the transaction
                } else if (isDeliveryOn) {
                    // Only delivery switch is ON
                    // Perform action for delivery
                    BasketCardShop basketCardShop = new BasketCardShop();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(container.getId(), basketCardShop); // Replace "R.id.fragment_container" with the ID of the container in your activity where you want to replace the fragment
                    transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    transaction.commit(); // Commit the transaction
                } else {
                    // Both switches are OFF
                    // Perform action for default case
                    BasketCardAddress basketCardAddress = new BasketCardAddress();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(container.getId(), basketCardAddress); // Replace "R.id.fragment_container" with the ID of the container in your activity where you want to replace the fragment
                    transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    transaction.commit(); // Commit the transaction
                }
            }
        });

        return view;
    }
}
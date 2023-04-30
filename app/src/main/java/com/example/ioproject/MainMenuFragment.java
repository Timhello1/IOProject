package com.example.ioproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import android.R.layout.*;


public class MainMenuFragment extends Fragment {

    ListView listView;

    public MainMenuFragment() {

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_menu,container,false);

        listView = (ListView) root.findViewById(R.id.menuList);

        ArrayList<String> menuItems = new ArrayList<>();
        menuItems.add("View items");
        menuItems.add("View prices in competing store");
        menuItems.add("View estimated delivery time");
        menuItems.add("View discounts");

        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, menuItems);

        listView.setAdapter(menuAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = menuItems.get(position);

                switch (selectedItem) {
                    case "View available items in store":
                        // Handle click for this item
                        break;
                    case "View prices in competing stores":
                        // Handle click for this item
                        break;
                    case "View estimated delivery time":
                        // Handle click for this item
                        break;
                    case "View discounts":
                        // Handle click for this item
                        break;
                }
            }
        });

        return root;
    }
}
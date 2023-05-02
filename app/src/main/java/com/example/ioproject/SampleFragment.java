package com.example.ioproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SampleFragment extends Fragment {

    public SampleFragment() {

    }




//TODO TUTAJ NIE WIEM CO Z TYM ROBIMY CZY USUWAMY, CZY JAK, TO JUŻ ZOSTAWIAM TOBIE

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sample,container,false);
    }
}
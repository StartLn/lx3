package com.example.msi.week3demo.fragement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msi.week3demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFromFragment extends Fragment {


    public OrderFromFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_from, container, false);
    }

}

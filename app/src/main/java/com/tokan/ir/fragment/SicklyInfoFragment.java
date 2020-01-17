package com.tokan.ir.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.tokan.ir.CheckDeviceActivity;
import com.tokan.ir.R;
import com.tokan.ir.widget.BEditTextView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SicklyInfoFragment extends Fragment {

    @BindView(R.id.btn_action)
    AppCompatButton btn_action;

    public SicklyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sickly_info, container, false);
        btn_action = view.findViewById(R.id.btn_action);
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CLICK-----");
            }
        });
        return view;
    }

}

package com.tokan.ir.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tokan.ir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceSettingFragment extends Fragment {


    public DeviceSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_setting, container, false);
        return view;
    }

}

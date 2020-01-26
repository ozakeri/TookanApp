package com.tokan.ir.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tokan.ir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackupManageFragment extends Fragment {


    public BackupManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_backup_manage, container, false);
    }

}

package com.tokan.ir.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tokan.ir.CheckDeviceActivity;
import com.tokan.ir.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestLoginFragment extends Fragment {

    @BindView(R.id.btn_action)
    AppCompatButton btn_action;

    public TestLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_login, container, false);

        ButterKnife.bind(getActivity());

        btn_action = view.findViewById(R.id.btn_action);

        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.home_container, new SicklyInfoFragment());
                transaction.commit();
            }
        });

        return view;
    }

    public void action(View view) {
        startActivity(new Intent(getActivity(), CheckDeviceActivity.class));
    }

}

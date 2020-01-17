package com.tokan.ir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_test)
    AppCompatButton btn_test;

    @BindView(R.id.btn_search)
    AppCompatButton btn_search;

    @BindView(R.id.btn_report)
    AppCompatButton btn_report;

    @BindView(R.id.btn_setting)
    AppCompatButton btn_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


    }

    public void action(View view) {
        /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, new TestLoginFragment());
        transaction.commit();*/
        startActivity(new Intent(getApplicationContext(), Server.class));
    }
}

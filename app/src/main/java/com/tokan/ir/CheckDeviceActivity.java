package com.tokan.ir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tokan.ir.widget.BEditTextView;
import com.tokan.ir.widget.BTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckDeviceActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img_arrow)
    ImageView img_arrow;

    @BindView(R.id.txt_title)
    BTextView txt_title;

    @BindView(R.id.edt_code)
    BEditTextView edt_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_device);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        txt_title.setHint("تنظیمات اتصال به دستگاه");
        edt_code.setHint("کد شناسایی دستگاه");

    }

    public void action(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}

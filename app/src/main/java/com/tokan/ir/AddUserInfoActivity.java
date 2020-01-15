package com.tokan.ir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tokan.ir.widget.BEditTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddUserInfoActivity extends AppCompatActivity {

    @BindView(R.id.edt_nameFamily)
    BEditTextView edt_nameFamily;

    @BindView(R.id.edt_title)
    BEditTextView edt_title;

    @BindView(R.id.edt_phoneNumber)
    BEditTextView edt_phoneNumber;

    @BindView(R.id.edt_mobileNumber)
    BEditTextView edt_mobileNumber;

    @BindView(R.id.edt_address)
    BEditTextView edt_address;

    @BindView(R.id.edt_email)
    BEditTextView edt_email;

    @BindView(R.id.edt_site)
    BEditTextView edt_site;

    @BindView(R.id.edt_username)
    BEditTextView edt_username;

    @BindView(R.id.edt_password)
    BEditTextView edt_password;

    @BindView(R.id.edt_confirmPassword)
    BEditTextView edt_confirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_user);

        ButterKnife.bind(this);

        edt_nameFamily.setHint("نام و نانوادگی");
        edt_title.setHint("عنوان مجموعه");
        edt_phoneNumber.setHint("تلفن ثابت مجموعه");
        edt_mobileNumber.setHint("تلفن همراه");
        edt_address.setHint("نشانی");
        edt_email.setHint("ایمیل");
        edt_site.setHint("وب سایت");
        edt_username.setHint("نام کاربری");
        edt_password.setHint("رمز عبور");
        edt_confirmPassword.setHint("تکرار رمز عبور");

        //edt_address.setMax(1000);
        edt_address.setLines(5);


    }

    public void action(View view) {
        startActivity(new Intent(getApplicationContext(), CheckDeviceActivity.class));
    }
}

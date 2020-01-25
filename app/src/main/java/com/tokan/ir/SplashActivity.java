package com.tokan.ir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.User;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getUserList();
            }
        }, SPLASH_TIME_OUT);
    }

    public void getUserList() {
        class GetUserList extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                users = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().getUsers();
                return users;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                if (users.size() != 0) {
                    User user = users.get(0);
                    if (user.isLoginIs()) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                } else {
                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                }


                finish();
            }
        }

        new GetUserList().execute();
    }
}

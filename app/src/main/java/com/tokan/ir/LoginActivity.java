package com.tokan.ir;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.User;
import com.tokan.ir.utils.Util;
import com.tokan.ir.widget.BEditTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_username)
    EditText edt_username;

    @BindView(R.id.edt_password)
    EditText edt_password;

    @BindView(R.id.ben_action)
    Button ben_action;

    @BindView(R.id.img_user)
    CircleImageView img_user;

    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getUserList();

        edt_username.setHint("نام کاربری");
        edt_password.setHint("کلمه عبور");


        ben_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAction();
            }
        });
    }

    public void getUserList() {

        class GetInfo extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                userList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().getUsers();
                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                User user = users.get(0);
                Bitmap bitmap = resizeBitmap(user.getPath(), 100, 100);
                img_user.setImageBitmap(bitmap);


            }
        }

        new GetInfo().execute();
    }

    public void getAction() {

        String userName = edt_username.getText().toString();
        String password = edt_password.getText().toString();

        if (userName.equals("")) {
            edt_username.setError("کامل کنید");
            return;
        }

        if (password.equals("")) {
            edt_password.setError("کامل کنید");
            return;
        }


        class GetInfo extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                userList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().getUsers();
                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                User user = users.get(0);

                if (user.getUsername().equals(userName) && user.getPassword().equals(Util.faToEn(password))) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "اطلاعات اشتباه است", Toast.LENGTH_SHORT).show();
                }

            }
        }

        new GetInfo().execute();
    }

    private Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }
}

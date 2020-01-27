package com.tokan.ir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.callback.OnBackPressedListener;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.User;
import com.tokan.ir.fragment.ReportErrorFragment;
import com.tokan.ir.fragment.ReportFragment;
import com.tokan.ir.fragment.SearchFragment;
import com.tokan.ir.fragment.SettingFragment;
import com.tokan.ir.fragment.SicklyInfoFragment;
import com.tokan.ir.widget.BTextView;
import com.tokan.ir.widget.drawer.DrawerList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView listView_drawer;
    private RelativeLayout layout_drawer;

    @BindView(R.id.btn_test)
    CardView btn_test;

    @BindView(R.id.txt_username)
    BTextView txt_username;

    @BindView(R.id.img_user)
    AppCompatImageView img_user;

    @BindView(R.id.btn_search)
    CardView btn_search;

    @BindView(R.id.btn_report)
    CardView btn_report;

    @BindView(R.id.btn_setting)
    CardView btn_setting;

    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        layout_drawer = (RelativeLayout) findViewById(R.id.layout_drawer);
        listView_drawer = (RecyclerView) findViewById(R.id.listView_drawer);

        DrawerList drawerlayout = new DrawerList(MainActivity.this, drawerLayout, layout_drawer, listView_drawer);
        drawerlayout.addListDrawer();

        ButterKnife.bind(this);

        getUserList();


    }

    public void test(View view) {
        gotoFragment(new SicklyInfoFragment());
    }

    public void search(View view) {
        gotoFragment(new SearchFragment());
    }

    public void report(View view) {
        gotoFragment(new ReportFragment());
    }


    public void setting(View view) {

        gotoFragment(new SettingFragment());
    }

    private void gotoFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
                Bitmap bitmap = resizeBitmap(user.getPath(), 200, 200);
                img_user.setImageBitmap(bitmap);
                txt_username.setText(user.getNameFamily());

                System.out.println("user.getPath()===" + user.getPath());
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

    protected OnBackPressedListener onBackPressedListener;

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        }
    }
}

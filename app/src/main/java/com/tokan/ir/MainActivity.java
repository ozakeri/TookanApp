package com.tokan.ir;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.User;
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
    AppCompatButton btn_test;

    @BindView(R.id.img_user)
    AppCompatImageView img_user;

    @BindView(R.id.btn_search)
    AppCompatButton btn_search;

    @BindView(R.id.btn_report)
    AppCompatButton btn_report;

    @BindView(R.id.btn_setting)
    AppCompatButton btn_setting;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

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

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        getUserInfo();


    }

    public void action(View view) {
        /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, new TestLoginFragment());
        transaction.commit();*/
        startActivity(new Intent(getApplicationContext(), Server.class));
    }

    public void getUserInfo() {
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
                Uri mUri = Uri.parse(user.getPath());
                img_user.setImageURI(mUri);
                System.out.println("getPath===" + user.getPath());
                System.out.println("getPath===" + user.getNameFamily());
            }
        }

        new GetInfo().execute();
    }
}

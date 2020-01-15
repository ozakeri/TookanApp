package com.tokan.ir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.tokan.ir.widget.drawer.DrawerList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView listView_drawer;
    private RelativeLayout layout_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        layout_drawer = (RelativeLayout) findViewById(R.id.layout_drawer);
        listView_drawer = (RecyclerView) findViewById(R.id.listView_drawer);

        DrawerList drawerlayout = new DrawerList(MainActivity.this, drawerLayout, layout_drawer, listView_drawer);
        drawerlayout.addListDrawer();
    }
}

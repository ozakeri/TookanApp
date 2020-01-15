package com.tokan.ir.widget.drawer;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.application.TokanApplication;
import com.tokan.ir.widget.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.tokan.ir.R2.id.home_container;

public class DrawerList {
    private static DrawerLayout mDrawerLayout;
    private static DrawerAdapter adapter;
    private static List<DrawerItem> dataList;
    private static RecyclerView recyclerView;
    private static RelativeLayout rel;
    private Context context;

    public DrawerList(Context context, DrawerLayout DrawerLayout, RelativeLayout rel, RecyclerView recyclerView) {
        this.mDrawerLayout = DrawerLayout;
        this.recyclerView = recyclerView;
        this.rel = rel;
        this.context = context;

    }

    public void addListDrawer() {
        dataList = new ArrayList<DrawerItem>();
        dataList.add(new DrawerItem("شروع تست ", R.drawable.ic_launcher_background));
        dataList.add(new DrawerItem("جستجوی بیماران", R.drawable.ic_launcher_background));
        dataList.add(new DrawerItem("گزارشات", R.drawable.ic_launcher_background));
        dataList.add(new DrawerItem("تنظیمات", R.drawable.ic_launcher_background));
        dataList.add(new DrawerItem("درباره ما ", R.drawable.ic_launcher_background));
        dataList.add(new DrawerItem("تماس با ما ", R.drawable.ic_launcher_background));


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new DrawerAdapter(context, dataList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SelectItem(position);
            }
        }));

    }

    private void SelectItem(int possition) {

        switch (possition) {
            case 0:
               // fragmentTransaction(new RecentServicesFragment(), context.getString(R.string.drawer_home));
                break;

            case 1:
                // fragmentTransaction(new RecentServicesFragment(), context.getString(R.string.drawer_home));
                break;

            case 2:
                // fragmentTransaction(new RecentServicesFragment(), context.getString(R.string.drawer_home));
                break;

            case 3:
                // fragmentTransaction(new RecentServicesFragment(), context.getString(R.string.drawer_home));
                break;

            case 4:
                // fragmentTransaction(new RecentServicesFragment(), context.getString(R.string.drawer_home));
                break;

            case 5:
                // fragmentTransaction(new RecentServicesFragment(), context.getString(R.string.drawer_home));
                break;

            default:

                break;
        }

        mDrawerLayout.closeDrawer(rel);

    }

    public void fragmentTransaction(final Fragment fragment, final String subtitle) {

        TokanApplication.getInstance().resetBackCount();

        if (fragment != null) {

            Fragment current = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag(fragment.getClass().getCanonicalName());
            if (current != null && current.isVisible())
                return;

            //mToolBar.setSubtitle(subtitle);
            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(home_container, fragment, fragment.getClass().getCanonicalName());
            fragmentTransaction.commit();
        }
    }
}

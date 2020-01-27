package com.tokan.ir.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.application.TokanApplication;
import com.tokan.ir.utils.FragmentUtil;
import com.tokan.ir.utils.RecyclerItemClickListener;
import com.tokan.ir.widget.drawer.DrawerAdapter;
import com.tokan.ir.widget.drawer.DrawerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tokan.ir.R2.id.home_container;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private static List<DrawerItem> dataList;
    private static DrawerAdapter adapter;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);

        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        final FragmentManager fragmentManager = getFragmentManager();


        dataList = new ArrayList<DrawerItem>();
        dataList.add(new DrawerItem("تنظیمات کاربری ", R.drawable.user_icon));
        dataList.add(new DrawerItem("تنظیمات دستگاه", R.drawable.settings_icon));
        dataList.add(new DrawerItem("تغییر رمز عبور", R.drawable.login_icon));
        dataList.add(new DrawerItem("مدیریت پشتیبان ها", R.drawable.cloud_icon));
        dataList.add(new DrawerItem("پشتیبان گیری ", R.drawable.backup_icon));
        dataList.add(new DrawerItem("گزارش خطا ", R.drawable.warning_icon));
        dataList.add(new DrawerItem("راهنما ", R.drawable.information_icon));

        adapter = new DrawerAdapter(getActivity(), dataList);
        recycler_view.setAdapter(adapter);


        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        //fragmentTransaction(new UserSettingFragment(), "UserSettingFragment");
                        gotoFragment(new UserSettingFragment(), "UserSettingFragment");
                        break;

                    case 1:
                        //fragmentTransaction(new DeviceSettingFragment(), "DeviceSettingFragment");
                        gotoFragment(new DeviceSettingFragment(), "DeviceSettingFragment");
                        break;

                    case 2:
                        gotoFragment(new ChangePasswordFragment(), "ChangePasswordFragment");

                        break;

                    case 3:
                        //fragmentTransaction(new BackupManageFragment(), "BackupManageFragment");
                        gotoFragment(new BackupManageFragment(), "BackupManageFragment");

                        break;

                    case 4:
                        // fragmentTransaction(new BackupFragment(), "BackupFragment");
                        gotoFragment(new BackupFragment(), "BackupFragment");
                        break;

                    case 5:
                        //fragmentTransaction(new ReportErrorFragment(), "ReportErrorFragment");
                        gotoFragment(new ReportErrorFragment(), "ReportErrorFragment");
                        break;

                    case 6:
                        //fragmentTransaction(new GuideFragment(), "GuideFragment");
                        gotoFragment(new GuideFragment(), "GuideFragment");
                        break;
                }
            }
        }));

        return view;
    }

    private void gotoFragment(Fragment fragment, String fragmentName) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment frg = FragmentUtil.getFragmentByTagName(fragmentManager, fragmentName);
        if (frg == null) {
            frg = fragment;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_container, frg, fragmentName);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        FragmentUtil.printActivityFragmentList(fragmentManager);
    }

    public void fragmentTransaction(final Fragment fragment, final String subtitle) {

        TokanApplication.getInstance().resetBackCount();

        if (fragment != null) {

            Fragment current = (getActivity()).getSupportFragmentManager().findFragmentByTag(fragment.getClass().getCanonicalName());
            if (current != null && current.isVisible())
                return;

            //mToolBar.setSubtitle(subtitle);
            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(home_container, fragment, fragment.getClass().getCanonicalName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}

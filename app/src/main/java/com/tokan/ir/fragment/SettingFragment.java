package com.tokan.ir.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.Backup;
import com.tokan.ir.model.EventModel;
import com.tokan.ir.utils.FragmentUtil;
import com.tokan.ir.utils.RecyclerItemClickListener;
import com.tokan.ir.widget.drawer.DrawerAdapter;
import com.tokan.ir.widget.drawer.DrawerItem;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private static List<DrawerItem> dataList;
    private static DrawerAdapter adapter;

    private List<Backup> backupList = new ArrayList<>();

    public static final String PACKAGE_NAME = "com.tokan.ir";
    public static final String DATABASE_NAME = "MyDatabase.db";

    private ProgressDialog progress;

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
                        //gotoFragment(new BackupFragment(), "BackupFragment");
                        showDialog();
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
        EventBus.getDefault().post(new EventModel(fragmentName));
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


    public void showDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.fragment_backup);
        dialog.show();
        Button btn_no = dialog.findViewById(R.id.btn_No);
        Button btn_yes = dialog.findViewById(R.id.btn_Yes);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backup();
                dialog.dismiss();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void backup() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                //String currentDbPath = "//data//com.bluapp.androidview//databases//UserDb";
                String currentDbPath = "//data/com.tokan.ir/databases/MyDatabase";
                String backupPath = "BackupMyDatabase" + new Date() + ".db";
                File currentDb = new File(data, currentDbPath);
                File backupDb = new File(sd, backupPath);
                FileChannel src = new FileInputStream(currentDb).getChannel();
                FileChannel dst = new FileOutputStream(backupDb).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                saveBackup(backupPath, currentDbPath, DATABASE_NAME, PACKAGE_NAME);
            }

        } catch (Exception e) {
            Log.e("Error====", e.getMessage());
        }
    }


    public void saveBackup(String backupPath, String currentDbPath, String databaseName, String packageName) {

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading..."); // Setting Message
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progress.show(); // Display Progress Dialog
        progress.setCancelable(false);

        class BackupList extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Backup backup = new Backup();
                backup.setBackupPath(backupPath);
                backup.setCurrentDbPath(currentDbPath);
                backup.setDatabaseName(databaseName);
                backup.setPackageName(packageName);

                try {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progress.dismiss();
                            DatabaseClient.getInstance(getActivity()).getAppDatabase().backupDao().insertBackup(backup);
                        }
                    }).start();
                } catch (Exception e) {
                    e.getMessage();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity(), "با موفقیت انجام شد", Toast.LENGTH_LONG).show();
            }
        }

        new BackupList().execute();
    }
}

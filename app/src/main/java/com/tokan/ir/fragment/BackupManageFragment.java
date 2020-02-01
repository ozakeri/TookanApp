package com.tokan.ir.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.adapter.BackupListAdapter;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.Backup;
import com.tokan.ir.widget.RecyclerItemClickListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackupManageFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    List<Backup> backupList = new ArrayList<>();

    public static final String PACKAGE_NAME = "com.tokan.ir";
    public static final String DATABASE_NAME = "MyDatabase.db";

    private static final File DATA_DIRECTORY_DATABASE =
            new File(Environment.getDataDirectory() +
                    "/data/" + PACKAGE_NAME +
                    "/databases/" + DATABASE_NAME);

    /**
     * Directory that files are to be read from and written to
     **/
    protected static final File DATABASE_DIRECTORY =
            new File(Environment.getExternalStorageDirectory(), "");


    public BackupManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_backup_manage, container, false);

        ButterKnife.bind(this, view);

        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));

        getBackupList();

        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Backup backup = backupList.get(position);
                if (backup != null) {
                    restoreDb(backup.getBackupPath());
                }
            }
        }));

        return view;
    }


    public void getBackupList() {
        class BackupAsync extends AsyncTask<Void, Void, List<Backup>> {

            @Override
            protected List<Backup> doInBackground(Void... voids) {
                backupList = DatabaseClient.getInstance(getActivity()).getAppDatabase().backupDao().getBackups();
                return backupList;
            }


            @Override
            protected void onPostExecute(List<Backup> backupList) {
                super.onPostExecute(backupList);

                if (backupList != null) {
                    recycler_view.setAdapter(new BackupListAdapter(backupList));
                }
            }
        }
        new BackupAsync().execute();
    }


    private boolean restoreDb( String databaseName) {
        File exportFile = DATA_DIRECTORY_DATABASE;
        File importFile = new File(DATABASE_DIRECTORY, databaseName);
        ;
        try {
            exportFile.createNewFile();
            copyFile(importFile, exportFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }

}

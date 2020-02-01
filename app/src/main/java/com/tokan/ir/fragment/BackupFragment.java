package com.tokan.ir.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tokan.ir.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackupFragment extends Fragment {


    public static final String PACKAGE_NAME = "com.tokan.ir";
    public static final String DATABASE_NAME = "MyDatabase.db";


    /**
     * Directory that files are to be read from and written to
     **/
    protected static final File DATABASE_DIRECTORY =
            new File(Environment.getExternalStorageDirectory(), "");

    /**
     * File path of Db to be imported
     **/
    protected static final File IMPORT_FILE =
            new File(DATABASE_DIRECTORY, "BackupMyDatabase.db");

    /**
     * Contains: /data/data/com.example.app/databases/example.db
     **/
    private static final File DATA_DIRECTORY_DATABASE =
            new File(Environment.getDataDirectory() +
                    "/data/" + PACKAGE_NAME +
                    "/databases/" + DATABASE_NAME);


    public BackupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_backup, container, false);
        ButterKnife.bind(this, view);

        view.findViewById(R.id.btn_Yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backup();
            }
        });

       /* btn_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restoreDb();
                System.out.println("restoreDb====" + restoreDb());
            }
        });*/
        return view;
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
                Toast.makeText(getActivity(), "Backup successfully", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.e("Error====", e.getMessage());
        }
    }


    private boolean restoreDb() {
        File exportFile = DATA_DIRECTORY_DATABASE;
        File importFile = IMPORT_FILE;
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

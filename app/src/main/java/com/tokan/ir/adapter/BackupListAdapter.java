package com.tokan.ir.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.entity.Backup;
import com.tokan.ir.widget.BTextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class BackupListAdapter extends RecyclerView.Adapter<BackupListAdapter.CustomView> {
    private Context context;
    private List<Backup> backupList = new ArrayList<>();
    public static final String PACKAGE_NAME = "com.tokan.ir";
    public static final String DATABASE_NAME = "MyDatabase.db";
    private ProgressDialog progress;

    private static final File DATA_DIRECTORY_DATABASE =
            new File(Environment.getDataDirectory() +
                    "/data/" + PACKAGE_NAME +
                    "/databases/" + DATABASE_NAME);

    /**
     * Directory that files are to be read from and written to
     **/
    protected static final File DATABASE_DIRECTORY =
            new File(Environment.getExternalStorageDirectory(), "");

    public BackupListAdapter(Context context, List<Backup> backupList) {
        this.context = context;
        this.backupList = backupList;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.backup_items_row, parent, false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {

        Backup backup = backupList.get(position);
        if (backup != null) {
            holder.txt_date.setText(backup.getBackupPath());
            holder.txt_restore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progress = new ProgressDialog(context);
                    progress.setMessage("Loading..."); // Setting Message
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progress.show(); // Display Progress Dialog
                    progress.setCancelable(false);

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progress.dismiss();
                            restoreDb(backup.getBackupPath());
                            //Toast.makeText(context, "با موفقیت انجام شد", Toast.LENGTH_LONG).show();
                        }
                    }).start();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return backupList.size();
    }

    class CustomView extends RecyclerView.ViewHolder {
        BTextView txt_date, txt_restore;

        public CustomView(@NonNull View itemView) {
            super(itemView);

            txt_date = itemView.findViewById(R.id.txt_date);
            txt_restore = itemView.findViewById(R.id.txt_restore);
        }
    }

    private boolean restoreDb(String databaseName) {
        File exportFile = DATA_DIRECTORY_DATABASE;
        File importFile = new File(DATABASE_DIRECTORY, databaseName);
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

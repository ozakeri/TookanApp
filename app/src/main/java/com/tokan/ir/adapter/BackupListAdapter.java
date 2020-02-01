package com.tokan.ir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.entity.Backup;
import com.tokan.ir.widget.BTextView;

import java.util.ArrayList;
import java.util.List;

public class BackupListAdapter extends RecyclerView.Adapter<BackupListAdapter.CustomView> {
    private List<Backup> backupList = new ArrayList<>();

    public BackupListAdapter(List<Backup> backupList) {
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
                    System.out.println("backup======" + backup.getBackupPath());
                    System.out.println("backup======" + backup.getCurrentDbPath());
                    System.out.println("backup======" + backup.getDatabaseName());
                    System.out.println("backup======" + backup.getPackageName());
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
}

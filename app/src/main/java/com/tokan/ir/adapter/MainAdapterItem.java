package com.tokan.ir.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapterItem extends RecyclerView.Adapter<MainAdapterItem.CustomView>{


    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class CustomView extends RecyclerView.ViewHolder{

        public CustomView(@NonNull View itemView) {
            super(itemView);
        }
    }
}

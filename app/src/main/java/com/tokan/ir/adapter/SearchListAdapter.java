package com.tokan.ir.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;
import com.tokan.ir.database.DatabaseClient;
import com.tokan.ir.entity.Customer;
import com.tokan.ir.widget.BTextView;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.CustomView> {

    private List<Customer> customerList = new ArrayList<>();

    public SearchListAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_items_row, parent, false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {

        Customer customer = customerList.get(position);
        if (customer != null) {
            holder.txt_name.setText(customer.getNameFamily());
            holder.txt_sex.setText(customer.getSex());
        }

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class CustomView extends RecyclerView.ViewHolder {

        BTextView txt_name, txt_sex;

        public CustomView(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_sex = itemView.findViewById(R.id.txt_sex);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    System.out.println("-+-+-+--+-+-");

                    return true;
                }
            });
        }
    }

}

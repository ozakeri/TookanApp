package com.tokan.ir.widget.drawer;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tokan.ir.R;

import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {

    private Context context;
    private List<DrawerItem> drawerItemList;
    private int layoutResID;
    private Handler handler;
    private View view;
    //Typeface tf;

    public DrawerAdapter(Context context, List<DrawerItem> listItems) {
        this.context = context;
        this.drawerItemList = listItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        DrawerItem dItem = this.drawerItemList.get(position);
        holder.itemLayout.setVisibility(LinearLayout.VISIBLE);
        //holder.icon.setImageDrawable(R.drawable);
        holder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgResID()));
        holder.ItemName.setText(dItem.getItemName());

    }

    @Override
    public int getItemCount() {
        return drawerItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ItemName;
        ImageView icon;
        LinearLayout itemLayout;

        MyViewHolder(View view) {
            super(view);
            ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
            icon = (ImageView) view.findViewById(R.id.drawer_icon);
            itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
        }

    }
}


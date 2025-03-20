package com.example.assignment2_zenderdiaz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    public ItemClickListener clickListener;
    List<Item> items;
    Context context;

    // context is like the parent
    public MyAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    public void setClickListener(ItemClickListener myListener) {
        this.clickListener = myListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new com.example.assignment2_zenderdiaz.MyViewHolder(itemView, this.clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.assignment2_zenderdiaz.MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.title.setText(item.getItemName());
        holder.description.setText(item.getItemDesc());
        holder.imageView.setImageResource(item.getItemImg());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

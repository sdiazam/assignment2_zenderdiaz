package com.example.assignment2_zenderdiaz;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ItemClickListener clickListener;
    ImageView imageView;
    TextView title;
    TextView description;

    public MyViewHolder(@NonNull View itemView, ItemClickListener clickListener) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageview);
        title = itemView.findViewById(R.id.title_txt);
        description = itemView.findViewById(R.id.description_text);

        this.clickListener = clickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "onViewHolder click");
                clickListener.onClick(view, getAdapterPosition());
            }
        });


    }

}

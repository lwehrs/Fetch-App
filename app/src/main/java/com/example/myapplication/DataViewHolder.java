package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewId;
    public TextView textViewListId;
    public TextView textViewName;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewId = itemView.findViewById(R.id.textViewId);
        textViewListId = itemView.findViewById(R.id.textViewListId);
        textViewName = itemView.findViewById(R.id.textViewName);
    }
}

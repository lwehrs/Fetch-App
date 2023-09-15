package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private List<JSONObject> data;

    public DataAdapter(List<JSONObject> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        JSONObject item = data.get(position);
        holder.textViewId.setText("ID: " + item.optString("id"));
        holder.textViewListId.setText("ListID: " + item.optString("listId"));
        holder.textViewName.setText("Name: " + item.optString("name"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Add a method to update the data in the adapter
    public void updateData(List<JSONObject> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }
}

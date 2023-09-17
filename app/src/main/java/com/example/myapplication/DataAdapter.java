package com.example.myapplication;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {
    private final List<JSONObject> data;

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
        Context context = holder.itemView.getContext();
        JSONObject item = data.get(position);
        holder.textViewId.setText(context.getString(R.string.id_str, item.optString("id")));
        holder.textViewListId.setText(context.getString(R.string.list_id_str, item.optString("listId")));
        holder.textViewName.setText(context.getString(R.string.name_str, item.optString("name")));
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

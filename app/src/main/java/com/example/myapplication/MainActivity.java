package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private List<JSONObject> filteredAndSortedData = new ArrayList<>();
    String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with an empty list
        dataAdapter = new DataAdapter(filteredAndSortedData);
        recyclerView.setAdapter(dataAdapter);

        // Fetch and display data
        fetchData();
    }

    private void fetchData() {
        // Create a request queue for network requests
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // Define a JSON request to fetch data from the URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    // Filter and sort the data
                    try {
                        filteredAndSortedData = filterAndSort(response);
                        // Update the RecyclerView adapter with the new data
                        dataAdapter.updateData(filteredAndSortedData);
                    } catch (JSONException e) {
                        // Handle JSON parsing error
                        e.printStackTrace();
                        showToastMessage("JSON parsing error");
                    }
                },
                error -> {
                    // Handle network errors
                    error.printStackTrace();
                    showToastMessage("Network error occurred");
                });
        queue.add(jsonArrayRequest);
    }

    private List<JSONObject> filterAndSort(JSONArray response) throws JSONException {
        List<JSONObject> mFilteredAndSortedData = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            JSONObject item = response.getJSONObject(i);
            String name = item.optString("name");
            if (name != null && !name.isEmpty() && !name.equals("null")) {
                mFilteredAndSortedData.add(item);
            }
        }

        mFilteredAndSortedData.sort((obj1, obj2) -> {
            int listId1 = obj1.optInt("listId");
            int listId2 = obj2.optInt("listId");
            int nameCompare = obj1.optString("name").compareTo(obj2.optString("name"));

            if (listId1 != listId2) {
                return Integer.compare(listId1, listId2);
            } else {
                return nameCompare;
            }
        });

        return mFilteredAndSortedData;
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

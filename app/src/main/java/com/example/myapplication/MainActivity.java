package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        textView = findViewById(R.id.textView);

        // Create a request queue for network requests
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // Define a JSON request to fetch data from the URL
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Filter and sort the data
                List<JSONObject> filteredAndSortedData = new ArrayList<>();
                try {
                    filteredAndSortedData = filterAndSort(response);
                } catch (JSONException e) {
                    // Filter Sort failed
                    e.printStackTrace();
                    // Display toast message for error
                    showToastMessage(MainActivity.this, "Filter Sort failed");
                }

                // Display the data in the TextView
                displayDataInTextView(filteredAndSortedData);
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle network errors
                error.printStackTrace();
                // Display toast message for the error
                showToastMessage(MainActivity.this, "Network error occurred");
            }
        });
        queue.add(jsonArrayRequest);
    }

    private List<JSONObject> filterAndSort(JSONArray response) throws JSONException {
        List<JSONObject> filteredAndSortedData = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            JSONObject item = response.getJSONObject(i);
            String name = item.optString("name");
            if (name != null && !name.isEmpty()  && name != "null"){
                filteredAndSortedData.add(item);
            }
        }

        Collections.sort(filteredAndSortedData, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject obj1, JSONObject obj2) {
                int listId1 = obj1.optInt("listId");
                int listId2 = obj2.optInt("listId");
                int nameCompare = obj1.optString("name").compareTo(obj2.optString("name"));

                if(listId1 != listId2) {
                    return Integer.compare(listId1, listId2);
                } else {
                    return nameCompare;
                }
            }
        });

        return filteredAndSortedData;
    }

    private void displayDataInTextView(List<JSONObject> data) {
        // Display the data in the TextView
        for (int i = 0; i < data.size(); i++) {
            JSONObject item = data.get(i);
            String itemId = item.optString("id");
            textView.append("ID: " + itemId );
            String itemListId = item.optString("listId");
            textView.append(" ListID: " + itemListId );
            String itemName = item.optString("name");
            textView.append(" Name: " + itemName + "\n");
        }
    }

    private void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
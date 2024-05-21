package com.example.dbtodevicetest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        fetchButton = findViewById(R.id.fetchButton);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
    }

    private void fetchData() {
        String url = "http://ec2-54-167-37-239.compute-1.amazonaws.com:3000/data";
        Log.d("MainActivity", "URL: " + url);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("MainActivity", "Response: " + response.toString());
                        try {
                            // Convert JSONArray to JSON string
                            String jsonString = response.toString();
                            // Parse JSON string with Gson
                            Gson gson = new Gson();
                            List<ytURL> youTubeUrls = gson.fromJson(jsonString, new TypeToken<List<ytURL>>(){}.getType());

                            // Display the data in a readable format
                            resultTextView.setText(youTubeUrls.toString());
                        } catch (JsonSyntaxException e) {
                            resultTextView.setText("Parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "Error: " + error.getMessage());
                        resultTextView.setText("Error: " + error.getMessage());
                    }
                }
        );

        queue.add(jsonArrayRequest);
        Log.d("MainActivity", "Request added to queue");
    }


}
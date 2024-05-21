package com.example.dbtodevicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dbtodevicetest.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    final private String dbURL = "http://ec2-54-167-37-239.compute-1.amazonaws.com:3000/data";

    private ActivityMainBinding binding;

    private TextView urlDisplay;
    private TextView titleDisplay;
    private TextView channelNameDisplay;
    private Button nextBtn;
    private List<ytURL> youTubeUrls;
    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        urlDisplay = findViewById(R.id.urlDisplay);
        titleDisplay = findViewById(R.id.titleDisplay);
        channelNameDisplay = findViewById(R.id.channelNameDisplay);
        nextBtn = findViewById(R.id.nextBtn);

        // Fetch data initially
        fetchData();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (youTubeUrls != null && !youTubeUrls.isEmpty()) {
                    counter = (counter + 1) % youTubeUrls.size();
                    displayData(counter);
                }
            }
        });
    }

    private void fetchData() {
        Log.d("MainActivity", "URL: " + dbURL);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, dbURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("MainActivity", "Response: " + response.toString());
                        try {
                            // Convert JSONArray to JSON string
                            String jsonString = response.toString();
                            // Parse JSON string with Gson
                            Gson gson = new Gson();
                            youTubeUrls = gson.fromJson(jsonString, new TypeToken<List<ytURL>>(){}.getType());

                            // Display the first item initially
                            if (!youTubeUrls.isEmpty()) {
                                displayData(counter);
                            }
                        } catch (JsonSyntaxException e) {
                            Log.d("MainActivity", "Parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MainActivity", "Error: " + error.getMessage());
                    }
                }
        );

        queue.add(jsonArrayRequest);
        Log.d("MainActivity", "Request added to queue");
    }

    private void displayData(int index) {
        if (youTubeUrls != null && index < youTubeUrls.size()) {
            ytURL youTubeUrl = youTubeUrls.get(index);
            urlDisplay.setText(youTubeUrl.getUrl());
            titleDisplay.setText(youTubeUrl.getTitle());
            channelNameDisplay.setText(youTubeUrl.getChannelName());
        }
    }
}

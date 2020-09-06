package com.example.anotherweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity {

    //Fields specific to the XML layout
    TextView mTemperature, mWeatherDescription, mLocation;
    EditText mLocationInput;
    Button mRefresh;
    Weather currentWeather;

    // Utilizing the OpenWeather API (free version)
    private final static String API_LINK = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static String API_location = "Pittsburgh,us"; //Initialized as Pittsburgh, user can now change the location
    private final static String API_KEY = "[user key]"; //Key deleted from file prior to commit

    //Volley documentation --> https://developer.android.com/training/volley
    //Initializing a new RequestQueue
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTemperature = (TextView)findViewById(R.id.tvTemperature);
        mWeatherDescription = (TextView)findViewById(R.id.tvWeatherDescription);
        mLocation = (TextView)findViewById(R.id.tvLocation);
        mLocationInput = (EditText)findViewById(R.id.etLocationInput);

        mRefresh = (Button)findViewById(R.id.bRefresh);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API_location = mLocationInput.getText().toString();
                Fetcher();
                Displayer();
            }
        });

        Fetcher();
        Displayer();
    }

    public void Fetcher(){
        String url = API_LINK + API_location + API_KEY;
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject weatherCondition = weather.getJSONObject(0);
                    String description = weatherCondition.getString("main");
                    JSONObject main = response.getJSONObject("main");
                    double temp = main.getDouble("temp");
                    String location = response.getString("name");
                    currentWeather = new Weather(temp, description, location);
                } catch (JSONException e) {
                    onFetcherError();
                    Toast.makeText(getBaseContext(), "Failed to parse JSON Data", Toast.LENGTH_LONG).show();
                }//end catch
            }// end onResponse
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onFetcherError();
                Toast.makeText(getBaseContext(), "Volley error", Toast.LENGTH_LONG).show();
            }// end ErrorListener
        });// end new JsonObjectRequest
        queue.add(jsonObjectRequest);
    }

    public void Displayer(){
        mTemperature.setText(Weather.tempToString());
        mWeatherDescription.setText(Weather.getWeatherDescription());
        mLocation.setText(Weather.getWeatherLocation());
    }

    public void onFetcherError(){
        mTemperature.setText("[temp]");
        mWeatherDescription.setText("[description]");
        mLocation.setText("[location]");
    }
}
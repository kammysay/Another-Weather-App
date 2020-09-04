package com.example.anotherweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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
    Weather currentWeather;

    //Initializing fields.  Just basic stuff for now.
    private final static String API_LINK = "https://api.openweathermap.org/data/2.5/weather?q=";
    private final static String API_LOCATION = "Pittsburgh,us"; //temp hardcoded location, will add location selector shortly
    private final static String API_KEY = "&APPID=b9314c460681bbf92233c1bedcc701c4"; //delete from file before posting to GitHub
    private static String url;
    // Utilizing the OpenWeather API (free version)
    // https://api.openweathermap.org/data/2.5/weather?q=Pittsburgh,us&APPID=b9314c460681bbf92233c1bedcc701c4   <-- Whole URL

    //Volley documentation --> https://developer.android.com/training/volley
    //Initializing a new RequestQueue
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //queue = Volley.newRequestQueue(this);
        url = API_LINK + API_LOCATION +API_KEY;

        Fetcher();

        mTemperature = (TextView)findViewById(R.id.tvTemperature);
        mTemperature.setText(Weather.tempToString());

        mWeatherDescription = (TextView)findViewById(R.id.tvWeatherDescription);
        mWeatherDescription.setText(Weather.getWeatherDescription());

        mLocation = (TextView)findViewById(R.id.tvLocation);
        mLocation.setText(API_LOCATION);
    }//End of onCreate()

    public void Fetcher(){
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject weatherCondition = weather.getJSONObject(0);
                    int conditionID = weatherCondition.getInt("id");
                    String conditionDescription = weatherCondition.getString("description");
                    mWeatherDescription.setText(conditionDescription);
                    JSONArray main = response.getJSONArray("main");
                    double temp = main.getDouble(0);
                    //JSONArray sys = response.getJSONArray("sys");
                    //String name = weather.getString("name");
                    currentWeather = new Weather(temp, conditionDescription, API_LOCATION);
                } catch (JSONException e) {
                    mTemperature.setText("JSON Try/Catch");
                    mWeatherDescription.setText("JSON Try/Catch");
                    mLocation.setText("JSON Try/Catch");
                    //e.printStackTrace();
                }//end catch
            }// end onResponse
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Handle Error, nothing for now
                mTemperature.setText(error.toString());
                mWeatherDescription.setText("onErrorResponse");
                mLocation.setText("onErrorResponse");
            }// end ErrorListener
        });// end new JsonObjectRequest
        queue.add(jsonObjectRequest);
    }

/*
    //Sumthin in here messed up.  Maybe try Simple volley request instead of Standard
    public void Fetcher(){
        String url = API_LINK + API_LOCATION +API_KEY;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mWeatherDescription.setText("onResponse");
                    mTemperature.setText("onResponse");
                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject weatherCondition = weather.getJSONObject(0);
                    int conditionID = weatherCondition.getInt("id");
                    String conditionDescription = weatherCondition.getString("description");
                    JSONObject main = response.getJSONObject("main");
                    double temp = main.getDouble("temp");
                    //JSONArray sys = response.getJSONArray("sys");
                    //String name = weather.getString("name");
                    currentWeather = new Weather(temp, conditionDescription, API_LOCATION);
                } catch (JSONException e) {
                    mTemperature.setText("Whoopsies!");  //These are just for debugging.
                    mWeatherDescription.setText("Whoopsies!");
                    mLocation.setText("Whoopsies!");
                    //e.printStackTrace();
                }//end catch
            }// end onResponse
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Handle Error, nothing for now
                mTemperature.setText("Whoopsies!");
                mWeatherDescription.setText("Whoopsies!");
                mLocation.setText("Whoopsies!");
            }// end ErrorListener
        });// end new JsonObjectRequest
    }
*/
}
package com.example.anotherweatherapp;

public class Weather {

    static double mTemperature;
    static String mWeatherDescription, mWeatherLocation;

    //Constructor
    public Weather(double temp, String description, String location){
        mTemperature = temp;
        mWeatherDescription = description;
        mWeatherLocation = location;
    }

    public static double getTemperature() {
        return mTemperature;
    }

    public static String tempToString(){
        return String.valueOf(mTemperature);
    }

    public static String getWeatherDescription() {
        return mWeatherDescription;
    }

    public static String getWeatherLocation() {
        return mWeatherLocation;
    }


}

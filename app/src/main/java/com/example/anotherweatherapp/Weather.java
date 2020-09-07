package com.example.anotherweatherapp;

public class Weather {

    static double mTemperature, mRealFeel;
    static String mWeatherDescription, mWeatherLocation;

    //Constructor
    public Weather(double temp, double feels_like, String description, String location){
        mTemperature = temp;
        mRealFeel = feels_like;
        mWeatherDescription = description;
        mWeatherLocation = location;
    }

    public static double getTemperature() {
        return mTemperature;
    }

    public static String tempToString(){
        mTemperature = kelvinToFahrenheit(mTemperature); //Converts Kevlin to Fahrenheit
        return String.valueOf((int)mTemperature);
    }

    public static double getRealFeel() {
        return mRealFeel;
    }

    public static String realFeelToString(){
        mRealFeel = kelvinToFahrenheit(mRealFeel);
        return String.valueOf((int)mRealFeel);
    }

    public static String getWeatherDescription() {
        return mWeatherDescription;
    }

    public static String getWeatherLocation() {
        return mWeatherLocation;
    }

    public static double kelvinToFahrenheit(double input){
        return (input - 273.15)*1.8 +32; //Kevlin to Fahrenheit Formula
    }
}

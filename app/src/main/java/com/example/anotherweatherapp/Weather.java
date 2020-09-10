package com.example.anotherweatherapp;


public class Weather {

    static double mTemperature, mRealFeel;
    static int mID = 0;
    static String mIconURL, mWeatherDescription, mWeatherLocation, mColor;

    //Constructor
    public Weather(double temp, double feels_like, int weatherID, String icon, String description, String location){
        mTemperature = temp;
        mRealFeel = feels_like;
        mID = weatherID;
        mIconURL = setIconURL(icon);
        mWeatherDescription = description;
        mWeatherLocation = location;
        mColor = setColor(weatherID);
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

    public static double kelvinToFahrenheit(double input){
        return (input - 273.15)*1.8 +32; //Kevlin to Fahrenheit Formula
    }

    public static int getWeatherID(){
        return mID;
    }

    public static String setIconURL(String iconID){
        return "https://openweathermap.org/img/wn/" + iconID + "@2x.png";
    }

    public static String getIconURL(){
        return mIconURL;
    }

    public static String getWeatherDescription() {
        return mWeatherDescription;
    }

    public static String getWeatherLocation() {
        return mWeatherLocation;
    }

    public static String getColor(){
        return mColor;
    }

    public static String setColor(int id){
        String color;
        if(id>=200 && id<300){ color = "thunderStorm";}
        else if(id>=300 && id<600){ color = "drizzleOrRain";}
        else if(id>=600 && id<700){ color = "snow";}
        else{ color = "clearClouds";}
        return color;
    }

    public static boolean readyToDisplay(){
        if(mID==0){
            return false;
        }else return true;
    }
}
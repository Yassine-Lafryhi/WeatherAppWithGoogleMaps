package com.weather.app.with.google.maps;

public class WeatherItem {

    public int temperature;
    public int maximumTemperature;
    public int minimumTemperature ;
    public int pressure;
    public String image;
    public String date;

    public WeatherItem(int temperature, int maximumTemperature, int minimumTemperature, int pressure, String image, String date) {
        this.temperature = temperature;
        this.maximumTemperature = maximumTemperature;
        this.minimumTemperature = minimumTemperature;
        this.pressure = pressure;
        this.image = image;
        this.date = date;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(int maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public int getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(int minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.example.sneha.gpsreminder;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sneha on 4/26/17.
 */

public class Task {
    private String name;
    private String description;
    private String location;
    private double latitude;
    private double longitude;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

}

package com.elorrieta.didaktikapp.model.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity
public class PlaceOfInterest {

    @PrimaryKey
    public int id;

    public String name;

    public double latitude;

    public double longitude;

    public PlaceOfInterest(int id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PlaceOfInterest(int id, String name, LatLng latLng) {
        this.id = id;
        this.name = name;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}

package com.elorrieta.didaktikapp.model.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

@Entity
public class PlaceOfInterest implements Serializable {

    @PrimaryKey
    public int idPoI;

    public String name;

    public double latitude;

    public double longitude;

    public PlaceOfInterest(int idPoI, String name, double latitude, double longitude) {
        this.idPoI = idPoI;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Ignore
    public PlaceOfInterest(int id, String name, LatLng latLng) {
        this.idPoI = id;
        this.name = name;
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}

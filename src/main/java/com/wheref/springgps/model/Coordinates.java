package com.wheref.springgps.model;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private float lat;
    private float lng;
    public float getLat() {
        return lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public float getLng() {
        return lng;
    }
    public void setLng(float lng) {
        this.lng = lng;
    }
    
    @Override
    public String toString() {
        return "Coordinates [lat=" + lat + ", lng=" + lng + "]";
    }

}

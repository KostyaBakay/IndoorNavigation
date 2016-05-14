package com.example.fooandbar.ibeacon.model;

/**
 * Created by rsv on 14.05.2016.
 */
public class UserDevice {
    private String name;
    private String idBeacon;
    private String userID;
    private double distance;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdBeacon() {
        return idBeacon;
    }

    public void setIdBeacon(String idBeacon) {
        this.idBeacon = idBeacon;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}

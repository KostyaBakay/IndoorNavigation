package com.example.fooandbar.ibeacon.model;


public class UserDevice {
    private String name;
    private String idBeacon;
    private String userID;
    private double distance;
    private int sex;


    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

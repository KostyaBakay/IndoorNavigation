package com.example.fooandbar.ibeacon.model;

import java.util.Map;

public class Room {

    private String name;
    private Map<String, UserDevice> userDevices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, UserDevice> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(Map<String, UserDevice> userDevices) {
        this.userDevices = userDevices;
    }
}
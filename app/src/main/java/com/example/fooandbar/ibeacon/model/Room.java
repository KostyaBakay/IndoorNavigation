package com.example.fooandbar.ibeacon.model;

public class Room {
    private String mName;
    private String mBeaconAlias;
    private int mUsersInRoom;

    public int getmUsersInRoom() {
        return mUsersInRoom;
    }

    public void setmUsersInRoom(int mUsersInRoom) {
        this.mUsersInRoom = mUsersInRoom;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAlias() {
        return mBeaconAlias;
    }

    public void setAlias(String mAlias) {
        this.mBeaconAlias = mAlias;
    }
}
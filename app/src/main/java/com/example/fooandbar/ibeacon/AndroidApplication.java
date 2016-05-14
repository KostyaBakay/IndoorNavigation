package com.example.fooandbar.ibeacon;

import android.app.Application;

import com.firebase.client.Firebase;

public class AndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}

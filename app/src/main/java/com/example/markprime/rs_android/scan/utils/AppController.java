package com.example.markprime.rs_android.scan.utils;

import android.app.Application;
import com.example.markprime.rs_android.scan.networking.NetworkManager;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NetworkManager.getInstance(this);
    }

}

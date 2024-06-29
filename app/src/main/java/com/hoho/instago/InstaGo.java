package com.hoho.instago;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class InstaGo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

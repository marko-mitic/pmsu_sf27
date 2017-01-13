package com.example.mitke.pmsu_sf27.app;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.facebook.stetho.Stetho;

/**
 * Created by mitke on 09-Jan-17.
 * Init of stetho inspector
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

// Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );




// Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

// Initialize Stetho with the Initializer
        Stetho.initialize(initializer);

    }
}

package com.example.mitke.pmsu_sf27.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mitke.pmsu_sf27.R;
import com.example.mitke.pmsu_sf27.db.DataBaseHelper;

import java.net.MalformedURLException;


public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    public static final int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            try {
                DataBaseHelper.insertTestValues(this.getApplicationContext());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(sharedPreferences.getBoolean(getString(R.string.pref_splash_key),false))
        {

            runMain(null);

        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    runMain(null);

                    finish();
                }
            }, SPLASH_TIME_OUT);

        }
    }

    public void runMain(View v) {
        Intent restourantsActivity = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(restourantsActivity);
        finish();
    }
}

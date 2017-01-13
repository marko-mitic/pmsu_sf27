package com.example.mitke.pmsu_sf27.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.mitke.pmsu_sf27.R;

import java.util.Random;

public class OrderService extends Service {
    private static final int ORDER_TIME_OUT = 10000;
    public OrderService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sendNotification();
            }
        }, ORDER_TIME_OUT);

        stopSelf();
        return START_NOT_STICKY;
    }

    

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void sendNotification() {
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Order successful")
                .setContentText("You will receive your order on your address in 30 minutes")
                .setSmallIcon(R.drawable.ic_add_shopping_cart_black_24dp)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);

    }
}


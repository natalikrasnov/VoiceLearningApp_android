package com.natali.voicelearningapp.mainApp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class App extends Application {
   public static final String CHANNAL_ID = "serviceChannel";
    public static final String NOTIFICATION_CHANNEL_ID = "my_channel_id";
    public static final String channelName = "My Background Service";
    @Override
    public void onCreate() {
        super.onCreate();
        createNothificationChannel();
        Log.i("tag", "onCreate: Application");
    }

    private void createNothificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }
}

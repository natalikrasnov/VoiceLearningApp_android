package com.natali.voicelearningapp.resiverAndServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.natali.voicelearningapp.KidApp.Mainh;
import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.KidData.MAin.Person;
import static com.natali.voicelearningapp.mainApp.App.*;


public class ServiceForLooperGetApps extends Service {
    private static final String TAG = "StickyService";

    private static Person curentPerson;



    public ServiceForLooperGetApps(Person person) {
        curentPerson = person;
    }


    public static Person getCurentPerson() {
        return curentPerson;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, new Intent(this, Mainh.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(chan);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("שלום!")
                    .setContentText("כרגע יש לך " + curentPerson.getPoints() + " נקודות :)")
                    .setSmallIcon(R.drawable.app_icon)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(2, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,new Intent(this, Mainh.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(this, CHANNAL_ID)
                    .setContentTitle("שלום!")
                    .setContentText( "כרגע יש לך "+ curentPerson.getPoints()+" נקודות :)")
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);
        }
        //for stopping this service call ->  stopSelf();
        new CheckRunningActivityThread(this, new Handler()).start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onSDestroy");
        FirebaseData.setNewEvent("נסגרה האפליקציה של הילד/ה... כדי להתחיל אותה מחדש פשוט תפעיל אותה מחדש בפלאפון של היל/ה שלך.");
    }
}

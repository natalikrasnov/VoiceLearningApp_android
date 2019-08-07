package com.natali.voicelearningapp.data.KidData.MAin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import com.natali.voicelearningapp.KidApp.Mainh;
import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.QueryPreferences;
import static com.natali.voicelearningapp.mainApp.App.CHANNAL_ID;
import static com.natali.voicelearningapp.mainApp.App.NOTIFICATION_CHANNEL_ID;
import static com.natali.voicelearningapp.mainApp.App.channelName;

public class Person {
    private String userFirebaseID;
    private int points;
    private Lesson[] lessons;
    private Lesson currentLesson;

    private Context context;

    public String getUserFirebaseID() {
        return userFirebaseID;
    }

    public void setUserFirebaseID(String userFirebaseID) {
        this.userFirebaseID = userFirebaseID;
    }

    public Person(Context context) {
        this.context = context;
        this.setPoints(QueryPreferences.getPerson_points(context));
        lessons = getFromShardPref();
    }

    public Lesson[] getFromShardPref(){
        return new Lesson[]{new Lesson(WordsBank.lessons[0],context), new Lesson(WordsBank.lessons[1],context)};
    }

    public void setCurrentLesson(Lesson currentLesson) {
        this.currentLesson = currentLesson;
    }

    public Lesson[] getLessons() {
        return lessons;
    }

    public Lesson getCurrentLesson() {
        return currentLesson;
    }

    public int getPoints() {
        return QueryPreferences.getPerson_points(context);
    }

    public void setPoints(int points) {
        this.points = points;
        QueryPreferences.setPrefPersonPoints(context,points);
    }

    public void addOnePoint(){
        setPoints(getPoints()+1);
        currentLesson.didThisMision();
        if(currentLesson.getLessonName().equals(lessons[0].getLessonName())) {
            lessons[0] = currentLesson;
            lessons[0].save();
        }else {
            lessons[1] = currentLesson;
            lessons[1].save();
        }
        FirebaseData.setNewEvent("סהכ הנקודות שיש לילד/ה שלך הוא: "+getPoints()+ ",נמצא כרגע בשיעור: "+currentLesson.getLessonNumber());

        updateNotification();
    }

    public void updateNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, new Intent(context, Mainh.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(chan);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("שלום! אתה כרגע לומד: " + getCurrentLesson().getLessonName())
                    .setContentText("כרגע יש לך " + getPoints() + " נקודות :)")
                    .setSmallIcon(R.drawable.app_icon)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setContentIntent(pendingIntent)
                    .build();
            mNotificationManager.notify(2, notification);
        } else {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,new Intent(context, Mainh.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(context, CHANNAL_ID)
                    .setContentTitle("שלום! אתה כרגע לומד: " + getCurrentLesson().getLessonName())
                    .setContentText( "כרגע יש לך "+ getPoints()+" נקודות :)")
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentIntent(pendingIntent)
                    .build();
            mNotificationManager.notify(1, notification);
        }
    }



}

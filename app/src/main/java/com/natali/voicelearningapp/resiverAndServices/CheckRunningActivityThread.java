package com.natali.voicelearningapp.resiverAndServices;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;

import android.os.*;
import android.provider.Telephony;

import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.KidData.MAin.GamesLayout;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.content.Context.USAGE_STATS_SERVICE;

public class CheckRunningActivityThread extends Thread{

    UsageStatsManager mUsageStatsManager;
    ActivityManager activityManager ;
    Context context;
    Handler handleFromMainActivty;
    String smsPackageName;

    @SuppressLint({"InlinedApi", "ServiceCast"})
    public CheckRunningActivityThread( Context context, Handler handler) {
        this.context = context;
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        handleFromMainActivty = handler;
        mUsageStatsManager = (UsageStatsManager) context.getSystemService(USAGE_STATS_SERVICE); //Context.USAGE_STATS_SERVICE
        smsPackageName = Telephony.Sms.getDefaultSmsPackage(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void run() {
        super.run();
        String LastPackageName = "";
        while(true) {
            long currentTime = System.currentTimeMillis();
            // get usage stats for the last 10 seconds
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 1000 * 10, currentTime);
            SortedMap<Long,UsageStats> mySortedMap = new TreeMap<>();
                    for (UsageStats usageStats : stats) {
                        mySortedMap.put(usageStats.getLastTimeUsed(),usageStats);
                    }
                    if(!mySortedMap.isEmpty()) {
                        String topPackageName =  mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                        if (!LastPackageName.equals(topPackageName)){
                            String finalLastPackageName = LastPackageName;
                            if(finalLastPackageName.equals(smsPackageName))
                                startIntent();
                            switch (finalLastPackageName){
                                case "com.whatsapp":
                                    FirebaseData.setNewEvent("הילד/ה יצא מהווטסאפ");
                                    startIntent();
                            }
                            LastPackageName = topPackageName;
                        }
                    }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }

        private void startIntent(){
            handleFromMainActivty.post(() -> {
                FirebaseData.setNewEvent("הילד/ה קיבל תרגיל");
                context.startActivity(GamesLayout.getRandomIntent());
            });
        }
}

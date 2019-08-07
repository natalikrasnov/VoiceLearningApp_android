package com.natali.voicelearningapp.resiverAndServices;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.KidData.MAin.GamesLayout;


public class TellService extends Service {

    private static boolean isThereWasCall = false;
    private static boolean isThisCallISOutCal = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("service", "destroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("StartService", "TService");
        return super.onStartCommand(intent, flags, startId);
    }

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;

    public abstract static class PhonecallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("reciver: ", "start: this is the action: "+intent.getAction());
        //TODO:    if(!QueryPreferences.isAlarmOn(context))return;
            //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.

            if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
                String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
                int state = 0;
                if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    state = TelephonyManager.CALL_STATE_IDLE;
                } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    state = TelephonyManager.CALL_STATE_OFFHOOK;
                } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    state = TelephonyManager.CALL_STATE_RINGING;
                }
                onCallStateChanged(context, state);
            }

        }
        //Derived classes should override these to respond to specific events of interest
        protected abstract void onIncomingCallReceived(Context ctx);
        protected abstract void onIncomingCallAnswered(Context ctx);
        protected abstract void onIncomingCallEnded(Context ctx);
        protected abstract void onOutgoingCallStarted(Context ctx);
        protected abstract void onOutgoingCallEnded(Context ctx);
        protected abstract void onMissedCall(Context ctx);
        protected abstract void onReadSMS(Context ctx);

        //Deals with actual events
        //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
        //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up

        public void onCallStateChanged(Context context, int state) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    isThereWasCall = true;
                    onIncomingCallReceived(context);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    isThereWasCall = true;
                    //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                    if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                        isThisCallISOutCal = true;
                        onOutgoingCallStarted(context);
                    } else {
                        onIncomingCallAnswered(context);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if(!isThereWasCall) break;
                    //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                    if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                        //Ring but no pickup-  a miss
                        onMissedCall(context);
                    } else if (!isThisCallISOutCal) {
                        onIncomingCallEnded(context);
                    } else {
                        onOutgoingCallEnded(context);
                    }
                    break;
            }
            lastState = state;
            Log.i("  ", "onCallStateChanged: "+state);
        }
    }

    public static class CallReceiver extends PhonecallReceiver {
        @Override
        protected void onIncomingCallReceived(Context ctx) {
           // Toast.makeText(ctx, "onIncomingCallReceived", Toast.LENGTH_LONG).show();
            FirebaseData.setNewEvent("מישהוא מתקשר לילד/ה שלך");

        }
        @Override
        protected void onIncomingCallAnswered(Context ctx) {
//            Toast.makeText(ctx, "onIncomingCallAnswered", Toast.LENGTH_LONG).show();
            FirebaseData.setNewEvent("הילד/ה שלך ענה לפלאפון");
        }
        @Override
        protected void onIncomingCallEnded(Context ctx) {
//            Toast.makeText(ctx, "onIncomingCallEnded", Toast.LENGTH_LONG).show();
            FirebaseData.setNewEvent("נגמרה השיחה");
            showDialogs(ctx);
        }
        @Override
        protected void onOutgoingCallStarted(Context ctx) {
//            Toast.makeText(ctx, "onOutgoingCallStarted", Toast.LENGTH_LONG).show();
            FirebaseData.setNewEvent("הילד/ה שלך מתקשר למישהוא");
        }
        @Override
        protected void onOutgoingCallEnded(Context ctx) {
//            Toast.makeText(ctx, "nOutgoingCallEnded", Toast.LENGTH_LONG).show();
            FirebaseData.setNewEvent("נגמרה השיחה");
            showDialogs(ctx);
        }
        @Override
        protected void onMissedCall(Context ctx) {
//            Toast.makeText(ctx, "onMissedCall", Toast.LENGTH_LONG).show();
            FirebaseData.setNewEvent("הילד/ה שלך ניתק");
        }

        @Override
        protected void onReadSMS(Context ctx) {
            FirebaseData.setNewEvent("הילד/ה קיבל סמס");
            showDialogs(ctx);
        }

        void showDialogs(Context ctx){
            FirebaseData.setNewEvent("הילד/ה קיבל תרגיל");
            ctx.startActivity(GamesLayout.getRandomIntent());
        }
    }
}




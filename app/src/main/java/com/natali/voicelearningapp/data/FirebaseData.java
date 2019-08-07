package com.natali.voicelearningapp.data;

import android.util.Log;

import com.google.firebase.database.*;
import com.natali.voicelearningapp.parentApp.ParentListFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

public class FirebaseData {

    private static DatabaseReference mDatabase;
    private static String userID;
    private static Hashtable<String,String> resultList = new Hashtable<String,String>();

    public static Hashtable<String, String> getResultList() {
        return resultList;
    }

    public FirebaseData(String userID) {
        FirebaseData.userID = userID;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void writeNewUser() {
        mDatabase.child("users").child(userID).setValue(" ");
    }


    public static void setNewEvent( String newEvent) {
        Date dateAndTime= Calendar.getInstance().getTime();
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.MEDIUM, Locale.getDefault());
        mDatabase.child("users").child(userID).child(format.format(dateAndTime)).setValue(newEvent);
    }

    public static void getEvents(ParentListFragment complition) {
        resultList = new Hashtable<String,String>();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    for (DataSnapshot usersSnapshot : postSnapshot.getChildren()) {
                        if (usersSnapshot.getKey().equals(userID)) {
                            Log.w("firebase: ", usersSnapshot.getValue() + "");
                            for (DataSnapshot lineData : usersSnapshot.getChildren()) {
                                resultList.put(lineData.getKey(), lineData.getValue().toString());
                                Log.w("result list: ", resultList.toString());
                            }
                        }
                    }
                }
                complition.updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("firebase: ", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);
    }
}

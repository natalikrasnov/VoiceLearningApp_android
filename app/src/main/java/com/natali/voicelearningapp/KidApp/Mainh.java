package com.natali.voicelearningapp.KidApp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.KidData.MAin.GamesLayout;
import com.natali.voicelearningapp.data.KidData.MAin.Lesson;
import com.natali.voicelearningapp.data.KidData.MAin.Person;
import com.natali.voicelearningapp.data.QueryPreferences;
import com.natali.voicelearningapp.resiverAndServices.ServiceForLooperGetApps;

import java.util.ArrayList;

public class Mainh extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    Button numbersButton, lettersButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"CommitPrefEdits", "SetTextI18n"})
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        findViewById(R.id.showInfo).setOnClickListener(v -> showStartingDialogs());
        if (!hasSelfPermision())
            showStartingDialogs();
         else
            if (!isAccessGranted())
                showPermishionsDialog();

        if (GamesLayout.getAllIntents() == null) initIntentsFOrGames();

        ((TextView) findViewById(R.id.points)).setText("נקודות: " + QueryPreferences.getPerson_points(this));

        if(hasSelfPermision()){
            FirebaseData.setNewEvent("הילד/ה פתח את המסך הראשי");

            ServiceForLooperGetApps.getCurentPerson().setCurrentLesson(new Lesson("אותיות", Mainh.this));
            ServiceForLooperGetApps.getCurentPerson().updateNotification();

            numbersButton = findViewById(R.id.buttonNumbers);
            lettersButton = findViewById(R.id.buttonLetters);
            numbersButton.setOnClickListener(v -> {
                Drawable forReplace = lettersButton.getBackground();
                lettersButton.setBackground(v.getBackground());
                v.setBackground(forReplace);
                ServiceForLooperGetApps.getCurentPerson().setCurrentLesson(new Lesson(((TextView) v).getText() + "", Mainh.this));
                ServiceForLooperGetApps.getCurentPerson().updateNotification();
                FirebaseData.setNewEvent("הילד/ה בחר ללמוד מספרים");

            });
            lettersButton.setOnClickListener(v -> {
                Drawable forReplace = numbersButton.getBackground();
                numbersButton.setBackground(v.getBackground());
                v.setBackground(forReplace);
                ServiceForLooperGetApps.getCurentPerson().setCurrentLesson(new Lesson(((TextView) v).getText() + "", Mainh.this));
                ServiceForLooperGetApps.getCurentPerson().updateNotification();
                FirebaseData.setNewEvent("הילד/ה בחר ללמוד אותיות");

            });
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void showStartingDialogs(){
        new AlertDialog.Builder(this)
                .setMessage(R.string.welcome)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        checkingPermishion();
                    }
                })
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initIntentsFOrGames(){
        new GamesLayout(this);
        if(isAccessGranted()){
            ServiceForLooperGetApps mService = new ServiceForLooperGetApps(new Person(this));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
                startService(new Intent(this, mService.getClass()));
            else ContextCompat.startForegroundService(this, new Intent(this, mService.getClass()));
            ServiceForLooperGetApps.getCurentPerson().setCurrentLesson(new Lesson("אותיות", Mainh.this));
            ServiceForLooperGetApps.getCurentPerson().updateNotification();
            new FirebaseData(FirebaseAuth.getInstance().getCurrentUser().getUid()).writeNewUser();
            FirebaseData.setNewEvent("הילד/ה התחיל בפעם הראשונה את האפליקציה");
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //permissions:

    private void showPermishionsDialog(){
        new AlertDialog.Builder(this)
                .setTitle("הרשאות:")
                .setMessage("אנחנו צריכים שתאשר בבקשה את ההרשאה שתוצג לך בעוד רגע..")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 173);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void checkingPermishion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasSelfPermision()) {
            ArrayList<String> permissions = new ArrayList<>();
            if (Mainh.this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                permissions.add(Manifest.permission.READ_PHONE_STATE);
//            if (Mainh.this.checkSelfPermission(Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED)
//                permissions.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
            if (this.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                permissions.add(Manifest.permission.RECORD_AUDIO);
//            if (this.checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
//                permissions.add(Manifest.permission.RECEIVE_SMS);
            ActivityCompat.requestPermissions(Mainh.this, permissions.toArray(new String[permissions.size()]), REQUEST_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected  boolean hasSelfPermision() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS ) == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 173) {
            initIntentsFOrGames();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(!isAccessGranted())
                showPermishionsDialog();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}

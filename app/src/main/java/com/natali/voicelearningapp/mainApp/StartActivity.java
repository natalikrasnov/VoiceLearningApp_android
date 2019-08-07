package com.natali.voicelearningapp.mainApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.natali.voicelearningapp.KidApp.Mainh;
import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.FirebaseData;
import com.natali.voicelearningapp.data.QueryPreferences;
import com.natali.voicelearningapp.kidOrParent.MainActivityForKidOrParent;
import com.natali.voicelearningapp.parentApp.ParentMainActivity;

public class StartActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, null);
    }

    private void updateUI(FirebaseUser currentUser, String password) {
        if(currentUser != null){
            if(QueryPreferences.getIsThisAKid(this)){
                startActivity(new Intent(this, Mainh.class));
                finish();
            }else{
                new FirebaseData(currentUser.getUid());
                startActivity(new Intent(this, ParentMainActivity.class));
                finish();
            }
        }else {
            startActivity(new Intent(this, MainActivityForKidOrParent.class));
            finish();

        }
    }
}

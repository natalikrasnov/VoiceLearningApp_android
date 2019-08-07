package com.natali.voicelearningapp.parentApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.natali.voicelearningapp.R;


public abstract class SingleFragmentActivityFoParent extends AppCompatActivity {
    protected abstract Fragment createFragment();

    protected int getLayoutResId() {
        return R.layout.activity_parent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.parent_container);
        if (fragment == null) {
            fragment =createFragment();
            fm.beginTransaction()
                    .add(R.id.parent_container, fragment)
                    .commit();
        }
    }
}

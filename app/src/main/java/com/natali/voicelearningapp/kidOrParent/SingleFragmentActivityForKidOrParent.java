package com.natali.voicelearningapp.kidOrParent;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.natali.voicelearningapp.R;

public abstract class SingleFragmentActivityForKidOrParent extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.kid_or_parent_dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.kid_parent_dialog);
        if (fragment == null) {
            fragment =createFragment();
            fm.beginTransaction()
                    .add(R.id.kid_parent_dialog, fragment)
                    .commit();
        }
    }
}

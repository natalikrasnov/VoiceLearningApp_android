package com.natali.voicelearningapp.kidOrParent;

import android.support.v4.app.Fragment;

public class MainActivityForKidOrParent extends SingleFragmentActivityForKidOrParent {

    @Override
    protected Fragment createFragment() {
        return KidOrParentChooserDialog.newInstance();
    }
}
package com.natali.voicelearningapp.parentApp;

import android.support.v4.app.Fragment;

public class ParentMainActivity extends SingleFragmentActivityFoParent {

    @Override
    protected Fragment createFragment() {
        return ParentListFragment.newInstance();
    }

}

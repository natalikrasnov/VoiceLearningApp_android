package com.natali.voicelearningapp.kidOrParent;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.natali.voicelearningapp.R;

public class KidOrParentChooserDialog extends Fragment {



    public static KidOrParentChooserDialog newInstance() {
       return new KidOrParentChooserDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.kid_or_parent_fragment, container, false);

        v.findViewById(R.id.radioButtonKid).setOnClickListener(v1 -> {
            KidSingUpFragment nextFrag= new KidSingUpFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.kid_parent_dialog , nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        });

        v.findViewById(R.id.radioButtonParent).setOnClickListener(v1 -> {
            ParentSingUpFragment nextFrag= new ParentSingUpFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.kid_parent_dialog , nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        });
        return v;
    }


}

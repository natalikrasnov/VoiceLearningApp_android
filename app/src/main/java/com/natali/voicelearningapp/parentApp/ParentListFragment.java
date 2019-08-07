package com.natali.voicelearningapp.parentApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.natali.voicelearningapp.R;
import com.natali.voicelearningapp.data.FirebaseData;

import java.util.Arrays;
import java.util.Hashtable;

public class ParentListFragment extends Fragment implements Complition {

    private RecyclerView mRecyclerView;
    private static final String TAG = "NerdLauncherFragment";

    public static ParentListFragment newInstance() {
        return new ParentListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parent_list_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseData.getEvents(this);
        updateUI();
    }

    @Override
    public void updateUI() {
            setupAdapter();
            mRecyclerView.getAdapter().notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount());
    }

    private void setupAdapter() {
        // get all Event Log from kid game and set as items:
        mRecyclerView.setAdapter(new ActivityAdapter(FirebaseData.getResultList()));
    }

    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {
        private Hashtable<String,String> mlogEvent;
        private String[] keysSorted;

        public ActivityAdapter(Hashtable<String, String> resultList) {
            mlogEvent = resultList;

            if(resultList.isEmpty())return;
            String[] res = mlogEvent.keySet().toArray(new String[mlogEvent.size()]);
            Arrays.sort(res, String::compareTo);
            keysSorted = res;
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ActivityHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
           holder.bindActivity(keysSorted[position], mlogEvent.get(keysSorted[position]));
        }

        @Override
        public int getItemCount() {
            return mlogEvent.size();
        }
    }

    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private View view;


        public ActivityHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.single_item_for_parent, parent, false));
            view =itemView;
        }

        public void bindActivity(String date, String event) {
            ((TextView)view.findViewById(R.id.textViewDate)).setText(date);
            ((TextView)view.findViewById(R.id.textViewEvent)).setText(event);
        }

        @Override
        public void onClick(View v) {
        }
    }
}

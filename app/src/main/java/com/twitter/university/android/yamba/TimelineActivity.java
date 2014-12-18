package com.twitter.university.android.yamba;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;


public class TimelineActivity extends YambaActivity {
    private static final String TAG = "TIME";
    private static final String DETAIL_FRAGMENT = "Timeline.DETAILS";


    private boolean usingFragments;


    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int code) {
        if (usingFragments) { launchDetailFragment(intent.getExtras()); }
        else { super.startActivityFromFragment(fragment, intent, code); }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline);

        usingFragments = null != findViewById(R.id.details);

        if (savedInstanceState == null) {
            FragmentTransaction xact = getFragmentManager().beginTransaction();
            xact.add(R.id.container, TimelineFragment.newInstance());

            if (usingFragments) {
                xact.add(R.id.details, TimelineDetailFragment.newInstance());
            }

            xact.commit();
        }
    }

    private void launchDetailFragment(Bundle args) {
        FragmentTransaction xact = getFragmentManager().beginTransaction();
        xact.replace(
            R.id.details,
            TimelineDetailFragment.newInstance(args),
            DETAIL_FRAGMENT);
        xact.addToBackStack(null);
        xact.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        xact.commit();
    }
}

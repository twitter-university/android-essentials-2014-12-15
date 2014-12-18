package com.twitter.university.android.yamba;

import android.os.Bundle;


public class TimelineActivity extends YambaActivity {
    private static final String TAG = "TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_timeline);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                .add(R.id.container, TimelineFragment.newInstance())
                .commit();
        }
    }
}

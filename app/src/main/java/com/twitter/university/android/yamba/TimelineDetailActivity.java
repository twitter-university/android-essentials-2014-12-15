package com.twitter.university.android.yamba;

import android.os.Bundle;


public class TimelineDetailActivity extends YambaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_detail);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                .add(
                    R.id.container,
                    TimelineDetailFragment.newInstance(getIntent().getExtras()))
                .commit();
        }
    }
}

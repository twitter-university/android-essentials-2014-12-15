package com.twitter.university.android.yamba;

import android.os.Bundle;
import android.util.Log;


public class TweetActivity extends YambaActivity {
    private static final String TAG = "TWEET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tweet);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                .add(R.id.container, TweetFragment.newInstance())
                .commit();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }
}

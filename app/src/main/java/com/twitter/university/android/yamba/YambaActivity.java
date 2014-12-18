/*
 * Copyright (c) 2014 Twitter Inc.
 */
package com.twitter.university.android.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class YambaActivity extends Activity {
    private static final String TAG = "ACT";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_tweet) {
            nextPage(TweetActivity.class);
            return true;
        }

        if (id == R.id.menu_timeline) {
            nextPage(TimelineActivity.class);
            return true;
        }

        if (id == R.id.menu_about) {
            Toast.makeText(this, R.string.about, Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void nextPage(Class<?> page) {
        Intent i = new Intent(this, page);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }
}

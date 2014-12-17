/*
 * Copyright (c) 2014 Twitter Inc.
 */
package com.twitter.university.android.yamba;

import android.app.IntentService;
import android.content.Intent;

public class YambaService extends IntentService {
    private static final String TAG = "SVC";


    public YambaService() { super(TAG); }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}

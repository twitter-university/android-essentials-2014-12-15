/*
 * Copyright (c) 2014 Twitter Inc.
 */
package com.twitter.university.android.yamba;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


public class YambaService extends IntentService {
    private static final String TAG = "SVC";

    private static final String PARAM_TWEET = "YambaService.TWEET";

    private static final int OP_TOAST = -1;


    private static class ServiceHandler extends Handler {
        private final Context ctxt;

        public ServiceHandler(Context ctxt) { this.ctxt = ctxt; }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OP_TOAST:
                    Toast.makeText(ctxt, msg.arg1, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void post(Context ctxt, String tweet) {
        Intent i = new Intent(ctxt, YambaService.class);
        i.putExtra(PARAM_TWEET, tweet);
        ctxt.startService(i);
    }


    private final ServiceHandler hdlr;

    public YambaService() {
        super(TAG);
        hdlr = new ServiceHandler(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Tweet: " + intent.getStringExtra(PARAM_TWEET));

        int msg = R.string.tweet_failed;
        try {
            Thread.sleep(3 * 1000);
            msg = R.string.tweet_succeeded;
        }
        catch (InterruptedException e) { }
        Message.obtain(hdlr, OP_TOAST, msg, -1).sendToTarget();
    }
}

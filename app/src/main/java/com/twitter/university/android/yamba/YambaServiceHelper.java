/*
 * Copyright (c) 2014 Twitter Inc.
 */
package com.twitter.university.android.yamba;

import android.content.Context;
import android.content.Intent;

import com.twitter.university.android.yamba.service.YambaContract;


public class YambaServiceHelper {
    private static final String TAG = "HELPER";

    private final Context ctxt;

    public YambaServiceHelper(Context ctxt) {
        this.ctxt = ctxt;
    }

    public void post(String tweet) {
        Intent i = new Intent(YambaContract.Service.ACTION_EXECUTE);
        i.setPackage(YambaContract.Service.PACKAGE);
        i.putExtra(YambaContract.Service.PARAM_OP, YambaContract.Service.OP_POST);
        i.putExtra(YambaContract.Service.PARAM_TWEET, tweet);
        ctxt.startService(i);
    }
}

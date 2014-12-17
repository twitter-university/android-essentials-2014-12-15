/*
 * Copyright (c) 2014 Twitter Inc.
 */
package com.twitter.university.android.yamba;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TweetFragment extends Fragment {

    private class Poster extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... post) {
            String tweet = post[0];

            // Emulation
            int msg = R.string.tweet_failed;
            try {
                Thread.sleep( 3 * 1000 );
                msg = R.string.tweet_succeeded;
            }
            catch (InterruptedException e) { }

            return Integer.valueOf(msg);
        }

        @Override
        protected void onCancelled() { finish(R.string.tweet_failed); }

        @Override
        protected void onPostExecute(Integer msg) { finish(msg.intValue()); }

        private void finish(int msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            poster = null;
            updateCount();
        }
    }



    public static TweetFragment newInstance() {
        return new TweetFragment();
    }

    private static Poster poster;

    private int tweetMaxLen;
    private int tweetWarnLen;
    private int tweetMinLen;

    private int colorOk;
    private int colorWarn;
    private int colorError;

    private EditText tweetView;
    private TextView countView;
    private View submitButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources rez = getActivity().getResources();

        tweetMaxLen = rez.getInteger(R.integer.tweet_max);
        tweetWarnLen = rez.getInteger(R.integer.tweet_warn);
        tweetMinLen = rez.getInteger(R.integer.tweet_min);

        colorOk = rez.getColor(R.color.count_ok);
        colorWarn = rez.getColor(R.color.count_warn);
        colorError = rez.getColor(R.color.count_err);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tweet, container, false);

        tweetView = (EditText) rootView.findViewById(R.id.tweet_tweet);

        countView = (TextView) rootView.findViewById(R.id.tweet_count);

        submitButton = rootView.findViewById(R.id.tweet_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { post(); }
        });

        tweetView.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    updateCount();
                }

                @Override public void afterTextChanged(Editable editable) { }
                @Override public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }
            }
        );

        return rootView;
    }

    void updateCount() {
        int n = tweetMaxLen - tweetView.getText().length();
        submitButton.setEnabled(canPost(n));

        countView.setText(String.valueOf(n));

        int countColor;
        if (n < tweetMinLen) { countColor = colorError; }
        else if (n < tweetWarnLen) { countColor = colorWarn; }
        else { countColor = colorOk; }
        countView.setTextColor(countColor);
    }


    void post() {
        String tweet = tweetView.getText().toString();
        if (!canPost(tweetMaxLen - tweet.length())) { return; }

        poster = new Poster();
        poster.execute(tweet);

        tweetView.setText("");
        updateCount();

    }

    private boolean canPost(int n) {
      return (null == poster) && ((n >= tweetMinLen) && (n < tweetMaxLen));
    }
}

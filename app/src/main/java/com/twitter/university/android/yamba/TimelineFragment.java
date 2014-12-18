package com.twitter.university.android.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaContract;


public class TimelineFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TIMELINE_LOADER = 42;

    private static final String[] FROM = {
        YambaContract.Timeline.Columns.HANDLE,
        YambaContract.Timeline.Columns.TIMESTAMP,
        YambaContract.Timeline.Columns.TWEET
    };

    private static final int[] TO = {
        R.id.timeline_handle,
        R.id.timeline_timestamp,
        R.id.timeline_tweet
    };

    private class TimelineBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Cursor cur, int col) {
            if (view.getId() != R.id.timeline_timestamp) { return false; }

            CharSequence s = "long ago";
            long t = cur.getLong(col);
            if (0 < t) { s = DateUtils.getRelativeTimeSpanString(t); }
            ((TextView) view).setText(s);
            return true;
        }
    }

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {
        return new CursorLoader(
            getActivity(),
            YambaContract.Timeline.URI,
            null,
            null,
            null,
            YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View rootView = super.onCreateView(inflater, container, state);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
            getActivity(),
            R.layout.timeline_row,
            null,
            FROM,
            TO,
            0);
        adapter.setViewBinder(new TimelineBinder());
        setListAdapter(adapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int p, long id) {
        Cursor c = (Cursor) l.getItemAtPosition(p);

        Intent i = TimelineDetailFragment.marshallDetails(
            getActivity(),
            c.getLong(c.getColumnIndex(YambaContract.Timeline.Columns.TIMESTAMP)),
            c.getString(c.getColumnIndex(YambaContract.Timeline.Columns.HANDLE)),
            c.getString(c.getColumnIndex(YambaContract.Timeline.Columns.TWEET)));

        startActivity(i);
    }
}

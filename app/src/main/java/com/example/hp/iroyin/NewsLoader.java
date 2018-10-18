package com.example.hp.iroyin;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /**
     * Tag for log messages
     */
    private static final String TAG = NewsLoader.class.getName();
    /**
     * Query URL
     */
    private String mUrl;

    /**
     * context of the activity
     * url to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(TAG, "TEST: onStartLoading() called ... ");
        forceLoad();
    }

    /**
     * background thread.
     */
    @Override
    public List<News> loadInBackground() {
        Log.i(TAG, "TEST: loadInBackground() called ... ");
        if (mUrl == null) {
            return null;
        }
        // contract network request, parse the response, and extract a list of items making the news.*/
        return Utils.fetchNewsData(mUrl);
    }
}

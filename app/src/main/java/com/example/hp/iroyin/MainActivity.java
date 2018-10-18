package com.example.hp.iroyin;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String TAG = MainActivity.class.getName();
    private static final int LOADER_ID = 1;
    /*Base Url for fetching data from the Guardian server*/
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search";
    private NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "TEST: MainActivity onCreate() called ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = getLoaderManager();

        Log.i(TAG, "TEST: calling initLoader()... ");
        loaderManager.initLoader(LOADER_ID, null, this);
        ListView newsListView = findViewById(R.id.list);
        adapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(adapter);


        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News trendingNews = adapter.getItem(position);
                assert trendingNews != null;
                Uri newsUri = Uri.parse(trendingNews.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);
            }
        });
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder builder = baseUri.buildUpon();

        //Get the API Key
        String apiKey = "2b1f8e05-8a41-4e5c-ac30-b98cebde15e0";

        builder.appendQueryParameter("?", "soccer");
        builder.appendQueryParameter("show-tags", "contributor");
        builder.appendQueryParameter("show-fields", "headline");
        builder.appendQueryParameter("page-size", "50");
        builder.appendQueryParameter("api-key", apiKey);

        return new NewsLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
        else{
            TextView mEmptyStateTextView = findViewById(R.id.empty_view);
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}

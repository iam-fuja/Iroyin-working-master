package com.example.hp.iroyin;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static final int CONNECT_TIMEOUT = 15000; /* milliseconds */
    private static final int READ_TIMEOUT = 10000; /* milliseconds */

    private Utils() {
    }


    public static List<News> fetchNewsData(String qUrl) {
        Log.i(TAG, "TEST: fetchNewsData() called ... ");

        URL url = writeUrl(qUrl);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "issues making the HTTP request.", e);
        }
        return fetchResultJson(jsonResponse);
    }


    private static List<News> fetchResultJson(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        List<News> freshNews = new ArrayList<>();

        try {
            // Parse the response given by the JSON response string and
            // build up a list of NewsItem objects with the corresponding data.
            JSONObject baseJsonResponse = new JSONObject(jsonString);

            //Get JSON Object
            JSONObject response = baseJsonResponse.getJSONObject("response");

            //Get the array
            JSONArray results = response.getJSONArray("results");
            ;

            for (int i = 0; i < results.length(); i++) {
                //Get the JSON object at index i
                JSONObject currentNews = results.getJSONObject(i);

                //Get the fields object
                JSONObject fields = currentNews.getJSONObject("fields");

                //Get the title of the article
                String newsTitle = fields.optString("headline");

                Log.i("TAG","TITLE is called..." + newsTitle);

                //Get the section of the article
                String newsSection = currentNews.optString("sectionName");

                //Get th url of the article
                String newsUrl = currentNews.optString("webUrl");

                //Get the tags array
                JSONArray tags = currentNews.getJSONArray("tags");

                //Get the first object in the tags array
                JSONObject currentNewsTags = tags.getJSONObject(0);

                //Get the first name
                String newsAuthorFirstName = currentNewsTags.optString("firstName");

                //Get the last name
                String newsAuthorLastName = currentNewsTags.optString("lastName");

                //Get the date from publication
                final String date = currentNews.optString("webPublicationDate");

                //Get the name of the author
                String newsAuthor = newsAuthorFirstName + " " + newsAuthorLastName;

                News news = new News(newsTitle, newsSection, newsAuthor, newsUrl, date);
                freshNews.add(news);
            }

        } catch (JSONException e) {
            Log.e("Utils", "Problem parsing the news JSON results", e);
        }
        return freshNews;
    }


    private static URL writeUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "challenge building the URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = convertStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String convertStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bReader = new BufferedReader(inputStreamReader);
            String line = bReader.readLine();
            while (line != null) {
                output.append(line);
                line = bReader.readLine();
            }
        }
        return output.toString();
    }
}

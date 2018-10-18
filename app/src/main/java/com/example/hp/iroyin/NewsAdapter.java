package com.example.hp.iroyin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String TAG = NewsAdapter.class.getSimpleName();

    NewsAdapter(Activity context, ArrayList<News> news){
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News trendingNews = getItem(position);

        TextView sectionTextView = listView.findViewById(R.id.section);
        sectionTextView.setText(trendingNews.getSection());

        TextView titleTextView = listView.findViewById(R.id.title);
        titleTextView.setText(trendingNews.getTitle());

        TextView authorTextView = listView.findViewById(R.id.author);
        authorTextView.setText(trendingNews.getAuthor());

        TextView dateTextView = listView.findViewById(R.id.date);
        dateTextView.setText(trendingNews.getDate());

        return listView;

    }
}

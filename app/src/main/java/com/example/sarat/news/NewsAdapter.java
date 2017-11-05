package com.example.sarat.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sarat on 11/3/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context,ArrayList<News> cards){
        super(context,0,cards);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = convertView;

        if(customView == null){
            customView = LayoutInflater.from(getContext()).inflate(R.layout.news_item,parent,false);
        }

        News currentNews = getItem(position);

        TextView author = customView.findViewById(R.id.authorTextView);

        String authorText = "-";

        Log.v("Author",currentNews.getAuthor()+"");
        if(!currentNews.getAuthor().equals("null"))
                authorText=currentNews.getAuthor();
        author.setText(authorText);

        TextView description = customView.findViewById(R.id.descriptionTextView);
        description.setText(currentNews.getDescription());

        TextView title = customView.findViewById(R.id.titleTextView);
        title.setText(currentNews.getTitle());

        TextView publishedAt = customView.findViewById(R.id.publishedAtTextView);
        publishedAt.setText(currentNews.getPublishedAt());

        String publishedText = "-";
        if(!currentNews.getPublishedAt().equals("null"))
            publishedText=currentNews.getPublishedAt();
        publishedAt.setText(publishedText);


        return customView;
    }
}

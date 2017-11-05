package com.example.sarat.news.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sarat.news.News;
import com.example.sarat.news.NewsAdapter;
import com.example.sarat.news.NewsApiData;
import com.example.sarat.news.NewsLoader;
import com.example.sarat.news.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TOI extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private NewsAdapter adapter;

    private ProgressBar progressBar;

    public TOI(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View fragmentView = inflater.inflate(R.layout.fragment_common_list, container, false);

        progressBar = fragmentView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                afterRefresh(fragmentView);
            }

            public void onTick(long millisUntilFinished) {}
        }.start();


        return fragmentView;
    }

    public void afterRefresh(View fragmentView){

        progressBar.setVisibility(View.INVISIBLE);

        ListView listView = fragmentView.findViewById(R.id.listView);

        adapter = new NewsAdapter(getContext(),new ArrayList<News>());

        listView.setAdapter(adapter);

        getLoaderManager().initLoader(1,null,this).forceLoad();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                News news = adapter.getItem(i);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
                startActivity(intent);
            }
        });
    }


    @Override
    public android.support.v4.content.Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getContext(), "the-times-of-india,the-hindu");
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<News>> loader, ArrayList<News> data) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<News>> loader) {
        progressBar.setVisibility(View.VISIBLE);
        adapter.addAll(new ArrayList<News>());
    }
}
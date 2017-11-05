package com.example.sarat.news;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sarat on 11/3/2017.
 */

public class NewsLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<News>>{

    private String source;
    private String JSON_RESPONSE;

    public NewsLoader(Context context,String source){
        super(context);
        this.source = source;
    }

    @Override
    public ArrayList<News> loadInBackground() {

        ArrayList<News> newses;

        if(source.equals("the-times-of-india,the-hindu")){
            source = "the-times-of-india";
            newses = networkCall();

            source = "the-hindu";
            newses.addAll(networkCall());
        }else{
            newses = networkCall();
        }

        return newses;
    }

    private ArrayList<News> networkCall(){

        try{

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(NewsApiData.URL).newBuilder();
            urlBuilder.addQueryParameter("source",source);
            urlBuilder.addQueryParameter("apiKey",NewsApiData.API_KEY);

            String url = urlBuilder.build().toString();

            Request request = new Request.Builder().url(url).build();

            try{
                Response response = client.newCall(request).execute();
                JSON_RESPONSE = response.body().string();
                Log.v("Recieved response",JSON_RESPONSE);

            }catch(Exception e){
                e.printStackTrace();
            }


        }catch(Exception e){
            e.printStackTrace();
        }

        return parseJSONResponse();
    }

    private ArrayList<News> parseJSONResponse(){

        ArrayList<News> news = new ArrayList<>();

        Log.v("Restarted again","Yes again!");

        try {
            JSONObject object = new JSONObject(JSON_RESPONSE);
            JSONArray articles = object.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject current = articles.getJSONObject(i);

                String author = current.getString("author");
                String title = current.getString("title");
                String description = current.getString("description");
                String url = current.getString("url");
                String urlToImage = current.getString("urlToImage");
                String publishedAt = current.getString("publishedAt");

                if(!publishedAt.equals("null")) {
                    String[] publishedArray = publishedAt.split("T");
                    publishedArray[1] = publishedArray[1].substring(0, publishedArray[1].length() - 1);
                    publishedAt = publishedArray[0] + " " + publishedArray[1];
                }

                news.add(new News(author, title, description, url, urlToImage, publishedAt));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return news;
    }
}

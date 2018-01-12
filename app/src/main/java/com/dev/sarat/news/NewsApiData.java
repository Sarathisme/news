package com.dev.sarat.news;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sarat on 11/3/2017.
 */

public class NewsApiData extends Application {

    public static String URL = "https://newsapi.org/v2/top-headlines";
    public static String API_KEY = "00f85cd5105d43a384a947cadfd21525";

     HashMap<String,News> dataForSearch = new HashMap<>();



}

package com.example.sarat.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sarat.news.fragments.General;
import com.example.sarat.news.fragments.TOI;

/**
 * Created by sarat on 11/3/2017.
 */

public class NewsFragmentAdapter extends FragmentPagerAdapter {

    public NewsFragmentAdapter(Context context, FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new TOI();
            case 1: NewsApiData.SOURCE = "techcrunch";
                return new General();
            case 2: NewsApiData.SOURCE = "ign";
                return new General();
            case 3: NewsApiData.SOURCE = "bbc-news";
                return new General();
            case 4: NewsApiData.SOURCE = "espn";
                return new General();
            case 5: NewsApiData.SOURCE = "mtv-news";
                return new General();
            default:NewsApiData.SOURCE = "the-times-of-india";
        }
        return new General();
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "General";
            case 1: return "Tech";
            case 2: return "Games";
            case 3: return "World";
            case 4: return "Sports";
            case 5: return "Hollywood";
            default:return "The Hindu";
        }
    }
}

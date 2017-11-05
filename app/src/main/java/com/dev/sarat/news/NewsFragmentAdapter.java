package com.dev.sarat.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dev.sarat.news.fragments.Games;
import com.dev.sarat.news.fragments.Hollywood;
import com.dev.sarat.news.fragments.Sports;
import com.dev.sarat.news.fragments.TOI;
import com.dev.sarat.news.fragments.Tech;
import com.dev.sarat.news.fragments.World;

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
            case 1: return new Tech();
            case 2: return new Games();
            case 3: return new World();
            case 4: return new Sports();
            case 5: return new Hollywood();
        }
        return new TOI();
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

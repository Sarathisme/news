package com.dev.sarat.news;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class NewsActivity extends AppCompatActivity{

    private ViewPager viewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
//
//        searchView.setIconifiedByDefault(false);
//
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
//        searchView.setLayoutParams(params);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.refresh_fragment){
            if(!isNwConnected(getBaseContext()))
                Toast.makeText(getBaseContext(),"Internet not connected!",Toast.LENGTH_LONG).show();
            else {

                final Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
                getSupportFragmentManager().beginTransaction().detach(fragment).commit();
                getSupportFragmentManager().beginTransaction().attach(fragment).commit();

                final ListView listView = fragment.getView().findViewById(R.id.listView);
                final ProgressBar progressBar = fragment.getView().findViewById(R.id.progressBar);

                listView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.VISIBLE);
                    }
                },3000);
                Toast.makeText(getBaseContext(), "Loading! Please wait.......", Toast.LENGTH_SHORT).show();
            }
        }

        if(item.getItemId() == R.id.info){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            View alertView = this.getLayoutInflater().inflate(R.layout.alert,null);
            builder.setView(alertView);

            AlertDialog alert = builder.create();

            setCancelButton(alertView,alert);
            alert.setTitle("Info");
            alert.show();

            TextView textView = alertView.findViewById(R.id.newsapi);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("Its here","IS HERE");
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://www.newsapi.org"));
                    startActivity(i);
                }
            });

            TextView textView1 = alertView.findViewById(R.id.pixelkit);
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://www.pixelkit.com"));
                    startActivity(i);
                }
            });

            TextView textView2 = alertView.findViewById(R.id.iconarchive);
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://www.iconarchive.com"));
                    startActivity(i);
                }
            });
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        MenuInflater menuInflater = new MenuInflater(getApplicationContext());

        if(!isNwConnected(getBaseContext())&&Build.VERSION.SDK_INT<=26)
            Toast.makeText(getBaseContext(),"Internet not connected!",Toast.LENGTH_LONG).show();

        try {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

        }catch(Exception e){
            e.printStackTrace();
        }

        finally {
            viewPager = (ViewPager)findViewById(R.id.viewPager);

            NewsFragmentAdapter adapter = new NewsFragmentAdapter(getBaseContext(),getSupportFragmentManager());

            viewPager.setAdapter(adapter);
            viewPager.setPageTransformer(true,new ZoomOutPageTransformer());

            TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);

            tabLayout.setupWithViewPager(viewPager);
        }
    }

    public void setCancelButton(View view,final AlertDialog alert){
        Button okButton = view.findViewById(R.id.ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
    }

    public static boolean isNwConnected(Context context) {
        if (context == null) {
            return true;
        }

        if(Build.VERSION.SDK_INT<=26) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            if (nwInfo != null && nwInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }
}

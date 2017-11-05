package com.example.sarat.news;

import android.content.DialogInterface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity{

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        try {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }catch(Exception e){
            e.printStackTrace();
        }

        finally {
            viewPager = (ViewPager)findViewById(R.id.viewPager);

            NewsFragmentAdapter adapter = new NewsFragmentAdapter(getBaseContext(),getSupportFragmentManager());

            viewPager.setAdapter(adapter);

            TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);

            tabLayout.setupWithViewPager(viewPager);
        }
    }

    public void showAlert(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Setting message manually and performing action on button click
        builder.setMessage("Developed by Sarath Sattiraju\nPowered by Newsapi.org")
                .setCancelable(false)
                .setPositiveButton("RATE IT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getBaseContext(), "Yet to be implemented!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Info");
        alert.show();
    }

    public void restartNetwork(View v){

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewPager+":"+viewPager.getCurrentItem());
        getSupportFragmentManager().beginTransaction().detach(fragment).commit();

        getSupportFragmentManager().beginTransaction().attach(fragment).commit();
        Toast.makeText(getBaseContext(),"Loading! Please wait.......",Toast.LENGTH_LONG).show();
    }
}

package com.dev.sarat.news;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewsActivity extends AppCompatActivity{

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if(!isNwConnected(getBaseContext())&&Build.VERSION.SDK_INT<=26)
            Toast.makeText(getBaseContext(),"Internet not connected!",Toast.LENGTH_LONG).show();

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

    public void setCancelButton(View view,final AlertDialog alert){
        Button okButton = view.findViewById(R.id.ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
    }

    public void restartNetwork(View v){

        if(!isNwConnected(getBaseContext()))
            Toast.makeText(getBaseContext(),"Internet not connected!",Toast.LENGTH_LONG).show();
        else {

            final Fragment fragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
            getSupportFragmentManager().beginTransaction().detach(fragment).commit();

            getSupportFragmentManager().beginTransaction().attach(fragment).commit();
            Toast.makeText(getBaseContext(), "Loading! Please wait.......", Toast.LENGTH_SHORT).show();
        }
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

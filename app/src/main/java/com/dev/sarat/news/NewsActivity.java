package com.dev.sarat.news;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

        //Setting message manually and performing action on button click
        String icon = "<a href='http://www.pixelkit.com'> PixelKit </a>";
        String message = "Developed by Sarath Sattiraju\nPowered by Newsapi.org\nIcon made by ";
        String second = "from ";
        String web =   "<a href='http://www.iconarchive.com'> Icon Archive </a>";

        String whole;

        if(Build.VERSION.SDK_INT<=24)
            whole = message+ Html.fromHtml(icon)+second+Html.fromHtml(web);
        else
            whole = message+ Html.fromHtml(icon,0)+second+Html.fromHtml(web,0);

        builder.setMessage(whole)
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

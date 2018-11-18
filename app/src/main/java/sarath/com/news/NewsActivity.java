package sarath.com.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String country="in", category="business";
    private LinearLayout mainPage;
    private NavigationView navigationView;
    private TextView featured, categories;
    private ImageButton more;
    private Typeface tf;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categories = findViewById(R.id.categories);
        featured = findViewById(R.id.featured);

        drawerLayout = findViewById(R.id.drawer_layout);

        mainPage = findViewById(R.id.mainPageLayout);
        mainPage.setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTypeFace();
        initFragment(true);
    }

    public void setTypeFace(){
        tf = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Bold.ttf");

        categories.setTypeface(tf);
        featured.setTypeface(tf);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_info){
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
            builder.setMessage(R.string.infoBox)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Rate", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.dev.sarath.news&hl=en"));
                            startActivity(i);
                        }
                    });
            builder.show();
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                mainPage.setVisibility(View.VISIBLE);
                initFragment(true);
                break;
            case R.id.nav_business:
                initFragment("business", "none");
                break;
            case R.id.nav_entertainment:
                initFragment("entertainment", "none");
                break;
            case R.id.nav_health:
                initFragment("health", "none");
                break;
            case R.id.nav_science:
                initFragment("science", "none");
                break;
            case R.id.nav_sports:
                initFragment("sports", "none");
                break;
            case R.id.nav_technology:
                initFragment("technology", "none");
                break;
            case R.id.hinduMenu:
                initFragment("none", "the-hindu");
                break;
            case R.id.toiMenu:
                initFragment("none", "the-times-of-india");
                break;
            case R.id.googleMenu:
                initFragment("none", "google-news-in");
                break;
            case R.id.saved:
                openSavedFragment();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openSavedFragment(){
        mainPage.setVisibility(View.GONE);

        Fragment fragment = new NewsSavedFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, fragment);
        ft.commit();
    }

    public void initFragment(boolean homepage){
        Fragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("country", country);
        bundle.putString("category", "none");
        bundle.putString("sources", "none");
        bundle.putBoolean("homepage", homepage);
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, fragment);
        ft.commit();
    }

    public void initFragment(String category, String sources){
        mainPage.setVisibility(View.GONE);

        Fragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("country", country);
        bundle.putString("category", category);
        bundle.putString("sources", sources);
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, fragment);
        ft.commit();
    }

    public void openNavigationMenu(View v){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void onClickButton(View v){
        int id = v.getId();

        switch (id){
            case R.id.businessButton:
                initFragment("business", "none");
                break;

            case R.id.entertainmentButton:
                initFragment("entertainment", "none");
                break;

            case R.id.healthButton:
                initFragment("health", "none");
                break;

            case R.id.hinduButton:
                initFragment("none", "the-hindu");
                break;

            case R.id.toiButton:
                initFragment("none", "the-times-of-india");
                break;
        }
    }
}

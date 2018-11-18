package sarath.com.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsArticleActivity extends AppCompatActivity {

    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView headlines = findViewById(R.id.headlinesArticle);
        TextView content = findViewById(R.id.descriptionArticle);
        TextView author = findViewById(R.id.authorArticle);
        TextView timestamp = findViewById(R.id.publishedAtArticle);
        ImageView image = findViewById(R.id.collapsingImage);

        Bundle extras = getIntent().getExtras();

        try {
            news = (News) extras.getSerializable("news");
            headlines.setText(news.getHeadlines());
            content.setText(news.getContent());
            author.setText(news.getAuthor());
            timestamp.setText(news.getPublishedAt());

            Picasso.get().load(news.getUrlToImage()).placeholder(R.drawable.good).into(image);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(news.getUrl()));
                startActivity(i);
            }
        });
    }
}

package sarath.com.news;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.GeneralViewHolder>{

    private ArrayList<News> news;
    private Context context;
    private NewsDatabase database;
    private boolean isSaved = false;

    NewsAdapter(ArrayList<News> news, Context context){
        this.news = news;
        this.context = context;
    }

    NewsAdapter(ArrayList<News> news, Context context, boolean isSaved){
        this.news = news;
        this.context = context;
        this.isSaved = isSaved;
    }

    static class GeneralViewHolder extends RecyclerView.ViewHolder {

        private TextView headlines, description;
        private ImageView image;
        private Button read;
        private ImageButton download, share;
        private String url;
        private int uid;

         GeneralViewHolder(@NonNull View itemView) {
            super(itemView);

            headlines = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            read = itemView.findViewById(R.id.read);
            download = itemView.findViewById(R.id.download);
            share = itemView.findViewById(R.id.share);
            image = itemView.findViewById(R.id.imageNews);
        }
    }

    @NonNull
    @Override
    public NewsAdapter.GeneralViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_article, viewGroup, false);
        return new GeneralViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.GeneralViewHolder generalViewHolder, final int i) {
        generalViewHolder.headlines.setText(news.get(i).getHeadlines());
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        generalViewHolder.headlines.setTypeface(tf);

        generalViewHolder.description.setText(news.get(i).getDescription());
        Typeface tf1 = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue-Medium.ttf");
        generalViewHolder.description.setTypeface(tf1);

        generalViewHolder.url = news.get(i).getUrl();

        //TODO: Cache the data
        if(news.get(i).getUrlToImage() != null && !news.get(i).getUrlToImage().equals("")) {
            Picasso.get().load(news.get(i).getUrlToImage()).placeholder(R.drawable.good).into(generalViewHolder.image);
        }

        //TODO: Check the download option
        if(isSaved){
            generalViewHolder.download.setImageResource(R.drawable.ic_delete_black_24dp);
            generalViewHolder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database = NewsDatabase.getNewsDatabase(context);
                    final NewsActivity activity = (NewsActivity) context;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                database.newsDao().deleteNews(news.get(i));
                                news.remove(i);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Item Removed Successfully!", Toast.LENGTH_SHORT).show();
                                        notifyItemRemoved(i);
                                        notifyDataSetChanged();
                                    }
                                });
                            }catch (Exception e){
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        generalViewHolder.download.setImageResource(R.drawable.ic_file_download_black_24dp);
                                        Toast.makeText(context, "Something's wrong!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });
        }else {
            generalViewHolder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database = NewsDatabase.getNewsDatabase(context);
                    final NewsActivity activity = (NewsActivity) context;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                database.newsDao().insertNews(news.get(i));
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        generalViewHolder.download.setImageResource(R.drawable.ic_cloud_done_black_24dp);
                                    }
                                });
                            } catch (SQLiteConstraintException e) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Already added!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            });
        }

        generalViewHolder.read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", news.get(i));
                Intent i = new Intent(context, NewsArticleActivity.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

        generalViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String message = news.get(i).getHeadlines() + "\n.\n.\nTo know more about the news click on this link.\n.\n.\n"
                            + news.get(i).getUrl() + "\n.\n.\n" + "Indian News. Download from Google Play Store";
                    shareIntent.putExtra(Intent.EXTRA_TITLE, news.get(i).getHeadlines());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                    context.startActivity(Intent.createChooser(shareIntent, "Choose sharing method"));
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Toast.makeText(context, "Something's wrong!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}

package com.dev.sarat.news;

import android.graphics.Bitmap;

/**
 * Created by sarat on 11/3/2017.
 */

public class News {

    private String author,title,description,url,urlToImage,publishedAt;

    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public News(String author, String title, String description, String url, String urlToImage, String publishedAt, Bitmap image) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.image = image;

    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}

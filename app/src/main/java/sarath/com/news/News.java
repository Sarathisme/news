package sarath.com.news;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"headlines"}, unique = true)})
class News implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "headlines")
    private String headlines;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "publishedAt")
    private String publishedAt;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ColumnInfo(name = "urlToImage")
    private String urlToImage;

    @ColumnInfo(name = "hasData")
    private boolean hasData = true;

    @NonNull
    public int getUid() {
        return uid;
    }

    public void setUid(@NonNull int uid) {
        this.uid = uid;
    }

    public News(){

    }

    @Ignore
    public News(String headlines, String author, String description, String publishedAt, String url, String urlToImage, String content) {
        this.headlines = headlines;
        this.author = author;
        this.description = description;
        this.publishedAt = publishedAt;
        this.url = url;
        this.urlToImage = urlToImage;
        this.content = content;
    }

    @Ignore
    public News(boolean hasData){
        this.hasData = hasData;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public String getHeadlines() {
        return headlines;

    }

    public void setHeadlines(String headlines) {
        this.headlines = headlines;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}

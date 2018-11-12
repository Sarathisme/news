package sarath.com.news;

class News {
    private String headlines, author, description, publishedAt, url, urlToImage;
    private boolean hasData = true;

    public News(String headlines, String author, String description, String publishedAt, String url, String urlToImage) {
        this.headlines = headlines;
        this.author = author;
        this.description = description;
        this.publishedAt = publishedAt;
        this.url = url;
        this.urlToImage = urlToImage;
    }

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

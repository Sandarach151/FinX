package com.example.finx.Model;

import java.time.Instant;

public class NewsArticle {

    private Instant publishTime;

    private String headline;

    private String imageURL;

    private String summary;

    private String url;

    public NewsArticle() {}

    public NewsArticle(Integer unixPublishTime, String headline, String imageURL, String summary, String url) {
        this.publishTime = Instant.ofEpochSecond(unixPublishTime);
        this.headline = headline;
        this.imageURL = imageURL;
        this.summary = summary;
        this.url = url;
    }


    public void setPublishTime(Instant publishTime) {
        this.publishTime = publishTime;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getPublishTime() {
        return publishTime;
    }

    public String getHeadline() {
        return headline;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "publishTime=" + publishTime +
                ", headline='" + headline + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", summary='" + summary + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

package com.alarm.vxtlab.heywakealarm.models;

import io.realm.RealmObject;

/**
 * Created by VXT on 1/26/2018.
 */

public class News extends RealmObject {
    private String imageURL;
    private String title;
    private String newsSource;
    private String newsTime;
    private String link;

    public News() {
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

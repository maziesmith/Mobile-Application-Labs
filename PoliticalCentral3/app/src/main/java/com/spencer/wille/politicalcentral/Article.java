package com.spencer.wille.politicalcentral;

import android.graphics.drawable.Drawable;

/**
 * Created by wille on 5/19/2017.
 */

public class Article {
    private String name;
    private String description;
    private String source;
    private Drawable image;
    private String url;
    public Article(String n, String d, String s, Drawable i, String u){
        name = n;
        description = d;
        source = s;
        image = i;
        url = u;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

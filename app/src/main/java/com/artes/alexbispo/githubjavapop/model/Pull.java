package com.artes.alexbispo.githubjavapop.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alex on 11/05/17.
 */

public class Pull implements Comparable<Pull> {

    @SerializedName("id")
    private long id;

    @SerializedName("user")
    private Owner user;

    @SerializedName("title")
    private String title;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("body")
    private String body;

    @SerializedName("html_url")
    private String url;

    @SerializedName("state")
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Owner getUser() {
        return user;
    }

    public void setUser(Owner user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAtFormatted(String format) {
        SimpleDateFormat dFormat = new SimpleDateFormat(format);
        return dFormat.format(getCreatedAtInDate());
    }

    public Date getCreatedAtInDate(){
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = dFormat.parse(getCreatedAt());
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pull pull = (Pull) o;

        return id == pull.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(@NonNull Pull pull) {
        return Long.valueOf(pull.getCreatedAtInDate().getTime()).compareTo(Long.valueOf(this.getCreatedAtInDate().getTime()));
    }
}

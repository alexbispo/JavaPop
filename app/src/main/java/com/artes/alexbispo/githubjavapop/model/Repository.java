package com.artes.alexbispo.githubjavapop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 09/05/17.
 */

public class Repository {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("forks")
    private int forks;

    @SerializedName("stargazers_count")
    private int stars;

    @SerializedName("owner")
    private Owner owner;

    public Repository() {}

    public Repository(long id, String name, String description, Owner owner, int forks, int stars) {
        this.name = name;
        this.description = description;
        this.forks = forks;
        this.stars = stars;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}

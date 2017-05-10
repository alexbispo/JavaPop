package com.artes.alexbispo.githubjavapop.model;

/**
 * Created by alex on 09/05/17.
 */

public class Repository {

    private String name;
    private String description;
    private String userName;
    private int forks;
    private int stars;

    public Repository() {}

    public Repository(String name, String description, String userName, int forks, int stars) {
        this.name = name;
        this.description = description;
        this.userName = userName;
        this.forks = forks;
        this.stars = stars;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}

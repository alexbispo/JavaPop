package com.artes.alexbispo.githubjavapop.web;

import com.artes.alexbispo.githubjavapop.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alex on 10/05/17.
 */

public interface GitHubInterface {

    @GET("search/repositories?q=language:Java&sort=stars")
    Call<GitHubResponse> getJavaPopRepositories(@Query("page") int page);
}

package com.artes.alexbispo.githubjavapop.web;

import com.artes.alexbispo.githubjavapop.model.Repository;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alex on 10/05/17.
 */

public class GitHubResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<Repository> items;

    public GitHubResponse() {}

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }
}

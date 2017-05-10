package com.artes.alexbispo.githubjavapop.web;

import com.artes.alexbispo.githubjavapop.model.Repository;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;

/**
 * Created by alex on 10/05/17.
 */

public class GitHubResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private Set<Repository> items;

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

    public Set<Repository> getItems() {
        return items;
    }

    public void setItems(Set<Repository> items) {
        this.items = items;
    }
}

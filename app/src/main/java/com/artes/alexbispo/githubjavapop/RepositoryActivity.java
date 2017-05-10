package com.artes.alexbispo.githubjavapop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.artes.alexbispo.githubjavapop.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_repositories);

        List<Repository> repositories = getAllRepositories();

        RepositoryAdapter repositoryAdapter = new RepositoryAdapter(repositories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(repositoryAdapter);
    }

    private List<Repository> getAllRepositories() {
        List<Repository> repositories = new ArrayList<>();
        for(int i=0; i < 20; i++){
            repositories.add(new Repository("Repository " + i, "Repository " + i + "description", "user " + i, 562, 65));
        }
        return repositories;
    }
}

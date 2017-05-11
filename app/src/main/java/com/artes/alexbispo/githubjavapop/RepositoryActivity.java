package com.artes.alexbispo.githubjavapop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.artes.alexbispo.githubjavapop.model.Repository;
import com.artes.alexbispo.githubjavapop.task.LoadRepositoriesTask;

import java.util.Set;
import java.util.TreeSet;

public class RepositoryActivity extends AppCompatActivity implements LoadRepositoriesTask.Listener {

    private Set<Repository> mRepositorySet = new TreeSet<>();
    private RepositoryAdapter mRepositoryAdapter;
    private boolean loadRepositoriesTaskCompleted;
    private int dataSetPage;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_repositories);

        mRepositoryAdapter = new RepositoryAdapter(mRepositorySet);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mRepositoryAdapter);
        recyclerView.addOnScrollListener(getOnScrollListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSetPage++;
        new LoadRepositoriesTask(this, this, dataSetPage).execute();
    }

    @Override
    public void onLoadRepositoriesTaskCompleted(Set<Repository> repositorySet) {
        mRepositorySet.addAll(repositorySet);
        mRepositoryAdapter.notifyDataSetChanged();
        loadRepositoriesTaskCompleted = true;
    }

    private RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollDown = dy > 0;
                if(isScrollDown){
                    if(loadRepositoriesTaskCompleted){
                        int itemsVisibleCount = mLayoutManager.getChildCount();
                        int itemCount = mLayoutManager.getItemCount();

                        Log.d("VISIBLE", "findFirstCompletelyVisibleItemPosition " + mLayoutManager.findFirstCompletelyVisibleItemPosition());
                        Log.d("VISIBLE", "findFirstVisibleItemPosition " + mLayoutManager.findFirstVisibleItemPosition());
                        Log.d("VISIBLE", "findLastCompletelyVisibleItemPosition " + mLayoutManager.findLastCompletelyVisibleItemPosition());
                        Log.d("VISIBLE", "findLastVisibleItemPosition " + mLayoutManager.findLastVisibleItemPosition());

                        Log.d("SCROLL", "itemCount: " + itemCount + "; childCount: " + itemsVisibleCount +  "; dx: " + dx + "; dy: " + dy);

                        if((mLayoutManager.findLastVisibleItemPosition() + 1) == itemCount){
                            dataSetPage++;
                            loadRepositoriesTaskCompleted = false;
                            new LoadRepositoriesTask(RepositoryActivity.this, RepositoryActivity.this, dataSetPage).execute();
                        }

                    }
                }
            }
        };
    }
}

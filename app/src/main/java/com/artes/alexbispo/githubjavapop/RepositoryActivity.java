package com.artes.alexbispo.githubjavapop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.artes.alexbispo.githubjavapop.model.Repository;
import com.artes.alexbispo.githubjavapop.task.LoadRepositoriesTask;
import com.artes.alexbispo.githubjavapop.web.GitHubResponse;

import java.util.Set;
import java.util.TreeSet;

import retrofit2.Response;

public class RepositoryActivity extends AppCompatActivity implements LoadRepositoriesTask.Listener {

    private Set<Repository> mRepositorySet = new TreeSet<>();
    private RepositoryAdapter mRepositoryAdapter;
    private boolean mLoadRepositoriesTaskCompleted;
    private int mDataSetPage;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_repositories);

        mRepositoryAdapter = new RepositoryAdapter(mRepositorySet);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRepositoryAdapter);
        mRecyclerView.addOnScrollListener(getOnScrollListener());
        mRecyclerView.addOnItemTouchListener(getReCyclerViewOnItemTouchListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSetPage++;
        new LoadRepositoriesTask(this, this, mDataSetPage).execute();
    }

    @Override
    public void onLoadRepositoriesTaskCompleted(Set<Repository> repositorySet) {
        mRepositorySet.addAll(repositorySet);
        mRepositoryAdapter.notifyDataSetChanged();
        mLoadRepositoriesTaskCompleted = true;
    }

    @Override
    public void onLoadRepositoriesTaskFailed(String message) {
        Snackbar.make(findViewById(R.id.rv_repositories), message, Snackbar.LENGTH_LONG).show();
    }

    private RecyclerView.OnScrollListener getOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isScrollDown = dy > 0;
                if(isScrollDown){
                    if(mLoadRepositoriesTaskCompleted){
                        int itemCount = mLayoutManager.getItemCount();

                        if((mLayoutManager.findLastVisibleItemPosition() + 1) == itemCount){
                            mDataSetPage++;
                            mLoadRepositoriesTaskCompleted = false;
                            new LoadRepositoriesTask(RepositoryActivity.this, RepositoryActivity.this, mDataSetPage).execute();
                        }

                    }
                }
            }
        };
    }

    private RecyclerView.OnItemTouchListener getReCyclerViewOnItemTouchListener(){
        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if(child != null){
                    Object[] repositoryArray = mRepositorySet.toArray();
                    Repository repo = (Repository) repositoryArray[mRecyclerView.getChildAdapterPosition(child)];
                    goToPullActivity(repo);
                }
            }
        });

        return new RecyclerView.OnItemTouchListener(){

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)){
                    Object[] repositoryArray = mRepositorySet.toArray();
                    Repository repo = (Repository) repositoryArray[rv.getChildAdapterPosition(child)];
                    goToPullActivity(repo);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        };
    }

    private void goToPullActivity(Repository repo){
        Intent goToPullActivityIntent = new Intent(RepositoryActivity.this, PullActivity.class);
        goToPullActivityIntent.putExtra("owner_name", repo.getOwner().getLogin());
        goToPullActivityIntent.putExtra("repo_name", repo.getName());
        startActivity(goToPullActivityIntent);
    }
}

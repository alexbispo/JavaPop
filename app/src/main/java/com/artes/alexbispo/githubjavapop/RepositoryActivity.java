package com.artes.alexbispo.githubjavapop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

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

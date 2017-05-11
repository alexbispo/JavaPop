package com.artes.alexbispo.githubjavapop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.artes.alexbispo.githubjavapop.model.Pull;
import com.artes.alexbispo.githubjavapop.model.Repository;
import com.artes.alexbispo.githubjavapop.task.LoadPullsTask;
import com.artes.alexbispo.githubjavapop.task.LoadRepositoriesTask;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class PullActivity extends AppCompatActivity implements LoadPullsTask.Listener {

    private Set<Pull> mPullSet = new HashSet<>();
    private RecyclerView mRecyclerView;
    private PullAdapter mPullAdapter;
    private LinearLayoutManager mLayoutManager;
    private String ownerName;
    private String repoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        Intent intent = getIntent();
        ownerName = intent.getStringExtra("owner_name");
        repoName = intent.getStringExtra("repo_name");

//        Toast.makeText(this, "owner:"+ownerName+"; repo:"+repoName, Toast.LENGTH_LONG).show();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_pulls);

        mPullAdapter = new PullAdapter(mPullSet);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mPullAdapter);
        mRecyclerView.addOnItemTouchListener(getReCyclerViewOnItemTouchListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadPullsTask(this, this, ownerName, repoName).execute();
    }

    @Override
    public void onLoadPullsTaskCompleted(Set<Pull> pullSet) {
        mPullSet.addAll(pullSet);
        mPullAdapter.notifyDataSetChanged();
    }

    private RecyclerView.OnItemTouchListener getReCyclerViewOnItemTouchListener(){
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if(child != null){
//                    Object[] repositoryArray = mPullSet.toArray();
//                    Repository repo = (Repository) repositoryArray[mRecyclerView.getChildAdapterPosition(child)];
//                    goToPullActivity(repo);
                }
            }
        });

        return new RecyclerView.OnItemTouchListener(){

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null){
//                    Object[] repositoryArray = mPullSet.toArray();
//                    Repository repo = (Repository) repositoryArray[rv.getChildAdapterPosition(child)];
//                    goToPullActivity(repo);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}



        };
    }
}

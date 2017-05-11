package com.artes.alexbispo.githubjavapop;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.artes.alexbispo.githubjavapop.model.Pull;
import com.artes.alexbispo.githubjavapop.model.Repository;
import com.artes.alexbispo.githubjavapop.task.LoadPullsTask;
import com.artes.alexbispo.githubjavapop.task.LoadRepositoriesTask;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static android.app.PendingIntent.getActivity;

public class PullActivity extends AppCompatActivity implements LoadPullsTask.Listener {

    private Set<Pull> mPullSet = new HashSet<>();
    private RecyclerView mRecyclerView;
    private PullAdapter mPullAdapter;
    private LinearLayoutManager mLayoutManager;
    private String ownerName;
    private String repoName;
    private int openedCount;
    private int closedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        setupActionBar();

        Intent intent = getIntent();
        ownerName = intent.getStringExtra("owner_name");
        repoName = intent.getStringExtra("repo_name");

        setTitle(repoName);

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
        openedCount = 0;
        closedCount = 0;
        for (Pull pull : mPullSet) {
            if("open".equalsIgnoreCase(pull.getState())) openedCount++;
            if("closed".equalsIgnoreCase(pull.getState())) closedCount++;
        }
        TextView tvPullOpened = (TextView) findViewById(R.id.tv_pull_opened);
        TextView tvPullClosed = (TextView) findViewById(R.id.tv_pull_closed);
        tvPullOpened.setText(String.format("%d opened", openedCount));
        tvPullClosed.setText(String.format(" / %d closed", closedCount));
        mPullAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
                    Object[] repositoryArray = mPullSet.toArray();
                    Pull pull = (Pull) repositoryArray[mRecyclerView.getChildAdapterPosition(child)];
                    Intent goToPullPage = new Intent(Intent.ACTION_VIEW);
                    goToPullPage.setData(Uri.parse(pull.getUrl()));
                    startActivity(goToPullPage);
                }
            }
        });

        return new RecyclerView.OnItemTouchListener(){

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)){
                    Object[] repositoryArray = mPullSet.toArray();
                    Pull pull = (Pull) repositoryArray[rv.getChildAdapterPosition(child)];
                    Intent goToPullPage = new Intent(Intent.ACTION_VIEW);
                    goToPullPage.setData(Uri.parse(pull.getUrl()));
                    startActivity(goToPullPage);
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

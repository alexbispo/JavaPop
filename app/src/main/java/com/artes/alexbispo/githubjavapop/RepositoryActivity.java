package com.artes.alexbispo.githubjavapop;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.artes.alexbispo.githubjavapop.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoryActivity extends AppCompatActivity {

    private List<Repository> mRepositoryList = new ArrayList<>();
    private RepositoryAdapter mRepositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_repositories);

        mRepositoryAdapter = new RepositoryAdapter(mRepositoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mRepositoryAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadRepositoriesTask().execute();
    }

    private class LoadRepositoriesTask extends AsyncTask {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(RepositoryActivity.this, "Por favor aguarde", "Buscando repositórios...", true, true);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getAllRepositories(mRepositoryList);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            dialog.dismiss();
            mRepositoryAdapter.notifyDataSetChanged();
        }

        private List<Repository> getAllRepositories(List<Repository> repositoryList) {
            for(int i=0; i < 20; i++){
                repositoryList.add(new Repository("Repository " + i, "Repository " + i + "description", "user " + i, 562, 65));
            }
            return repositoryList;
        }
    }


}

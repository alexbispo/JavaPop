package com.artes.alexbispo.githubjavapop;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.artes.alexbispo.githubjavapop.model.Owner;
import com.artes.alexbispo.githubjavapop.model.Repository;
import com.artes.alexbispo.githubjavapop.web.GitHubInterface;
import com.artes.alexbispo.githubjavapop.web.GitHubResponse;
import com.artes.alexbispo.githubjavapop.web.WebClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            // TODO Tratar timeout da requisição, por falta de internet por exemplo.
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            getAllRepositories(mRepositoryList);
            httpGetRepositories();
            return null;
        }

        private void httpGetRepositories() {
            GitHubInterface ghInterface = WebClient.getClient().create(GitHubInterface.class);
            Call<GitHubResponse> call = ghInterface.getJavaPopRepositories();
            call.enqueue(new Callback<GitHubResponse>() {
                @Override
                public void onResponse(Call<GitHubResponse> call, Response<GitHubResponse> response) {
                    if(response.isSuccessful()){
                        setRepositories(response.body().getItems());
                        dialog.dismiss();
                        mRepositoryAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<GitHubResponse> call, Throwable t) {
                    Log.e("HTTP", "", t);
                }
            });
        }

        private void setRepositories(List<Repository> repositoryList) {
            // TODO Verificar se não temos um problema com muito objetos em memória.
            mRepositoryList.addAll(repositoryList);
        }
    }


}

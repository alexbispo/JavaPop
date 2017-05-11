package com.artes.alexbispo.githubjavapop.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.artes.alexbispo.githubjavapop.model.Repository;
import com.artes.alexbispo.githubjavapop.web.GitHubInterface;
import com.artes.alexbispo.githubjavapop.web.GitHubResponse;
import com.artes.alexbispo.githubjavapop.web.WebClient;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alex on 10/05/17.
 */

public class LoadRepositoriesTask extends AsyncTask {

    private final Context mContext;
    private final int mPage;
//    private final RepositoryAdapter mAdapter;
//    private final Set<Repository> mDataSet;
    private Listener mListener;
    private ProgressDialog dialog;
    public static final int START_PAGE = 1;

//    public LoadRepositoriesTask(Context context, int page, RepositoryAdapter adapter, Set<Repository> dataSet) {
//        mAdapter = adapter;
//        mPage = page;
//        mContext = context;
//        mDataSet = dataSet;
//    }

    public LoadRepositoriesTask(Context context, Listener listener, int page) {
        mContext = context;
        mListener = listener;
        mPage = page;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "Por favor aguarde", "Buscando repositórios...", true, true);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        // TODO Tratar timeout da requisição, por falta de internet por exemplo.
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            getAllRepositories(mRepositorySet);
        httpGetRepositories();
        return null;
    }

    private void httpGetRepositories() {
        GitHubInterface ghInterface = WebClient.getClient().create(GitHubInterface.class);
        Call<GitHubResponse> call = ghInterface.getJavaPopRepositories(mPage);
        call.enqueue(new Callback<GitHubResponse>() {
            @Override
            public void onResponse(Call<GitHubResponse> call, Response<GitHubResponse> response) {
                if(response.isSuccessful()){
//                    setRepositories(response.body().getItems());
                    dialog.dismiss();
                    mListener.onLoadRepositoriesTaskCompleted(response.body().getItems());
//                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GitHubResponse> call, Throwable t) {
                Log.e("HTTP", "", t);
            }
        });
    }

//    private void setRepositories(Set<Repository> repositorySet) {
//        mDataSet.addAll(repositorySet);
//    }

    public interface Listener {

        void onLoadRepositoriesTaskCompleted(Set<Repository> repositorySet);
    }
}


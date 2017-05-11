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
    private Listener mListener;
    private ProgressDialog dialog;

    public LoadRepositoriesTask(Context context, Listener listener, int page) {
        mContext = context;
        mListener = listener;
        mPage = page;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "Please wait", "Searching repositories...", true, true);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        // TODO Tratar timeout da requisição, por falta de internet por exemplo.
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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
                    dialog.dismiss();
                    mListener.onLoadRepositoriesTaskCompleted(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<GitHubResponse> call, Throwable t) {
                Log.e("HTTP", "", t);
            }
        });
    }

    public interface Listener {

        void onLoadRepositoriesTaskCompleted(Set<Repository> repositorySet);
    }
}


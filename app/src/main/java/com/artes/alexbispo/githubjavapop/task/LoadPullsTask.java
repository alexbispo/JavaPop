package com.artes.alexbispo.githubjavapop.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.artes.alexbispo.githubjavapop.model.Pull;
import com.artes.alexbispo.githubjavapop.web.GitHubInterface;
import com.artes.alexbispo.githubjavapop.web.WebClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alex on 10/05/17.
 */

public class LoadPullsTask extends AsyncTask {

    private final Context mContext;
    private Listener mListener;
    private ProgressDialog dialog;
    private String mOwnerName;
    private String mRepoName;

    public LoadPullsTask(Context context, Listener listener, String ownerName, String repoName) {
        mContext = context;
        mListener = listener;
        mOwnerName = ownerName;
        mRepoName = repoName;

    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "Please wait", "Searching pull requests...", true, false);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        httpGetPullRequests();
        return null;
    }

    private void httpGetPullRequests() {
        GitHubInterface ghInterface = WebClient.getClient().create(GitHubInterface.class);
        Call<List<Pull>> call = ghInterface.getPullRequests(mOwnerName, mRepoName);
        call.enqueue(new Callback<List<Pull>>() {
            @Override
            public void onResponse(Call<List<Pull>> call, Response<List<Pull>> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    mListener.onLoadPullsTaskCompleted(response.body());
                } else {
                    mListener.onLoadPullsTaskFailed("It wasn't possible to search the pull requests, please try again late.");
                }
            }

            @Override
            public void onFailure(Call<List<Pull>> call, Throwable t) {
                if(t instanceof IOException){
                    mListener.onLoadPullsTaskFailed("Failed on attempt to connect to the server, please check your internet connection.");
                } else {
                    mListener.onLoadPullsTaskFailed("So sorry, there is anything wrong! try again late.");
                }
                dialog.dismiss();
                Log.e("HTTP", "", t);
            }
        });
    }

    public interface Listener {

        void onLoadPullsTaskCompleted(List<Pull> pullList);
        void onLoadPullsTaskFailed(String message);
    }
}


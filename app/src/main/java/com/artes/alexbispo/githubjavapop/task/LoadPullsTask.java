package com.artes.alexbispo.githubjavapop.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.artes.alexbispo.githubjavapop.model.Pull;
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
        dialog = ProgressDialog.show(mContext, "Please wait", "Searching pull requests...", true, true);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        // TODO Tratar timeout da requisição, por falta de internet por exemplo.
        httpGetPullRequests();
        return null;
    }

    private void httpGetPullRequests() {
        GitHubInterface ghInterface = WebClient.getClient().create(GitHubInterface.class);
        Call<Set<Pull>> call = ghInterface.getPullRequests(mOwnerName, mRepoName);
        call.enqueue(new Callback<Set<Pull>>() {
            @Override
            public void onResponse(Call<Set<Pull>> call, Response<Set<Pull>> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    mListener.onLoadPullsTaskCompleted(response.body());
                }
            }

            @Override
            public void onFailure(Call<Set<Pull>> call, Throwable t) {
                Log.e("HTTP", "", t);
            }
        });
    }

    public interface Listener {

        void onLoadPullsTaskCompleted(Set<Pull> pullSet);
    }
}


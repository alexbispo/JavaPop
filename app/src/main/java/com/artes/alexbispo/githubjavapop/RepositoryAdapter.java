package com.artes.alexbispo.githubjavapop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artes.alexbispo.githubjavapop.model.Repository;

import java.util.List;

/**
 * Created by alex on 10/05/17.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<Repository> repositoryList;

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {
        public TextView repositoryName, repositoryDescription, repositoryUserName;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            repositoryName = (TextView) itemView.findViewById(R.id.tv_repository_name);
            repositoryDescription = (TextView) itemView.findViewById(R.id.tv_repository_description);
            repositoryUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
        }
    }

    public RepositoryAdapter(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.repository_row, parent, false);
        return new RepositoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = repositoryList.get(position);
        holder.repositoryName.setText(repository.getName());
        holder.repositoryDescription.setText(repository.getDescription());
        holder.repositoryUserName.setText(repository.getUserName());
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }
}

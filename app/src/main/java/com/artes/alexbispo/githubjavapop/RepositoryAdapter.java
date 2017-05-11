package com.artes.alexbispo.githubjavapop;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artes.alexbispo.githubjavapop.model.Repository;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

/**
 * Created by alex on 10/05/17.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private Set<Repository> mDataset;

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {
        public TextView mRepositoryName, mRepositoryDescription, mRepositoryUserName, mRepositoryForks, mRepositoryStars;
        public ImageView mRepositoryOwner;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            mRepositoryName = (TextView) itemView.findViewById(R.id.tv_repository_name);
            mRepositoryDescription = (TextView) itemView.findViewById(R.id.tv_repository_description);
            mRepositoryUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            mRepositoryForks = (TextView) itemView.findViewById(R.id.tv_forks);
            mRepositoryStars = (TextView) itemView.findViewById(R.id.tv_stars);
            mRepositoryOwner = (ImageView) itemView.findViewById(R.id.iv_owner);
        }
    }

    public RepositoryAdapter(Set<Repository> dataSet) {
        this.mDataset = dataSet;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.repository_row, parent, false);
        return new RepositoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = (Repository) mDataset.toArray()[position];

        if(repository != null){
            holder.mRepositoryName.setText(repository.getName());
            // TODO carregar em uma async task
            Picasso.with(holder.mRepositoryOwner.getContext())
                    .load(repository.getOwner().getAvatarUrl())
                    .resize(32, 32)
                    .into(holder.mRepositoryOwner);

            StringBuilder sb = new StringBuilder();
            if(repository.getDescription().length() > 70){
                sb.append(repository.getDescription().substring(0, 70));
            } else {
                sb.append(repository.getDescription());
            }
            sb.append("...");

            holder.mRepositoryDescription.setText(sb.toString());

            holder.mRepositoryUserName.setText(repository.getOwner().getLogin());
            holder.mRepositoryForks.setText(String.valueOf(repository.getForks()));
            holder.mRepositoryStars.setText(String.valueOf(repository.getStars()));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

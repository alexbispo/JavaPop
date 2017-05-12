package com.artes.alexbispo.githubjavapop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artes.alexbispo.githubjavapop.model.Pull;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alex on 10/05/17.
 */

public class PullAdapter extends RecyclerView.Adapter<PullAdapter.PullViewHolder> {

    private List<Pull> mDataset;

    public class PullViewHolder extends RecyclerView.ViewHolder {
        public TextView mPullUserName, mPullBody, mPullTitle, mTvPullDate;
        public ImageView mPullUser;

        public PullViewHolder(View itemView) {
            super(itemView);
            mPullTitle = (TextView) itemView.findViewById(R.id.tv_pull_title);
            mPullBody = (TextView) itemView.findViewById(R.id.tv_pull_body);
            mPullUserName = (TextView) itemView.findViewById(R.id.tv_repo_owner_name);
            mPullUser = (ImageView) itemView.findViewById(R.id.iv_user);
            mTvPullDate = (TextView) itemView.findViewById(R.id.tv_pull_date);
        }
    }

    public PullAdapter(List<Pull> dataSet) {
        this.mDataset = dataSet;
    }

    @Override
    public PullViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.pull_row, parent, false);
        return new PullViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PullViewHolder holder, int position) {
        Pull pull = (Pull) mDataset.toArray()[position];

        if(pull != null){
            holder.mPullTitle.setText(pull.getTitle());

            Picasso.with(holder.mPullUser.getContext())
                    .load(pull.getUser().getAvatarUrl())
                    .resize(32, 32)
                    .into(holder.mPullUser);

            holder.mPullBody.setText(pull.getBody());
            holder.mPullUserName.setText(pull.getUser().getLogin());
            holder.mTvPullDate.setText(pull.getCreatedAtFormatted("dd/MM/yyyy"));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

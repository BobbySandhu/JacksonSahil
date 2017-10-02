package com.round.first.jacksonsahil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.round.first.jacksonsahil.R;
import com.round.first.jacksonsahil.model.AlbumInfoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.SahilsViewHolder> {
    private List<AlbumInfoModel> albumList;
    Context mContext;
    private OnItemClicked onClick;

    // interface for managing album click
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public AlbumAdapter(List<AlbumInfoModel> albumList, Context mContext) {
        this.albumList = albumList;
        this.mContext = mContext;
    }

    class SahilsViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        SahilsViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_img);
            title = itemView.findViewById(R.id.tv_album_name);
        }
    }

    @Override
    public SahilsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new SahilsViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(SahilsViewHolder holder, int position) {
        // getting current item from all albums
        AlbumInfoModel albumInfoModel = albumList.get(position);
        final int pos = position;
        holder.title.setText(albumInfoModel.getTrackName());

        // setting album art from url, if error showing defualt error image
        Picasso.with(mContext)
                .load(albumInfoModel.getArtworkUrl60())
                .error(R.drawable.icn_uncategorised_categories)
                .placeholder(R.drawable.icn_uncategorised_categories)
                .into(holder.icon);

        // setting album list click listener
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(pos);
            }
        });
    }

    // managing list click with help of this method
    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


}

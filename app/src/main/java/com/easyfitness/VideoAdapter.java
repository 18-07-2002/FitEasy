package com.easyfitness;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideoItem> videoList;
    private OnClickListener clickListener;

    public VideoAdapter(List<VideoItem> videoList, OnClickListener clickListener) {
        this.videoList = videoList;
        this.clickListener = clickListener;
    }

    public interface OnClickListener {
        void onItemClick(VideoItem videoItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView detailsTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            detailsTextView = itemView.findViewById(R.id.detailsTextView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View videoView = inflater.inflate(R.layout.video_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(videoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoItem videoItem = videoList.get(position);

        // Set the video data to the UI elements
        holder.titleTextView.setText(videoItem.getTitle());
        holder.detailsTextView.setText(videoItem.getDetails());

        // Load the thumbnail image using a library like Glide or Picasso
        Glide.with(holder.itemView.getContext())
                .load(videoItem.getThumbnailUrl())
                .into(holder.thumbnailImageView);

        // Set click listener on the item view to handle user interaction
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click event (e.g., open video details or start playback)
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickListener != null) {
                    clickListener.onItemClick(videoList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}


//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//
//
//public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
//    private List<VideoItem> videoList;
//    private FitnessVideosFragment.OnClickListener clickListener;
//
//
//    public VideoAdapter(List<VideoItem> videoList) {
//        this.videoList = videoList;
//        this.clickListener = clickListener;
//
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView thumbnailImageView;
//        public TextView titleTextView;
//        public TextView detailsTextView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
//            titleTextView = itemView.findViewById(R.id.titleTextView);
//            detailsTextView = itemView.findViewById(R.id.detailsTextView);
//        }
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View videoView = inflater.inflate(R.layout.video_item, parent, false);
//        ViewHolder viewHolder = new ViewHolder(videoView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        VideoItem videoItem = videoList.get(position);
//
//        // Set the video data to the UI elements
//        holder.titleTextView.setText(videoItem.getTitle());
//        holder.detailsTextView.setText(videoItem.getDetails());
//
//        // Load the thumbnail image using a library like Glide or Picasso
//        Glide.with(holder.itemView.getContext())
//                .load(videoItem.getThumbnailUrl())
//                .into(holder.thumbnailImageView);
//
//        // Set click listener on the item view to handle user interaction
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle item click event (e.g., open video details or start playback)
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION && clickListener != null) {
//                    clickListener.onItemClick(videoList.get(position));
//            }
//        }
//    });
//    }
//
//
//
//    @Override
//    public int getItemCount() {
//        return videoList.size();
//    }
//
//}

package com.easyfitness;


import android.content.Context;
import android.content.Intent;
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
    private Context c;

    public VideoAdapter(List<VideoItem> videoList, OnClickListener clickListener, Context c) {
        this.videoList = videoList;
        this.clickListener = clickListener;
        this.c = c;
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
        System.out.println("Video title : "+videoItem.getTitle());
        System.out.println("Video desc : "+videoItem.getDetails());
        System.out.println("Video thumb : "+videoItem.getThumbnailUrl());
        System.out.println("Video id : "+videoItem.getVideoId());

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
                Intent i = new Intent(c,FullScreeenYoutubePlayer.class);
                i.putExtra("id",videoItem.getVideoId());
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.println("VIdeo list size : "+videoList.size());
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

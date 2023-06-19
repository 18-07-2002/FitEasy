package com.easyfitness;

public class VideoItem {
    private String videoId;
    private String title;
    private String thumbnailUrl;
    private String details;

    public VideoItem(String videoId, String title, String thumbnailUrl, String details) {
        this.videoId = videoId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.details = details;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDetails() {
        return details;
    }

    // Add any additional getters and setters as needed for the video details
}

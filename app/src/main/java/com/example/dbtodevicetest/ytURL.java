package com.example.dbtodevicetest;

import com.google.gson.annotations.SerializedName;

public class ytURL {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("videoId")
    private String videoId;

    @SerializedName("title")
    private String title;

    @SerializedName("channelName")
    private String channelName;

    @SerializedName("url")
    private String url;

    // Getters and Setters

    @Override
    public String toString() {
        return "YouTubeUrl{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", channelName='" + channelName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public int getID(){ return id; }
    public String getUserID(){ return userId; }
    public String getVideoId(){ return videoId; }
    public String getTitle(){ return title; }
    public String getChannelName(){ return channelName; }
    public String getUrl(){ return url; }
}

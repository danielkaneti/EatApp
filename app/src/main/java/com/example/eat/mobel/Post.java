package com.example.eat.mobel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Post implements Serializable {
    @PrimaryKey
    @NonNull

    public String postid;
    public String postinfo;
    public String posttitle;
    public String postImgUrl;
    public String userId;
    public String username;
    public String contact;
    public long lastUpdated;


    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setPostinfo(String postinfo) {
        this.postinfo = postinfo;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPostid() {
        return postid;
    }

    public String getPostinfo() {
        return postinfo;
    }

    public String getPosttitle() {
        return posttitle;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getContact() {
        return contact;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public Post(){
        postid="";
        postImgUrl="";
        postinfo="";
        posttitle="";
        userId="";
        username="";
        contact="";
        lastUpdated=0;
    }
    public Post(String postId, String postTitle, String postContent, String postImgUrl, String userId, String userProfilePicUrl, String username, String contact){
        this.postid = postId;
        this.posttitle = postTitle;
        this.postinfo = postContent;
        this.postImgUrl = postImgUrl;
        this.userId = userId;

        this.username = username;
        this.contact = contact;
    }

}

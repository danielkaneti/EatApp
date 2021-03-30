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
    public long lastUpdated;

//seters
    public void setPostid(@NonNull String postid) {
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
    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
//geters
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
    public long getLastUpdated() {
        return lastUpdated;
    }




    //constructors


    public Post ( @NonNull String postid , String postinfo , String posttitle , String postImgUrl , String userId , String username , long lastUpdated ) {
        this.postid = postid;
        this.postinfo = postinfo;
        this.posttitle = posttitle;
        this.postImgUrl = postImgUrl;
        this.userId = userId;
        this.username = username;
        this.lastUpdated = lastUpdated;
    }

    public Post(){
        postid = "";
        posttitle = "";
        postinfo = "";
        postImgUrl = "";
        userId = "";
        username = "";
        lastUpdated =0;
    }
}

package com.example.eat.mobel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    public String userProfileImageUrl;
    public long lastUpdated;

//seters
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
    public String getUserProfileImageUrl() {return userProfileImageUrl; }

    public void setUserProfileImageUrl(String userProfileImageUrl) { this.userProfileImageUrl = userProfileImageUrl; }

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
    public String getContact() {
        return contact;
    }
    public long getLastUpdated() {
        return lastUpdated;
    }




    //constructors



    public Post ( @NonNull String postid , String postinfo , String posttitle , String postImgUrl , String userId , String username , long lastUpdated ,String userProfileImageUrl) {
        this.postid = postid;
        this.postinfo = postinfo;
        this.posttitle = posttitle;
        this.postImgUrl = postImgUrl;
        this.userId = userId;
        this.userProfileImageUrl=userProfileImageUrl;
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
        userProfileImageUrl="";
        lastUpdated =0;
    }



}

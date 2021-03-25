package com.example.eat.mobel;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.eat.EatAppApplication;

import java.util.List;

public class Model {

    public final static Model Instance=new Model();
    public static Model instance;
    ModelFirebase modelFirebase=new ModelFirebase();

    private Model(){
    }
    @SuppressLint("StaticFieldLeak")
    public void addPost(final Post post, Listener<Boolean> listener) {
        ModelFirebase.addPost(post,listener);
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.postDao().insertAllPosts(post);
                return "";
            }
        }.execute();
    }
    @SuppressLint("StaticFieldLeak")
    private void cleanLocalDb(){
        ModelFirebase.getDeletedPostsId(new Listener<List<String>>() {
            @Override
            public void onComplete(final List<String> data) {
                new AsyncTask<String,String,String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        for (String id: data){
                            Log.d("TAG", "deleted id: " + id);
                            AppLocalDb.db.postDao().deleteByPostId(id);
                        }
                        return "";
                    }
                }.execute("");
            }
        });
    }
    public void refreshPostsList(final CompListener listener){


    }
    @SuppressLint("StaticFieldLeak")
    public void deletePost(final Post post, Listener<Boolean> listener){
        ModelFirebase.deletePost(post,listener);
        new AsyncTask<String,String,String>(){
            @Override
            protected String doInBackground(String... strings) {
                AppLocalDb.db.postDao().deletePost(post);

                return "";
            }
        }.execute();
    }

    LiveData<List<Post>> data;

    public LiveData<List<Post>> getAllPosts(){
        data = AppLocalDb.db.postDao().getAllPosts();
        refreshPostsList(null);
        return data;
    }

    void addPost(Post post){

        AppLocalDb.db.postDao().insertAllPosts(post);
    }


    public interface CompListener{
        void onComplete();
    }
    public interface Listener<T>{
        void onComplete(T data);
    }



}

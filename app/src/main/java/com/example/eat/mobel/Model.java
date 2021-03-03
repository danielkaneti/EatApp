package com.example.eat.mobel;


import androidx.lifecycle.LiveData;

import java.util.List;

public class Model {

    public final static Model Instance=new Model();
    public static Model instance;
    ModelFirebase modelFirebase=new ModelFirebase();

    private Model(){
    }

    LiveData<List<Post>> data;

    public LiveData<List<Post>> getAllPosts(){
        data = AppLocalDb.db.PostDao().getAllPosts();
        return data;
    }

    void addPost(Post post){

        AppLocalDb.db.PostDao().insertAllPosts(post);
    }


    public interface CompListener{
        void onComplete();
    }
    public interface Listener<T>{
        void onComplete(T data);
    }



}

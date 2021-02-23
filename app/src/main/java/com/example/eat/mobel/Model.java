package com.example.eat.mobel;


import androidx.lifecycle.LiveData;

import java.util.List;

public class Model {

    List<Post> data;
    public final static Model Instance=new Model();
    public static Model instance;
    ModelFirebase modelFirebase=new ModelFirebase();

    public LiveData<List<Post>>getAllPosts(){
        LiveData<List<Post> >Data = AppLocalDb.db.PostDao().getAllPosts();

        return Data;
    }


    public interface CompListener{
        void onComplete();
    }
    public interface Listener<T>{
        void onComplete(T data);
    }



}

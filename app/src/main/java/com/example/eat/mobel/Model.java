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

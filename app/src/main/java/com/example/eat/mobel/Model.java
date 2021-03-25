package com.example.eat.mobel;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;


import java.util.List;

public class Model {

//    public final static Model Instance=new Model();
//    public static Model instance;
    ModelFirebase modelFirebase=new ModelFirebase();
    ModelSql modelSql=new ModelSql();
    public  final static Model instance=new Model();


    private Model(){
    }



    public interface GetAllPostListener {
        void onComplete(List<Post> data);
    }
    public void getAllPost(final GetAllPostListener listener){
        modelFirebase.getAllPost(listener);

    }
    public interface AddPostListener{
        void onComplete();
    }
    public void addPost(Post post, AddPostListener listener){
        modelFirebase.addPost(post,listener);

    }
    public interface CompListener{
        void onComplete();
    }
    public interface Listener<T>{
        void onComplete(T data);
    }



}

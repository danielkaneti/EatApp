package com.example.eat.mobel;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.eat.EatAppApplication;

import java.util.List;

public class Model {

//    public final static Model Instance=new Model();
//    public static Model instance;
    ModelFirebase modelFirebase=new ModelFirebase();
    ModelSql modelSql=new ModelSql();
    public  final static Model instance=new Model();


    private Model(){
    }

    public LiveData<List<Post>> getAllPosts() {
        LiveData<List<Post>> liveData = AppLocalDb.db.postDao().getAllPosts();
        refreshPostsList(null);
        return liveData;
    }

    public void refreshPostsList(CompListener listener) {
        long lastUpdated = EatAppApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("PostsLastUpdateDate",0);
        ModelFirebase.getAllPostsSince(lastUpdated,new Listener<List<Post>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Post> data) {
                new AsyncTask<String,String,String>(){
                    @Override
                    protected String doInBackground(String... strings) {
                        long lastUpdated = 0;
                        for(Post p: data){
                            AppLocalDb.db.postDao().insertAllPosts(p);
                            if (p.lastUpdated > lastUpdated)
                                lastUpdated = p.lastUpdated;
                        }
                        SharedPreferences.Editor edit = EatAppApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
                        edit.putLong("PostsLastUpdateDate",lastUpdated);
                        edit.commit();
                        return "";
                    }
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                     //   cleanLocalDb();
                        if (listener!=null)
                            listener.onComplete();
                    }
                }.execute("");
            }
        });
    }


    public interface GetAllPostListener {
        void onComplete(List<Post> data);
    }
    public void getAllPost(final GetAllPostListener listener){
        modelFirebase.getAllPost(listener);

    }
    public interface UploadImageListener{
        public void onComplete(String url);
    }

    public void uploadImage(Bitmap imageBmp,String name, final UploadImageListener listener){
        modelFirebase.uploadImage ( imageBmp,name,listener );
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

    public interface GetPostListener {
        void onComplete(Post p);
    }

//    public void getPost (String id, GetPostListener listener){
//        modelFirebase.getPost ( id,  listener);
//    }

    public void updateUserProfile(String username, String info, String profileImgUrl, Listener<Boolean> listener) {
        ModelFirebase.updateUserProfile(username, info, profileImgUrl, listener);
    }

    public void setUserAppData(String email){
        ModelFirebase.setUserAppData(email);
    }

}

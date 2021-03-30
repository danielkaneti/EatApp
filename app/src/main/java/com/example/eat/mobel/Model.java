package com.example.eat.mobel;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.eat.EatAppApplication;

import java.util.List;

public class Model {

    ModelFirebase modelFirebase=new ModelFirebase();
    ModelSql modelSql=new ModelSql();
    public  final static Model instance=new Model();


    private Model(){
    }

    public interface CompListener{
        void onComplete();
    }
    public interface Listener<T>{
        void onComplete(T data);
    }

    public interface GetAllPostListener {
        void onComplete(List<Post> data);
    }
//    public void getAllPost(final GetAllPostListener listener){
//        modelFirebase.getAllPost(listener);
//
//    }

    public List<Post> getAllPost(){
        List<Post> liveData = AppLocalDb.db.postDao().getAll();
        refreshPostsList(null);
        return liveData;
    }

    public void refreshPostsList(final CompListener listener){
        long lastUpdated = EatAppApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("PostsLastUpdateDate",0);
        ModelFirebase.getAllPost(lastUpdated,new Listener<List<Post>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Post> data) {
                new AsyncTask<String,String,String> (){
                    @Override
                    protected String doInBackground(String... strings) {
                        long lastUpdated = 0;
                        for(Post p: data){
                            AppLocalDb.db.postDao().insertAll(p);
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
                        //cleanLocalDb();
                        if (listener!=null)
                            listener.onComplete();
                    }
                }.execute("");
            }
        });
    }

    public interface GetPostListener {
        void onComplete(Post p);
    }

    public void getPost (String id, GetPostListener listener){
        modelFirebase.getPost ( id,  listener);
    }

    public interface AddPostListener{
        void onComplete();
    }
    public void addPost(final Post post,final AddPostListener listener){
        modelFirebase.addPost(post,listener);

    }
    interface DeleteListener extends AddPostListener{}
    public void deletePost (Post post, DeleteListener listener){
        modelFirebase.deletePost(post,listener);
    }

    public interface UpdatePostListener extends AddPostListener{ }

    public void updatePost(final Post post, final AddPostListener listener){
        modelFirebase.updatePost(post,listener);
    }

    public interface UploadImageListener{
        public void onComplete(String url);
    }

    public void uploadImage( Bitmap imageBmp, final UploadImageListener listener){
        modelFirebase.uploadImage ( imageBmp,listener );
    }

}

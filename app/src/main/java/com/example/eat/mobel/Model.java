package com.example.eat.mobel;


<<<<<<< Updated upstream
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;


import java.util.List;

public class Model {

//    public final static Model Instance=new Model();
//    public static Model instance;
=======
>>>>>>> Stashed changes
    ModelFirebase modelFirebase=new ModelFirebase();
    ModelSql modelSql=new ModelSql();
    public  final static Model instance=new Model();


    private Model(){
    }



<<<<<<< Updated upstream
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
=======
>>>>>>> Stashed changes

    public interface GetPostListener {
        void onComplete(Post p);
    }

    public void getPost (String id, GetPostListener listener){
        modelFirebase.getPost ( id,  listener);
    }

}
}
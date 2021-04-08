package com.example.eat.mobel;


import android.graphics.Bitmap;

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

    public void getPost (String id, GetPostListener listener){
        modelFirebase.getPost ( id,  listener);
    }

    public void updateUserProfile(String username, String info, String profileImgUrl, Listener<Boolean> listener) {
        ModelFirebase.updateUserProfile(username, info, profileImgUrl, listener);
    }

    public void setUserAppData(String email){
        ModelFirebase.setUserAppData(email);
    }

}

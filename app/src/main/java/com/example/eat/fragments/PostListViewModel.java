package com.example.eat.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;

import java.util.List;

public class PostListViewModel extends ViewModel {

    LiveData<List<Post>> liveData;

    public LiveData<List<Post>> getData(){
        if (liveData == null)
            liveData = Model.instance.getAllPosts();
        return liveData;
    }

    public void refresh(Model.CompListener listener){
        Model.instance.refreshPostsList(listener);
    }
}

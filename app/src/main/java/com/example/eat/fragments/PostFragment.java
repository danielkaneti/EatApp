package com.example.eat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.eat.R;
import com.example.eat.mobel.Post;
import com.example.eat.mobel.User;

import java.text.BreakIterator;
import java.util.UUID;

public class PostFragment extends Fragment {
    EditText postTitleInput;
    EditText postInfotInput;
    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post, container, false);


        Button postBtn = view.findViewById ( R.id.postlistfrag_AddPostBtn );
        postBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                //save the student
                savePost();


            }
        } );

        return view;
    }


    private Post generateNewPost(){
        Post newPost = new Post();
        newPost.postid = UUID.randomUUID().toString();

        newPost.postImgUrl = null;
        newPost.userId = User.getInstance().userId;

        newPost.username = User.getInstance().Username;

        return newPost;
    }
    void savePost(){

        final Post newPost = generateNewPost();


    }
}
package com.example.eat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.eat.mobel.Post;
import com.squareup.picasso.Picasso;

public class PostDetails extends Fragment {


    Post post;
    View view;
    TextView postTitle;
    TextView username;
    TextView postContent;
    ImageView postImg;

    public PostDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_post_details2, container, false);
        postTitle = view.findViewById(R.id.post_details_fragment_title_text_view);
        username = view.findViewById(R.id.post_details_fragment_username_text_view);
        postContent = view.findViewById(R.id.post_details_fragment_post_content_text_view);

        postImg = view.findViewById(R.id.post_details_fragment_post_image_view);


        post=PostDetailsArgs.fromBundle(getArguments()).getPost();
        if (post != null) {
            postTitle.setText(post.posttitle);
            username.setText(post.username);
            postContent.setText(post.postinfo);
            if (post.postImgUrl != null) {
                Picasso.get().load(post.postImgUrl).noPlaceholder().into(postImg);

            }


        }
        return view;
    }
}
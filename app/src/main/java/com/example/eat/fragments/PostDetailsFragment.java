package com.example.eat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eat.R;
import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;
import com.example.eat.mobel.User;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailsFragment extends Fragment {


    Post post;
    View view;
    TextView postTitle;
    TextView username;
    TextView postContent;
    ImageView postImg;
    Button editpostbtn;
    Button deletepostbtn;
    CircleImageView profilePic;

    public PostDetailsFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.fragment_post_details2, container, false);
        postTitle = view.findViewById(R.id.post_details_fragment_title_text_view);
        username = view.findViewById(R.id.post_details_fragment_username_text_view);
        postContent = view.findViewById(R.id.post_details_fragment_post_content_text_view);
        postImg = view.findViewById(R.id.post_details_fragment_post_image_view);
//        profilePic = view.findViewById ( R.id.list_row_profile_image_view );

        post = PostDetailsFragmentArgs.fromBundle ( getArguments () ).getPost ();
        if(post!=null)
        {
            postTitle.setText ( post.posttitle );
            username.setText ( post.username );
            postContent.setText ( post.postinfo );
            if(post.postImgUrl != null ){
                Picasso.get ().load ( post.postImgUrl ).noPlaceholder ().into ( postImg );
 //               Picasso.get ().load ( post.userProfileImageUrl ).noPlaceholder ().into ( profilePic );
            }
        }
        else{
            postImg.setImageResource ( R.drawable.profile_pic_placeholder );
            profilePic.setImageResource ( R.drawable.profile_pic_placeholder );
        }

        editpostbtn = view.findViewById ( R.id.post_details_fragment_edit_btn );
        editpostbtn.setVisibility ( View.INVISIBLE );
        deletepostbtn = view.findViewById ( R.id.post_details_fragment_delete_btn );
        deletepostbtn.setVisibility ( View.INVISIBLE );

        if(post.userId.equals ( User.getInstance ().userId )){
            editpostbtn.setVisibility ( View.VISIBLE );
            editpostbtn.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick ( View v ) {
                    toEditPostPage(post);
                }
            } );

            deletepostbtn.setVisibility ( View.VISIBLE );
            deletepostbtn.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick ( View v ) {
                    deletePost(post);
                }
            } );
        }


        return view;
    }

    private void deletePost ( Post postToDelete ) {

        Model.instance.deletePost ( postToDelete , new Model.DeleteListener ( ) {
            @Override
            public void onComplete ( ) {
                Model.instance.deleteImage ( post.postImgUrl , new Model.DeleteImageListener ( ) {
                    @Override
                    public void onSuccess ( String s ) {
                        NavController navController = Navigation.findNavController ( view );
                        navController.navigateUp ();
                    }

                    @Override
                    public void onFail ( ) {
                        Snackbar.make ( view,"Failed to create post and save it in databases" , Snackbar.LENGTH_LONG).show ();
                    }
                } );
            }
        } );
    }

    private void toEditPostPage (Post post) {
        NavController navController = Navigation.findNavController ( getActivity (),R.id.homeActivity_navHostfragment );
        PostDetailsFragmentDirections.ActionPostDetailsFragmentToEditPostFragment3 directions = PostDetailsFragmentDirections.actionPostDetailsFragmentToEditPostFragment3 ( post );
        navController.navigate ( directions );
    }
}
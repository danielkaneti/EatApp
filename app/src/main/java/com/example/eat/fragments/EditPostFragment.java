package com.example.eat.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eat.R;
import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;
import com.example.eat.mobel.User;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.IOException;

public class EditPostFragment extends Fragment {

    View view;
    Post post;
    EditText titleInput;
    EditText contentInput;
    Button saveChangesBtn;
    ImageView postImageView;
    //ProgressBar progressBar;

    Uri postImageUri;
    Bitmap postImgBitmap;
    static int REQUEST_CODE = 1;

    public EditPostFragment ( ) {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
                               Bundle savedInstanceState ) {
        view= inflater.inflate ( R.layout.fragment_edit_post , container , false );
        titleInput = view.findViewById ( R.id.edit_post_fragment_title_edit_text );
        contentInput=view.findViewById ( R.id.edit_post_fragment_content_edit_text );
        postImageView=view.findViewById ( R.id.edit_post_fragment_image_view );
        //progressBar = view.findViewById ( R.id.edit_post_fragment_progress_bar );
        //progressBar.setVisibility ( View.INVISIBLE );

        post= PostDetailsFragmentArgs.fromBundle ( getArguments () ).getPost ();
        if(post!=null){
            setEditPostHints();
        }

        postImageView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                chooseImageFromGallery ();
            }
        } );

        saveChangesBtn = view.findViewById ( R.id.edit_post_fragment_save_btn );
        saveChangesBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                updatePost();
            }
        } );

        return view;
    }

    private void updatePost ( ) {
        //progressBar.setVisibility ( View.VISIBLE );

        if(postImageUri!=null){
            Model.instance.uploadImage ( postImgBitmap , User.getInstance ( ).Username , new Model.UploadImageListener ( ) {
                @Override
                public void onComplete ( String url ) {
                    if (url == null) {
                        //progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(view, "Faild ", Snackbar.LENGTH_LONG).show();
                    } else {
                        Model.instance.addPost ( generatedEditedPost(url), new Model.AddPostListener ( ) {
                            @Override
                            public void onComplete ( ) {
                                Model.instance.setUserAppData ( User.getInstance ( ).userEmail );
                                NavController navController = Navigation.findNavController ( view );
                                navController.navigateUp ( );
                                navController.navigateUp ( );
                            }
                        });
                    }
                }
            } );
        }
        else{
            Model.instance.addPost ( generatedEditedPost(null), new Model.AddPostListener ( ) {
                @Override
                public void onComplete ( ) {
                    NavController navController =Navigation.findNavController ( view );
                    navController.navigateUp ();
                    navController.navigateUp ();
                }
            } );
        }
    }

    private Post generatedEditedPost ( String url ) {
        Post editedPost = post;
        if(titleInput.getText ().toString ()!=null && !titleInput.getText ().toString ().equals ( "" ))
            editedPost.posttitle = titleInput.getText ().toString ();
        else editedPost.posttitle = post.posttitle;
        if(contentInput.getText ().toString ()!= null && !contentInput.getText ().toString ().equals ( "" ))
            editedPost.postinfo = contentInput.getText ().toString ();
        else editedPost.postinfo = post.postinfo;
        if(url!=null)
            editedPost.postImgUrl=url;

        return editedPost;
    }

    private void setEditPostHints ( ) {
        if(post.postImgUrl!=null){
            Picasso.get ().load ( post.postImgUrl ).noPlaceholder ().into ( postImageView );
        }
        titleInput.setText ( post.posttitle );
        contentInput.setText ( post.postinfo );
    }

    private void chooseImageFromGallery(){
        try{
            Intent openGalleryIntent = new Intent (Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType ( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*" );

            startActivityForResult ( openGalleryIntent, REQUEST_CODE );
        }catch (Exception e){
            Toast.makeText ( getContext (),"Edit post Page:" +e.getMessage (),Toast.LENGTH_SHORT ).show ();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null && data != null){
            postImageUri = data.getData();
            postImageView.setImageURI(postImageUri);
            postImgBitmap = uriToBitmap(postImageUri);

        }
        else {
            Toast.makeText(getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }


    private Bitmap uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
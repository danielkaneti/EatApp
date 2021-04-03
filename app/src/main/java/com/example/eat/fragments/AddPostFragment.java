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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eat.R;
import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;
import com.example.eat.mobel.User;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddPostFragment extends Fragment {
    View view;
    ProgressBar progressBar;
    EditText postTitleInput;
    EditText postContentInput;
    ImageView postImageView;
    Uri postImgUri;
    Bitmap postImgBitmap;
    static int REQUEST_CODE=1;

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
                               Bundle savedInstanceState ) {
        view = inflater.inflate(R.layout.fragment_post, container, false);

        progressBar = view.findViewById(R.id.new_post_fragment_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        postImageView = view.findViewById(R.id.new_post_fragment_image_view);
        postTitleInput = view.findViewById(R.id.new_post_fragment_title_edit_text);
        postContentInput = view.findViewById(R.id.new_post_fragment_content_edit_text);

        postImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromGallery();
            }
        });

        Button publishBtn = view.findViewById(R.id.new_post_fragment_publish_btn);
        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( postTitleInput != null && postContentInput != null)
                    savePost();
                else
                    Toast.makeText(getContext(), "Please fill all fields and add a photo", Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    private void savePost ( ) {
        progressBar.setVisibility ( View.VISIBLE );
        Post post= new Post ();
        post.postid= UUID.randomUUID ().toString ();
        post.posttitle = postTitleInput.getText ().toString ();
        post.postinfo = postContentInput.getText ().toString ();
        post.postImgUrl=null;
        post.userId = User.getInstance ().userId;
        post.username = User.getInstance ().Username;


        Model.instance.uploadImage ( postImgBitmap ,post.postid, new Model.UploadImageListener ( ) {
            @Override
            public void onComplete ( String url ) {
                if(url==null){
                    progressBar.setVisibility ( View.INVISIBLE );
                    Snackbar.make ( view,"Faild to create post" , Snackbar.LENGTH_LONG).show ();
                }else{
                    post.setPostImgUrl ( url );
                    Model.instance.addPost ( post , new Model.AddPostListener ( ) {
                        @Override
                        public void onComplete ( ) {
                            NavController navController = Navigation.findNavController ( view );
                            navController.navigateUp ();
                        }
                    } );
                }
            }
        } );
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    void chooseImageFromGallery(){
        try{
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            startActivityForResult(openGalleryIntent, REQUEST_CODE);
        }
        catch (Exception e){
            Toast.makeText(getActivity(), "New post Page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
//        Intent takePictureIntent = new Intent(
//                MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity ().getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
    }

    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null  && resultCode == RESULT_OK) {
            postImgUri = data.getData();
            postImageView.setImageURI(postImgUri);
            postImgBitmap = uriToBitmap(postImgUri);
        }
        else {
            Toast.makeText(getActivity(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap uriToBitmap ( Uri postImgUri ) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContext().getContentResolver().openFileDescriptor(postImgUri, "r");
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
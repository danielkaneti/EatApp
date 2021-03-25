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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eat.R;
import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;
import com.example.eat.mobel.StoreModel;
import com.example.eat.mobel.User;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class PostFragment extends Fragment {
    View view;
    ProgressBar progressBar;
    EditText postTitleInput;
    EditText postContentInput;
    EditText contactInput;
    ImageView postImageView;
    Uri postImgUri;
    Bitmap postImgBitmap;
    static int REQUEST_CODE = 1;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);

        progressBar = view.findViewById(R.id.new_post_fragment_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        postImageView = view.findViewById(R.id.new_post_fragment_image_view);
        postTitleInput = view.findViewById(R.id.new_post_fragment_title_edit_text);
        postContentInput = view.findViewById(R.id.new_post_fragment_content_edit_text);
        contactInput = view.findViewById(R.id.new_post_fragment_contact_edit_text);

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
                if ( postTitleInput != null && postContentInput != null && contactInput != null)
                    savePost();
                else
                    Toast.makeText(getContext(), "Please fill all fields and add a photo", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    void savePost(){
        progressBar.setVisibility(View.VISIBLE);
        final Post newPost = generateNewPost();

        StoreModel.uploadImage(postImgBitmap, new StoreModel.Listener() {
            @Override
            public void onSuccess(String url) {
                newPost.postImgUrl = url;
                Model.instance.addPost(newPost, new Model.Listener<Boolean>() {
                    @Override
                    public void onComplete(Boolean data) {
                        NavController navCtrl = Navigation.findNavController(view);
                        navCtrl.navigateUp();
                    }
                });
            }

            @Override
            public void onFail() {
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar.make(view, "Failed to create post and save it in databases", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    void chooseImageFromGallery(){
        try{
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            startActivityForResult(openGalleryIntent, REQUEST_CODE);
        }
        catch (Exception e){
            Toast.makeText(getActivity(), "New post Page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null && data != null && resultCode == RESULT_OK) {
            postImgUri = data.getData();
            postImageView.setImageURI(postImgUri);
            postImgBitmap = uriToBitmap(postImgUri);
        }
        else {
            Toast.makeText(getActivity(), "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    private Post generateNewPost(){
        Post newPost = new Post();
        newPost.postid = UUID.randomUUID().toString();
        newPost.posttitle = postTitleInput.getText().toString();
        newPost.postinfo = postContentInput.getText().toString();
        newPost.postImgUrl = null;
        newPost.userId = User.getInstance().userId;

        newPost.username = User.getInstance().Username;
        newPost.contact = contactInput.getText().toString();
        return newPost;
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
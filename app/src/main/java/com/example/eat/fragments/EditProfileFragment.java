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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eat.R;
import com.example.eat.mobel.Model;
import com.example.eat.mobel.User;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    View view;

    CircleImageView profilePicImageView;

    EditText infoInput;
    Button saveProfileBtn;
    ImageButton closeBtn;
    ProgressBar progressBar;
    Uri profileImageUrl;
    Bitmap postImgBitmap;
    static int REQUEST_CODE = 1;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        profilePicImageView = view.findViewById(R.id.edit_profile_fragment_profile_image_view);

        infoInput = view.findViewById(R.id.edit_profile_fragment_info_edit_text);
        progressBar = view.findViewById(R.id.edit_post_fragment_progress_bar );
        progressBar.setVisibility(View.INVISIBLE);

        profilePicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromGallery();
            }
        });

        closeBtn = view.findViewById(R.id.edit_profile_fragment_close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navCtrl = Navigation.findNavController(view);
                navCtrl.navigateUp();
            }
        });

        saveProfileBtn = view.findViewById(R.id.edit_profile_fragment_save_btn);
        saveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();
            }
        });
        setEditProfileHints();
        return view;
    }

    private void setEditProfileHints(){
        if (User.getInstance().profileImageUrl != null) {
            Picasso.get().load(User.getInstance().profileImageUrl).noPlaceholder().into(profilePicImageView);
        }

        infoInput.setHint(User.getInstance().userInfo);
    }

    void updateUserProfile() {
        final String username;
        final String info;


            progressBar.setVisibility(View.VISIBLE);

            if (infoInput.getText().toString() != null && !infoInput.getText().toString().equals(""))
                info = infoInput.getText().toString();
            else info = User.getInstance().userInfo;

        if (profileImageUrl != null) {
            Model.instance.uploadImage(postImgBitmap, User.getInstance().Username, new Model.UploadImageListener() {
                @Override
                public void onComplete(String url) {
                    if (url == null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(view, "Faild ", Snackbar.LENGTH_LONG).show();
                    } else {
                        Model.instance.updateUserProfile( info, url, new Model.Listener<Boolean>() {
                            @Override
                            public void onComplete(Boolean data) {
                                Model.instance.setUserAppData(User.getInstance().userEmail);
                                NavController navCtrl = Navigation.findNavController(view);
                                navCtrl.navigateUp();
                                navCtrl.navigateUp();
                            }
                        });
                    }
                }
            });
        }

       else {
            Model.instance.updateUserProfile( info, null, new Model.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    Model.instance.setUserAppData(User.getInstance().userEmail);
                    NavController navCtrl = Navigation.findNavController(view);
                    navCtrl.navigateUp();
                    navCtrl.navigateUp();
                }
            });
        }
    }

    private void chooseImageFromGallery(){

        try{
            Intent openGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openGalleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

            startActivityForResult(openGalleryIntent, REQUEST_CODE);
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Edit profile Page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null && data != null){
            profileImageUrl = data.getData();
            profilePicImageView.setImageURI(profileImageUrl);
            postImgBitmap = uriToBitmap(profileImageUrl);

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
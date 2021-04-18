package com.example.eat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eat.mobel.ModelFirebase;

import de.hdodenhof.circleimageview.CircleImageView;


public class Register extends AppCompatActivity {
    EditText emailInput;
    EditText passwordInput;
    EditText username;
    CircleImageView profileImageView;
    ProgressBar progressBar;
    Button registerBtn;
    Uri profileImageUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailInput = findViewById(R.id.register_activity_email_edit_text);
        username = findViewById(R.id.register_activity_user_edit_text);
        profileImageView = findViewById ( R.id.list_row_profile_image_view );

        profileImageView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Utils.chooseImageFromGallery ( Register.this );
            }
        } );

        progressBar = findViewById(R.id.register_activity_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        passwordInput = findViewById(R.id.register_activity_password_edit_text);
        registerBtn = findViewById(R.id.register_newaccount_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                ModelFirebase.registerUserAccount(username.getText().toString(), passwordInput.getText().toString(), emailInput.getText().toString(), profileImageUri,new ModelFirebase.Listener<Boolean>() {
                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d ( "TAG", "user registered");
                        Register.this.finish();

                    }

                    @Override
                    public void onFail() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d ( "TAG", "failed");
                    }
                });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null && data != null){
            profileImageUri = data.getData();
            profileImageView.setImageURI(profileImageUri);
        }
        else {
            Toast.makeText(this, "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

}
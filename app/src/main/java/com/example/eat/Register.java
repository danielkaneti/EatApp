package com.example.eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.eat.mobel.ModelFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText emailInput;
    EditText passwordInput;
    EditText username;
    ProgressBar progressBar;
    Button registerBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailInput = findViewById(R.id.register_activity_email_edit_text);
        username = findViewById(R.id.register_activity_user_edit_text);

        progressBar = findViewById(R.id.register_activity_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        passwordInput = findViewById(R.id.register_activity_password_edit_text);
        registerBtn = findViewById(R.id.register_newaccount_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                ModelFirebase.registerUserAccount(username.getText().toString(), passwordInput.getText().toString(), emailInput.getText().toString(), new ModelFirebase.Listener<Boolean>() {
                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Register.this.finish();

                    }

                    @Override
                    public void onFail() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });


            }
        });
    }

}
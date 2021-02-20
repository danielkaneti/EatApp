package com.example.eat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.example.eat.mobel.ModelFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity  {
    private static final String AUTHENTICATION_FAILED_MESSAGE = "Authentication failed.";
    static final String INVALID_FORM_MESSAGE = "Form not valid. Please try again.";
    private static final String INVALID_EMAIL_MESSAGE = "Email not valid.";
    private static final String INVALID_PASSWORD_MESSAGE = "Password must be minimum 4 length.";

    EditText emailInput;
    EditText passwordInput;
    Button loginBtn;
    Button registerBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    Activity view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.setTitle("Login");


        emailInput = findViewById(R.id.login_activity_email_edit_text);
        passwordInput = findViewById(R.id.login_activity_password_edit_text);
        loginBtn=findViewById(R.id.login_activty);
         progressBar=findViewById(R.id.Login_activity_progress_bar);
        loginBtn.setOnClickListener(new View.OnClickListener() {
         @Override
        public void onClick(View v) {
        ModelFirebase.loginUser(emailInput.getText().toString(),passwordInput.getText().toString(), new ModelFirebase.Listener<Boolean>() {
            @Override
            public void onComplete() {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(Login.this, HomeActivity.class));

            }

            @Override
            public void onFail() {

            }
        });
        }
            });

        registerBtn = findViewById(R.id.Regesrer_btn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterPage();
            }
        });


    }



    private void toRegisterPage() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


}
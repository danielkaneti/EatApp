package com.example.eat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText emailInput;
    EditText passwordInput;
    EditText username;
    Button registerBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailInput = findViewById(R.id.register_activity_email_edit_text);
        username=findViewById(R.id.register_activity_user_edit_text);
        passwordInput=findViewById(R.id.register_activity_password_edit_text);
        registerBtn=findViewById(R.id.register_newaccount_btn);


    }
}
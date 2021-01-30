package com.example.eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ImageView backgroundImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        backgroundImageView = findViewById(R.id.main_background_image_view);
        Utils.animateBackground(backgroundImageView, 3500);

        new Thread() {
            public void run()
            {
                try {
                    //Display for 3 seconds
                    sleep(3000);
                } catch (InterruptedException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    //Goes to login
                    toLoginPage();
                }
            }
        }.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void toLoginPage(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}
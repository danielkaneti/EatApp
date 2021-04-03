package com.example.eat;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.eat.fragments.PostListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        navController = Navigation.findNavController(this,R.id.homeActivity_navHostfragment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavigationUI.setupWithNavController (bottomNavigationView,navController);

    }


}
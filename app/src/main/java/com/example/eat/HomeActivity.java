package com.example.eat;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.eat.fragments.PostListFragment;

public class HomeActivity extends AppCompatActivity {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navController = Navigation.findNavController(this,R.id.homeActivity_navHostfragment);
        NavigationUI.setupActionBarWithNavController(this,navController);

        PostListFragment postList = new PostListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.add(R.id.home_frg_container,postList);
        tran.commit();



        //BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        //bottomNavigationView.setOnNavigationItemReselectedListener(navListener);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            navController.navigateUp();
            return true;
        }
            return super.onOptionsItemSelected(item);

    }
}
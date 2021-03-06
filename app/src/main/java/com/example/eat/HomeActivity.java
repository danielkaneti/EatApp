package com.example.eat;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.eat.fragments.PostDetailsFragment;
import com.example.eat.fragments.PostFragment;
import com.example.eat.fragments.PostListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        //navController = Navigation.findNavController(this,R.id.homeActivity_navHostfragment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationitemSelecedLisener);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_frg_container, new PostListFragment()).commit();
        //NavigationUI.setupActionBarWithNavController(this,navController);


        //PostListFragment postList = new PostListFragment();
        //FragmentManager manager = getSupportFragmentManager();
        //FragmentTransaction tran = manager.beginTransaction();
        //tran.add(R.id.home_frg_container,postList);
        //tran.commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationitemSelecedLisener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedd = null;

            switch (item.getItemId()){
                case R.id.post:
                    selectedd=new PostFragment();
                    break;
                case R.id.profile:
                    selectedd=new PostDetailsFragment();
                    break;
                case R.id.list:
                    selectedd=new PostListFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.home_frg_container,selectedd).commit();
            return true;
        }
    };

}
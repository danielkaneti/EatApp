package com.example.eat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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

//    private BottomNavigationView.OnNavigationItemSelectedListener navigationitemSelecedLisener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//            Fragment selectedd = null;
//
////            switch (item.getItemId()){
////                case R.id.addPostFragment:
////
////                    break;
////                case R.id.profile:
////
////                    break;
////                case R.id.postListFragment2:
////                    selectedd=new PostListFragment();
////                    break;
////
////            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.home_frg_container,selectedd).commit();
//            return true;
//        }
//    };

}
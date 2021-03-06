package com.example.eat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat.HomeActivity;
import com.example.eat.Login;
import com.example.eat.R;
import com.example.eat.adapters.PostsAdapter;
import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PostListFragment extends Fragment {
    LinearLayoutManager layoutManager;
    RecyclerView postlist;
    LiveData<List<Post>> data;

    public  PostListFragment(){
        //Empty Constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post_list, container, false);
        postlist= view.findViewById(R.id.postlistfrag_recycler_v);
        postlist.hasFixedSize();

        layoutManager = new LinearLayoutManager(getContext());
        postlist.setLayoutManager(layoutManager);

        data = Model.Instance.getAllPosts();

        PostsAdapter adapter = new PostsAdapter ( getLayoutInflater () );
        adapter.data=data;
        postlist.setAdapter (adapter);

        adapter.setOnClickListener(new PostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick ( int position ) {
                Log.d ("TAG", "row was clicked" + position);
            }
        });

        //ProgressBar pb= view.findViewById(R.id.postlist_frag);
        //pb.setVisibility(View.INVISIBLE);

        FloatingActionButton addBtn = view.findViewById ( R.id.postlistfrag_AddPostBtn );
        addBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Navigation.findNavController(view).navigate(R.id.action_postDetails_to_postList);
    }
    });


        return view;
    }

}
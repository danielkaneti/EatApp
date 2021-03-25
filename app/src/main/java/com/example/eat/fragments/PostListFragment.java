package com.example.eat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eat.HomeActivity;
import com.example.eat.Login;
import com.example.eat.R;
import com.example.eat.adapters.PostsAdapter;
import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class PostListFragment extends Fragment {
    LinearLayoutManager layoutManager;
    RecyclerView postlist;
    LiveData<List<Post>> data;
    PostListViewFragment viewModel;

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
        //live data

        viewModel = new ViewModelProvider(this).get(PostListViewFragment.class);



        PostsAdapter adapter = new PostsAdapter ( getLayoutInflater () );
        adapter.data=data;
        postlist.setAdapter (adapter);


        adapter.setOnClickListener(new PostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick ( int position ) {

                Log.d ("TAG", "row was clicked" + position);
            }
        });

        data=Model.Instance.getAllPosts();



        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.feed_list_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh(new Model.CompListener() {
                    @Override
                    public void onComplete() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });


        return view;
    }
    private List<Post> reverseData(List<Post> posts) {
        List<Post> reversedData = new LinkedList<>();
        for (Post post: posts) {
            reversedData.add(0, post);
        }
        return reversedData;
    }
}
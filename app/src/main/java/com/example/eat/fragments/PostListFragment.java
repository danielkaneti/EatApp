package com.example.eat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class PostListFragment extends Fragment {
    RecyclerView list;
    List<Post> postList = new LinkedList<>();
    //ProgressBar pb;
    Button addBtn;
    MyAdapter adapter;

    public PostListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        RecyclerView list = view.findViewById(R.id.postlist_recycler_v);
        list.hasFixedSize();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);

        //pb = view.findViewById ( R.id.postlistfrag_progbar );
        addBtn = view.findViewById(R.id.postlist_frag_addbtn);
        //pb.setVisibility ( View.INVISIBLE );

        adapter = new MyAdapter();
        list.setAdapter(adapter);

        //postList = Model.instance.getAllPost();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPost();
                Log.d("id = ", "hello ");
            }
        });
        reloadData();
        return view;
    }

    static int id = 0;

    private void addNewPost() {
        Post post = new Post();
        post.setPostid("" + id);
        post.setPosttitle("name" + id);
        Model.instance.addPost(post, new Model.AddPostListener() {
            @Override
            public void onComplete() {
                reloadData();
            }
        });
        id++;
    }

    void reloadData() {
        //pb.setVisibility ( View.VISIBLE );
        addBtn.setEnabled(false);
        Model.instance.getAllPost(new Model.GetAllPostListener() {
            @Override
            public void onComplete(List<Post> data) {
                postList = data;
                for (Post p : data) {
                    Log.d("TAG", "post id: " + p.getPostid());
                }
                //   pb.setVisibility ( View.INVISIBLE );
                addBtn.setEnabled(true);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView postId;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postId = itemView.findViewById(R.id.listrow_textv);
            image = itemView.findViewById(R.id.imageView);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //View view =getLayoutInflater ().inflate (R.layout.list_row,null );
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Post post = postList.get(position);
            holder.postId.setText(post.getPostid());
        }

        @Override
        public int getItemCount() {
            if(postList==null) {
                return 0;
            }
            return postList.size();
        }
    }
}
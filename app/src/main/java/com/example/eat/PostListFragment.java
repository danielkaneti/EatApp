package com.example.eat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;

import java.util.List;

public class PostListFragment extends Fragment {
    LinearLayoutManager layoutManager;
    RecyclerView postlist;
    LiveData<List<Post>> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post_list, container, false);
        postlist= view.findViewById(R.id.postlistfrag_list);
        postlist.hasFixedSize();

        layoutManager = new LinearLayoutManager(this);
        postlist.setLayoutManager(layoutManager);

        data = Model.Instance.getAllPosts();
        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView postId;
        ImageView postImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postId = itemView.findViewById(R.id.listrow_text_v);
            postImage = itemView.findViewById(R.id.row_list_image_v);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
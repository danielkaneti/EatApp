package com.example.eat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat.R;
import com.example.eat.mobel.Post;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostViewHolder>{
    public LiveData<List<Post>> data;

    private OnItemClickListener listener;
    LayoutInflater inflater;

    public PostsAdapter (LayoutInflater inflater) {
        this.inflater=inflater;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener ( OnItemClickListener listener ) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
        View view = inflater.inflate ( R.layout.list_row , null );
        PostViewHolder holder = new PostViewHolder ( view );
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder ( @NonNull PostViewHolder holder , int position ) {
        Post post = data.getValue ( ).get ( position );
        holder.bindData ( post , position );
    }

    @Override
    public int getItemCount ( ) {
            return 0;
        //data.getValue().size();
    }


}



package com.example.eat.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat.R;
import com.example.eat.mobel.Post;

public class PostViewHolder extends RecyclerView.ViewHolder{
        public PostsAdapter.OnItemClickListener listener;
        TextView postId;
        ImageView postImage;
        int position;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postId = itemView.findViewById(R.id.listrow_text_v);
            postImage = itemView.findViewById(R.id.row_list_image_v);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (listener!= null)
                        position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                        listener.onItemClick(position);
                }
            });
        }

        public void bindData(Post post, int position) {
            postId.setText(post.postid);
            this.position = position;
        }
}


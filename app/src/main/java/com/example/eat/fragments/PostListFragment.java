package com.example.eat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eat.R;
import com.example.eat.mobel.Post;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class PostListFragment extends Fragment {
    RecyclerView list;
    List<Post> postList = new LinkedList<>();
    //ProgressBar pb;
    Button addBtn;
    MyAdapter adapter;

    PostListViewModel viewModel;

    public interface Delegate{
        void onItemSelected(Post post);
    }

    Delegate parent;

    public PostListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Delegate){
            parent = (Delegate) getActivity();
        }
        else {
            throw new RuntimeException(context.toString() + " must implement Delegate");
        }

        viewModel = new ViewModelProvider (this).get(PostListViewModel.class);
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

        addBtn = view.findViewById(R.id.postlist_frag_addbtn);

        adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnClickListener ( new OnItemClickListener ( ) {
            @Override
            public void onClick ( int position ) {
                Post post = postList.get ( position );
                parent.onItemSelected ( post );
            }
        } );

        //reloadData();
        return view;
    }


//    void reloadData() {
//        //pb.setVisibility ( View.VISIBLE );
//        long lastUpdated = EatAppApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("PostsLastUpdateDate",0);
//        addBtn.setEnabled(false);
//        Model.instance.getAllPost(new Model.GetAllPostListener() {
//            @Override
//            public void onComplete(List<Post> data) {
//                postList = data;
//                for (Post p : data) {
//                    Log.d("TAG", "post id: " + p.getPostid());
//                }
//                //   pb.setVisibility ( View.INVISIBLE );
//                addBtn.setEnabled(true);
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        TextView userName;
        ImageView postimage;
        ProgressBar pb;
        Post post;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.row_post_title_text_view);
            postimage = itemView.findViewById(R.id.row_post_image_view);
            userName = itemView.findViewById ( R.id.row_username_text_view );
            pb = itemView.findViewById ( R.id.row_post_progress_bar );
        }

        public void bind(Post postToBind){
            postTitle.setText(postToBind.posttitle);
            userName.setText(postToBind.username);
            post = postToBind;
            if (postToBind.postImgUrl != null ){
                Picasso.get().load(postToBind.postImgUrl).noPlaceholder().into(postimage);
            }
        }
    }

    interface OnItemClickListener {
        void onClick(int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private OnItemClickListener listener;

        void setOnClickListener( OnItemClickListener listener){
            this.listener = listener;
        }

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
            holder.bind ( post );

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
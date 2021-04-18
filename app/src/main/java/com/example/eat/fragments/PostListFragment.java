package com.example.eat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import com.example.eat.R;
import com.example.eat.mobel.Model;
import com.example.eat.mobel.Post;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class PostListFragment extends Fragment {
    RecyclerView list;
    List<Post> data = new LinkedList<>();
    PostListViewModel viewModel;
    MyAdapter adapter;
    LiveData<List<Post>> liveData;

    public PostListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel=new ViewModelProvider(this).get(PostListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        RecyclerView list = view.findViewById(R.id.postlist_recycler_v);
        list.hasFixedSize();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);


        adapter = new MyAdapter();
        list.setAdapter(adapter);
        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Post post = data.get(position);

               PostListFragmentDirections.ActionPostListFragment2ToPostDetails action = PostListFragmentDirections.actionPostListFragment2ToPostDetails(post);
                Navigation.findNavController(view).navigate(action);
            }
        });
        liveData = viewModel.getData();
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                List<Post> reversedData = reverseData(posts);
                data = reversedData;
                adapter.notifyDataSetChanged();
            }
        });
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

        viewModel.refresh(new Model.CompListener() {
            @Override
            public void onComplete() {

            }
        });

        //reloadData();
        return view;
    }

    private List<Post> reverseData(List<Post> posts) {
        List<Post> reversedData = new LinkedList<>();
        for (Post post: posts) {
            reversedData.add(0, post);
        }
        return reversedData;
    }



//    void reloadData() {
//        //pb.setVisibility ( View.VISIBLE );
//
//        Model.instance.getAllPost(new Model.GetAllPostListener() {
//            @Override
//            public void onComplete(List<Post> data) {
//                //postList = data;
//                for (Post p : data) {
//                    Log.d("TAG", "post id: " + p.getPostid());
//                }
//                //   pb.setVisibility ( View.INVISIBLE );
//
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle;
        TextView userName;
        ImageView postimage;
 //       CircleImageView profileImage;
        ProgressBar pb;
        Post post;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.row_post_title_text_view);
            postimage = itemView.findViewById(R.id.row_post_image_view);
            userName = itemView.findViewById ( R.id.row_username_text_view );
 //           profileImage = itemView.findViewById ( R.id.list_row_profile_image_view );
            pb = itemView.findViewById ( R.id.row_post_progress_bar );

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onClick(position);
                    }
                }
            });

        }
        public void bind(Post postToBind){
            postTitle.setText(postToBind.posttitle);
            userName.setText(postToBind.username);
            post = postToBind;
            if (postToBind.postImgUrl != null && postToBind.userProfileImageUrl!=null){
                Picasso.get().load(postToBind.postImgUrl).noPlaceholder().into(postimage);
//                Picasso.get ().load ( postToBind.userProfileImageUrl ).noPlaceholder ().into ( profileImage );
            }
        }
    }
    interface OnItemClickListener {
        void onClick(int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private OnItemClickListener listener;

        void setOnClickListener(OnItemClickListener listener){ this.listener=listener; }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //View view =getLayoutInflater ().inflate (R.layout.list_row,null );
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_row, parent, false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Post post = data.get(position);
            holder.bind ( post );
        }

        @Override
        public int getItemCount() {
            if(data==null) {
                return 0;
            }
            return data.size();
        }
    }
}
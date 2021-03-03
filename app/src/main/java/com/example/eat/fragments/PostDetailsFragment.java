package com.example.eat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.eat.R;

public class PostDetailsFragment extends Fragment {


    public PostDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post_details, container, false);
        Button backBtn = view.findViewById(R.id.postdetailsfrag_back_btn);
        backBtn.setOnClickListener ( Navigation.createNavigateOnClickListener ( R.id.action_postDetails_to_postList ) );
        return view;
    }
}
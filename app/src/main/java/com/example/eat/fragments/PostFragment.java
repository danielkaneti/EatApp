package com.example.eat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.eat.R;

public class PostFragment extends Fragment {

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post, container, false);
        Button postBtn = view.findViewById ( R.id.postlistfrag_AddPostBtn );
        postBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                //save the student

                //pop back to post list
                Navigation.findNavController(v).navigate (R.id.action_postFragment_pop );
            }
        } );

        return view;
    }
}
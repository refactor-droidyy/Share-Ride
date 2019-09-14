package com.example.needhelp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.needhelp.R.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class Request extends Fragment {
    private DatabaseReference reference;
    private FirebaseUser user;
    FirebaseAuth auth;

    List<String> idList;
    RecyclerView recyclerView;

    public Request() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(layout.fragment_request, container, false);

        return v;
    }


}

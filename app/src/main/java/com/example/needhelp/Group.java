package com.example.needhelp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class Group extends Fragment {

    private DatabaseReference reference;
    FirebaseUser user;
    private ListView listview;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list = new ArrayList<>();
    private View view;


    public Group() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_group, container, false);
        FloatingActionButton fab = view.findViewById(R.id.floating);
//
//
//        reference = FirebaseDatabase.getInstance().getReference("Groups");
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
//                builder.setTitle("Enter Group Name");
//                final EditText groupnamefield = new EditText(getContext());
//                groupnamefield.setHint("e.g.Group Name ");
//                builder.setView(groupnamefield);
//
//                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String groupname = groupnamefield.getText().toString();
//                        if (TextUtils.isEmpty(groupname)) {
//                            Toast.makeText(getContext(), "Please Enter A Group Name", Toast.LENGTH_SHORT).show();
//
//                        } else {
//
//                            createGroups(groupname);
//
//                        }
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.show();
//
//
//            }
//
//            private void createGroups(final String groupname) {
//
//                reference.child(groupname).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        if (task.isSuccessful()) {
//
//                            Toast.makeText(getContext(), "Group " + groupname + " Created Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Set<String> set = new HashSet<>();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    set.add((snapshot).getKey());
//                }
//                list.clear();
//                list.addAll(set);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        return view;

    }


}

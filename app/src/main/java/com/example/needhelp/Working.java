package com.example.needhelp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Working extends AppCompatActivity {
    FloatingActionButton first, second, third, main;
    LinearLayout linearLayout;
    TextView firstt, secondd, thirdd;
    private FirebaseUser firebaseUser;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Boolean isMenuopen = false;
    private DatabaseReference reference;
    private List<Upload> mUploads;
    private String message;

    OvershootInterpolator interpolator = new OvershootInterpolator();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);

        message = getIntent().getStringExtra("namee");

        Toolbar toolbar = findViewById(R.id.toolbar_work);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).dispatchMenuVisibilityChanged(true);

        initialfabMenu();

//        first.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                closeMenu();
//            }
//        });

//        second.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Working.this, HelpCall.class);
//                intent.putExtra("nameee", message);
//                startActivity(intent);
//            }
//        });

//        third.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Working.this, Converstion.class));
//            }
//        });
//
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Working.this, HelpCall.class);
                intent.putExtra("nameee", message);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.Recycle);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mUploads = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("item_details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot mdataSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = mdataSnapshot.getValue(Upload.class);
                    assert upload != null;
                    if (!firebaseUser.getUid().equals(upload.getId())) {
                        mUploads.add(upload);
                    }

                    adapter = new RecyclerViewAdapter(getApplicationContext(), mUploads);

                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.uploadss:
                startActivity(new Intent(Working.this, Myuploads.class));
                finish();
                break;

            case R.id.profile:
                startActivity(new Intent(Working.this, Profile.class));
                finish();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Working.this, MainActivity.class));
                finish();
                break;
            case R.id.about:
                startActivity(new Intent(Working.this, About.class));
                finish();
                break;

        }
        return true;
    }

    private void initialfabMenu() {

//        first = findViewById(R.id.back);
//        second = findViewById(R.id.upload);
//        third = findViewById(R.id.chat);
        main = findViewById(R.id.main);
//        linearLayout = findViewById(R.id.linearcolor);

//        firstt = findViewById(R.id.txt_back);
//        secondd = findViewById(R.id.txt_upload);
//        thirdd = findViewById(R.id.txt_chat);

//        first.setAlpha(0f);
//        second.setAlpha(0f);
//        third.setAlpha(0f);
//
//        first.setTranslationY(200f);
//        second.setTranslationY(200f);
//        third.setTranslationY(200f);

    }


    @SuppressLint({"RestrictedApi", "ResourceAsColor"})
    private void openMenu() {
        isMenuopen = !isMenuopen;

        firstt.setVisibility(View.VISIBLE);
        secondd.setVisibility(View.VISIBLE);
        thirdd.setVisibility(View.VISIBLE);
        linearLayout.setAlpha(1);

        main.animate().setInterpolator(interpolator).rotation(45f).setDuration(600).start();
        first.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(600).start();
        second.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(600).start();
        third.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(600).start();

    }

    @SuppressLint({"WrongConstant", "RestrictedApi", "ResourceAsColor"})
    private void closeMenu() {
        isMenuopen = !isMenuopen;

        firstt.setVisibility(View.INVISIBLE);
        secondd.setVisibility(View.INVISIBLE);
        thirdd.setVisibility(View.INVISIBLE);
        linearLayout.setAlpha(0);
        main.animate().setInterpolator(interpolator).rotation(0f).setDuration(600).start();
        first.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(600).start();
        second.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(600).start();
        third.animate().translationY(0f).alpha(0f).setInterpolator(interpolator).setDuration(600).start();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

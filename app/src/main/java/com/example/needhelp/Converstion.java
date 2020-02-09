package com.example.needhelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

public class Converstion extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private Toolbar tolbar;
    private ImageView close;
    private TabAccessAdapter adapter;
    private FirebaseUser user;
    private TextView txt;
    private DatabaseReference reference;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converstion);
        //tablayout = findViewById(R.id.tablayout);
        close = findViewById(R.id.close);

//        TabLayout tablayout  = findViewById(R.id.tablayout);
//        ViewPager viewPagerr = findViewById(R.id.viewpager);
//        adapter = new TabAccessAdapter(getSupportFragmentManager());
//        viewPagerr.setAdapter(adapter);
//        tablayout.setupWithViewPager(viewPagerr);

        ChatFragment f1= new ChatFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, f1);
        fragmentTransaction.commit();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Converstion.this,Working.class));
                finish();
            }
        });

    }


}

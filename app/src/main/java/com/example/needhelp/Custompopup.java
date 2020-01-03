package com.example.needhelp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class Custompopup extends Activity {
    private ImageView profile;
    public TextView text;
    public TextView IID;
    private ImageView close;
    private Button chatting;
    private FirebaseUser user;
    private String idd;
    private Button request;
    int Current_state = 0;
    FirebaseUser curretUser;
    private  DatabaseReference reference;
    String Currentstate;
    String title;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custompopup);

        chatting = findViewById(R.id.chating);
        request = findViewById(R.id.request);
        user = FirebaseAuth.getInstance().getCurrentUser();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -10;
        getWindow().setAttributes(params);


        close = findViewById(R.id.close);
        text = findViewById(R.id.username_item);
//        Currentstate = "not_friend";
        final String name = Objects.requireNonNull(getIntent().getExtras()).getString("username");
        text.setText(name);

        curretUser = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("Friend_Request");
        idd = getIntent().getExtras().getString("Id");

        assert idd != null;

//        // checking if the request is performed or not
//        reference.child(idd).addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Regain regain = snapshot.getValue(Regain.class);
//                    assert regain != null;
//                    String type = regain.getRequesttype();
//                    if(type.equals("recieved")){
//                        request.setEnabled(true);
//                        request.setText("Cancel Request");
//                        Current_state  = 1;
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        chatting.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {
                if (!user.getUid().equals(idd)) {
                    Intent intt = new Intent(Custompopup.this, Message.class);
                    intt.putExtra("ID", idd);
                    startActivity(intt);
                    finish();
                }
            }
        });


//        request.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                sendRequest();
//            }
//        });



    }

    @SuppressLint("SetTextI18n")
    private void sendRequest() {
        request.setEnabled(false);

        if(Current_state == 0){

            assert idd != null;

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("requesttype","sent");
            hashMap.put("id",idd);
            hashMap.put("friend","true");
            reference.child(curretUser.getUid()).child(idd).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("requesttype","recieved");
                    hashMap.put("id",curretUser.getUid());
                    hashMap.put("friend","false");
                    reference.child(idd).child(curretUser.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                                request.setEnabled(true);
                                request.setText("Cancel Request");
                                Current_state  = 1;

                        }
                    });


                }
            });
        }

        if(Current_state == 1){
            assert idd != null;
            reference.child(idd).child(curretUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    reference.child(curretUser.getUid()).child(idd).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            request.setEnabled(true);
                            Current_state = 0;
                            request.setText("Request For Share Ride");
                        }
                    });

                }
            });

        }
    }
}

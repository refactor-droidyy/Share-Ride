package com.example.needhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class HelpCall extends AppCompatActivity {
    private EditText from,to,description;
    private DatabaseReference mDatabaseReference,uploadd;
    ImageView close;
    private Button upload;
    private FirebaseAuth auth;
    private  String username_data,datetime,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_call);

        from = findViewById(R.id.from_upload);
        to = findViewById(R.id.to_upload);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        close = findViewById(R.id.close);

        auth = FirebaseAuth.getInstance();
        id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpCall.this,Working.class));
                finish();
            }
        });


        username_data = getIntent().getStringExtra("nameee");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("item_details");
        uploadd = FirebaseDatabase.getInstance().getReference().child("USERS");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fromm = from.getText().toString();
                final String too = to.getText().toString();
                final String description = HelpCall.this.description.getText().toString();

                Calendar c = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM,yy hh:mm aa");
                datetime = dateformat.format(c.getTime());


                if(TextUtils.isEmpty(fromm) || TextUtils.isEmpty(too) || TextUtils.isEmpty(description)){
                    Toast.makeText(getApplicationContext(), "All Fields Are Necessary", Toast.LENGTH_SHORT).show();
                }else
                {
                    uploadd.child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){
                                String Username = Objects.requireNonNull(dataSnapshot.child("username_item").getValue()).toString();
                                String email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                                String imageURL = Objects.requireNonNull(dataSnapshot.child("imageURL").getValue()).toString();
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("from",fromm);
                                hashMap.put("to",too);
                                hashMap.put("description",description);
                                hashMap.put("username_item",Username);
                                hashMap.put("time",datetime);
                                hashMap.put("email",email);
                                hashMap.put("id",id);
                                hashMap.put("imageUrl",imageURL);
                                mDatabaseReference.child(id+datetime).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(HelpCall.this, Working.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            }
        });
    }
}

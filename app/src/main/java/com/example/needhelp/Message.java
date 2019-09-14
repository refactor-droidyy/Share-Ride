package com.example.needhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Message extends AppCompatActivity {

    private ImageView profile_image;
    private TextView username;
    private ImageView close;
    private TextView  statuss;
   // private ImageView  attachement;

    FirebaseUser fireBse;
    DatabaseReference reference;

    private EditText message;
    private ImageButton send;

    MessageAdapter messageAdapter;
    List<Chat> mchats;
    RecyclerView recyclerView;
    String userid;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        profile_image = findViewById(R.id.img_toolbar_mess);
        username = findViewById(R.id.username_toolbar_mess);
        message = findViewById(R.id.txt_message);
        send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.recycler_view_mess);
        statuss = findViewById(R.id.online);

        close = findViewById(R.id.close);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager =  new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intent = getIntent();

        userid = intent.getStringExtra("ID");

        fireBse = FirebaseAuth.getInstance().getCurrentUser();
        assert userid != null;
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = message.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fireBse.getUid(),userid,msg);
                }else{
                    Toast.makeText(Message.this,"Please Type A Message",Toast.LENGTH_LONG).show();
                }
                message.setText("");
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                User user = dataSnapshot.getValue(User.class);
                assert user != null;

                    username.setText(user.getUsername_item());

                readMessages(fireBse.getUid(),userid,user.getImageURL());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readMessages(final String myid, final String userid, final String imageurl) {

        mchats = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mchats.clear();

                for(DataSnapshot snapshots : dataSnapshot.getChildren()){

                    Chat chat = snapshots.getValue(Chat.class);
                    assert chat != null;
                    if(chat.getReciever().equals(myid) && chat.getSender().equals(userid) || chat.getReciever().equals(userid) && chat.getSender().equals(myid)) {
                        mchats.add(chat);
                    }

                    messageAdapter = new MessageAdapter(Message.this,mchats,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void sendMessage(String sender, String reciever, String messag) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("sender",sender);
        hashMap.put("reciever",reciever);
        hashMap.put("message",messag);

        reference.child("Chats").push().setValue(hashMap);

    }
}

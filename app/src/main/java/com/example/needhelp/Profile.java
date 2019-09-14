package com.example.needhelp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    public TextView name;
    public TextView email;
    public TextView phone;
    public ImageView close, photo;
    private ImageView addinfo;
    String phonee, datetime;
    private StorageReference postref;
    DatabaseReference reference;
    private Uri mImageUri;
    private ProgressDialog dialog;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.userrname);
        email = findViewById(R.id.emailid);
        phone = findViewById(R.id.phone_number);

        photo = findViewById(R.id.select_post_image);

        close = findViewById(R.id.close);

        addinfo = findViewById(R.id.upload_new);
        dialog = new ProgressDialog(this);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // openFileChooser();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Working.class));
                finish();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        postref = FirebaseStorage.getInstance().getReference().child("Profile Images");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username = Objects.requireNonNull(dataSnapshot.child("username_item").getValue()).toString();
                String emaill = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                phonee = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();

                name.setText(username);
                email.setText(emaill);
                phone.setText(phonee);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

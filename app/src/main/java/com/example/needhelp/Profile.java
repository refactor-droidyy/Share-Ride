package com.example.needhelp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.*;

public class Profile extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    public TextView name;
    public TextView email;
    public TextView phone;
    public ImageView close, photo;
    private TextView addinfo;
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
                openFileChooser();
            }
        });
        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimagetofirebasedatabase();
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

    private void uploadimagetofirebasedatabase() {
        if(mImageUri == null){
            makeText(this, "Please Select A Photo From Gallery", LENGTH_SHORT).show();
        }else {
            postref = FirebaseStorage.getInstance().getReference().child("Profile_Images").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
            final String s = mImageUri.toString();
            postref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    Toast.makeText(Profile.this, "Please check your email for verification .....", Toast.LENGTH_SHORT).show();
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    database.child("imageURL").setValue(mImageUri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Profile.this,"Imageurl succesfully save",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            mImageUri  = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            photo.setImageBitmap(bitmap);
        }
    }
}

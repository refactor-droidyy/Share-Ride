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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.squareup.picasso.Picasso;

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
        postref = FirebaseStorage.getInstance().getReference("uploads");
        reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload In Progress", LENGTH_LONG).show();
                } else {
                    openFileChooser();
                }

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

      updateinfo();
    }

    private void updateinfo() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username = Objects.requireNonNull(dataSnapshot.child("username_item").getValue()).toString();
                String emaill = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                phonee = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();
                String url = Objects.requireNonNull(dataSnapshot.child("imageURL").getValue()).toString();
                Toast.makeText(getApplicationContext(),url,LENGTH_LONG).show();
                String s = "gs://needhelp-29d3b.appspot.com/uploads/1573743240371.jpg";
                Picasso.get()
                        .load(s)
                        .resize(100,100)
                        .into(photo);
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
        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading");
        if(mImageUri != null){
            final StorageReference fileref = postref.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploadTask = fileref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = String.valueOf(mImageUri);
                            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            HashMap<String,Object> hashMap=new HashMap<>();
                            hashMap.put("imageURL",downloadUrl);
                            dbref.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_LONG).show();
                                                                  }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Upload UnSuccessful", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"Please Select A Image",LENGTH_LONG).show();
            pd.dismiss();
        }

    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
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

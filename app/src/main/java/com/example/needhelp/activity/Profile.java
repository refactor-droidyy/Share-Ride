package com.example.needhelp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.needhelp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Objects;

import static android.widget.Toast.*;

public class Profile extends AppCompatActivity {

    private static final int IMAGE_REQUEST = 1;
    private static final int REQUEST_WRITE_PERMISSION = 12;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView organisation;
    private LinearLayout call;
    public ImageView close, photo;
    private TextView addinfo;
    String phonee, datetime;
    private StorageReference postref;
    DatabaseReference reference;
    private Uri mImageUri;
    private ProgressDialog dialog;
    private StorageTask mUploadTask;
    String url;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.userrname);
        email = findViewById(R.id.emailid);
        phone = findViewById(R.id.phone_number);
        photo = findViewById(R.id.select_post_image);
        close = findViewById(R.id.close);
        call = findViewById(R.id.make_call);

        organisation = findViewById(R.id.org_display);
        addinfo = findViewById(R.id.upload_new);
        dialog = new ProgressDialog(this);
        postref = FirebaseStorage.getInstance().getReference("uploads");
        reference = FirebaseDatabase.getInstance().getReference("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        updateinfo();

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // makeCallIntent();
            }
        });
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
                Intent intent = new Intent(Profile.this, EditImage.class);
                intent.putExtra("url", url);
                startActivity(intent);
                //uploadimagetofirebasedatabase();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Working.class));
                finish();
            }
        });

    }

    private void updateinfo() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username = Objects.requireNonNull(dataSnapshot.child("username_item").getValue()).toString();
                String emaill = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                phonee = Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString();
                url = Objects.requireNonNull(dataSnapshot.child("imageURL").getValue()).toString();
                String orgg = Objects.requireNonNull(dataSnapshot.child("organisation").getValue()).toString();
                Picasso.get()
                        .load(url)
                        .resize(100, 100)
                        .into(photo);
                name.setText(username);
                email.setText(emaill);
                phone.setText(phonee);
                organisation.setText(orgg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadimagetofirebasedatabase() {
        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading");
        if (mImageUri != null) {
            final StorageReference fileref = postref.child(fileName);
            mUploadTask = fileref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = String.valueOf(uri);
                            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("imageURL", downloadUrl);
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

        } else {
            Toast.makeText(getApplicationContext(), "Please Select A Image", LENGTH_LONG).show();
            pd.dismiss();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            CropImage.startPickImageActivity(Profile.this);
        }
    }

    private void openFileChooser() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            CropImage.startPickImageActivity(Profile.this);
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMultiTouchEnabled(true)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = (result.getUri());
                if (mImageUri.getScheme().equals("file")) {
                    fileName = mImageUri.getLastPathSegment();
                }
                photo.setImageURI(result.getUri());
            }
        }
    }
}

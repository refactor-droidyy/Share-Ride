package com.example.needhelp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class HelpCall extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText from, to, description;
    private DatabaseReference mDatabaseReference, uploadd;
    ImageView close;
    private Button upload;
    private FirebaseAuth auth;
    private String type_ride ;
    private String username_data, datetime, id;
    TextView date_picker, txact_time;
    String value, intent_time = null;
    String hr, min, am, arrival_time, din, mahina;
    private Button ola,uber,inDrive,train,plain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_call);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        } else {
            value = getIntent().getStringExtra("value");
            intent_time = getIntent().getStringExtra("time");
        }

        from = findViewById(R.id.from_upload);
        to = findViewById(R.id.to_upload);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        close = findViewById(R.id.close);

        ola = findViewById(R.id.olaBtn);
        uber = findViewById(R.id.uberBtn);
        inDrive = findViewById(R.id.indriverBtn);
        train = findViewById(R.id.trainBtn);
        plain = findViewById(R.id.flightBtn);

        ola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_ride = "ola";
                Toast.makeText(getApplicationContext(),"Ola Selected",Toast.LENGTH_SHORT).show();
            }
        });
        uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_ride = "uber";
                Toast.makeText(getApplicationContext(),"Uber Selected",Toast.LENGTH_SHORT).show();
            }
        });
        inDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_ride = "inDrive";
                Toast.makeText(getApplicationContext(),"inDrive Selected",Toast.LENGTH_SHORT).show();
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_ride = "train";
                Toast.makeText(getApplicationContext(),"Train Selected",Toast.LENGTH_SHORT).show();
            }
        });
        plain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_ride = "flight";
                Toast.makeText(getApplicationContext(),"Flight Selected",Toast.LENGTH_SHORT).show();
            }
        });

        auth = FirebaseAuth.getInstance();
        id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpCall.this, Working.class));
                finish();
            }
        });

        String[] arraySpinner = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        };

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
                // @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM,yy hh:mm aa");
                final long time = System.currentTimeMillis();
                // datetime = dateformat.format(c.getTime());


                if (TextUtils.isEmpty(fromm) || TextUtils.isEmpty(too) || TextUtils.isEmpty(description)) {
                    Toast.makeText(getApplicationContext(), "All Fields Are Necessary", Toast.LENGTH_SHORT).show();
                } else {
                    uploadd.child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {
                                String Username = Objects.requireNonNull(dataSnapshot.child("username_item").getValue()).toString();
                                String email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                                String imageURL = Objects.requireNonNull(dataSnapshot.child("imageURL").getValue()).toString();
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("from", fromm);
                                hashMap.put("to", too);
                                hashMap.put("description", description);
                                hashMap.put("username_item", Username);
                                hashMap.put("ride_type",type_ride);
                                if (intent_time == null) {
                                    hashMap.put("time", String.valueOf(time));
                                } else {
                                    hashMap.put("time", intent_time);
                                }
                                hashMap.put("email", email);
                                hashMap.put("id", id);
                                hashMap.put("imageUrl", imageURL);
                                if (value == null) {
                                    mDatabaseReference.child(id + time).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(HelpCall.this, Working.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                } else {
                                    mDatabaseReference.child(value).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(HelpCall.this, "Edited Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(HelpCall.this, Myuploads.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }

                                        }
                                    });
                                }

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

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i2);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DATE, i);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        date_picker.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        txact_time.setText(i + "hr" + "Minute : " + i1);
    }
}

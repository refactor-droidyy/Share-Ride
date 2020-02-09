package com.example.needhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class HelpCall extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {
    private EditText from, to, description;
    private DatabaseReference mDatabaseReference, uploadd;
    ImageView close;
    private Button upload;
    private FirebaseAuth auth;
    private String username_data, datetime, id;
    TextView date_picker,txact_time;
    String value, intent_time = null;
    String hr,min,am,arrival_time,din,mahina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_call);

        value = getIntent().getStringExtra("value");
        intent_time = getIntent().getStringExtra("time");

        from = findViewById(R.id.from_upload);
        to = findViewById(R.id.to_upload);
        description = findViewById(R.id.description);
        upload = findViewById(R.id.upload);
        close = findViewById(R.id.close);
        //date_picker = findViewById(R.id.exact_date);

//        txact_time = findViewById(R.id.exact_time);
//        txact_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(),"time picker");
//            }
//        });

//        date_picker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment datePicker = new DataPickerFragment();
//                datePicker.show(getSupportFragmentManager(),"date picker");
//            }
//        });

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
//       // Spinner s = (Spinner) findViewById(R.id.hrs);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, arraySpinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s.setAdapter(adapter);
//
//        hr = s.getSelectedItem().toString();
//
//        String[] arraySpinner2 = new String[]{
//                "10", "20", "30", "40", "50"
//        };
//        Spinner s1 = (Spinner) findViewById(R.id.minu);
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, arraySpinner2);
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s1.setAdapter(adapter2);
//
//        min = s1.getSelectedItem().toString();
//
//        String[] arraySpinner3 = new String[]{
//                "AM", "PM"
//        };
//        Spinner s2 = (Spinner) findViewById(R.id.pm);
//        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, arraySpinner3);
//        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s2.setAdapter(adapter3);
//
//        am = s2.getSelectedItem().toString();
//
//        arrival_time = hr + ":" + min + " " + am;

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
                                                Intent intent = new Intent( HelpCall.this, Working.class);
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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,i2);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DATE,i);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        date_picker.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        txact_time.setText(i + "hr"+"Minute : "+i1);
    }
}

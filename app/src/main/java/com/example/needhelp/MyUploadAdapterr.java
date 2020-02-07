package com.example.needhelp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyUploadAdapterr extends RecyclerView.Adapter<MyUploadAdapterr.ViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;

    MyUploadAdapterr(Context mContext, List<Upload> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.myuploads_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Upload upload = mUploads.get(position);
        holder.ffrom.setText(upload.getFrom());
        holder.tto.setText(upload.getTo());
        holder.description.setText(upload.getDescription());
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM,yyy hh:mm aa");
        final long time = Long.parseLong(upload.getTime());
        holder.time.setText(dateformat.format(time));


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext.getApplicationContext());
                alertDialog.setTitle(" Delete ");
                alertDialog.setMessage("Are you sure want to delete ?");
                alertDialog.setView(view);
                alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("item_details").orderByChild("time").equalTo(upload.getTime());
                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
                                    Toast.makeText(mContext, "Delete Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext, databaseError.toException().toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

            }
        });

        holder.edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("item_details").orderByChild("time").equalTo(upload.getTime());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Intent intent = new Intent(view.getContext(), HelpCall.class);
                            intent.putExtra("value", snapshot.getRef().getKey());
                            intent.putExtra("time", upload.getTime());
                            mContext.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ffrom, tto, description, username_item, time, email;
        RelativeLayout relativeLayout;
        CircleImageView delete, edit_data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ffrom = itemView.findViewById(R.id.from);
            tto = itemView.findViewById(R.id.to);
            description = itemView.findViewById(R.id.description);
            // username_item = itemView.findViewById(R.id.username_item);
            time = itemView.findViewById(R.id.time);
            relativeLayout = itemView.findViewById(R.id.relative);
            // email = itemView.findViewById(R.id.emailwa);
            edit_data = itemView.findViewById(R.id.edit_data);
            delete = itemView.findViewById(R.id.delete_data);
        }
    }
}

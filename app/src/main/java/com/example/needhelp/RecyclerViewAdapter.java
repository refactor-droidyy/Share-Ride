package com.example.needhelp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<Upload> mUploads;

    RecyclerViewAdapter(Context mContext, List<Upload> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        final  ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Upload upload = mUploads.get(position);
        holder.ffrom.setText(upload.getFrom());
        holder.tto.setText(upload.getTo());
        holder.description.setText(upload.getDescription());
        holder.username.setText(upload.getUsername());
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateformat = new SimpleDateFormat("dd MMM,yyyy hh:mm aa");
        final long time = Long.parseLong(upload.getTime());
        holder.time.setText(dateformat.format(time));
        holder.email.setText(upload.getEmail());
        Picasso.get()
                .load(upload.getImageUrl())
                .resize(100,100)
                .into(holder.imageUrl);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext.getApplicationContext(),Custompopup.class);
                intent.putExtra("Id",upload.getId());
                intent.putExtra("username",upload.getUsername());
                intent.putExtra("imageUrl",upload.getImageUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ffrom, tto, description, username,time,email;
        CircleImageView imageUrl;
        RelativeLayout relativeLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ffrom = itemView.findViewById(R.id.from);
            imageUrl = itemView.findViewById(R.id.profile_list);
            tto = itemView.findViewById(R.id.to);
            description = itemView.findViewById(R.id.description);
            username= itemView.findViewById(R.id.username);
            time = itemView.findViewById(R.id.time);
            relativeLayout = itemView.findViewById(R.id.relative);
            email = itemView.findViewById(R.id.emailwa);
        }
    }
}

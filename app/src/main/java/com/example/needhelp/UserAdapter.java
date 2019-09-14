package com.example.needhelp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> lst;

    public UserAdapter(Context mContext, List<User> lst) {
        this.mContext = mContext;
        this.lst = lst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = lst.get(position);
        holder.username.setText(user.getUsername_item());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Message.class);
                intent.putExtra("ID",user.getId());
                //Toast.makeText(mContext.getApplicationContext(),user.getUsername_item() + " " + user.getId(),Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_item);
            linearLayout = itemView.findViewById(R.id.linearlayout);
        }
    }
}

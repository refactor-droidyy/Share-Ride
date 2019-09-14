package com.example.needhelp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.Viewholder> {


    private Context context;
    private List<User> users;

    public RequestAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new RequestAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        final User user = users.get(position);
        holder.name.setText(user.getUsername_item());

//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext,Message.class);
//                intent.putExtra("ID",user.getId());
//                //Toast.makeText(mContext.getApplicationContext(),user.getUsername_item() + " " + user.getId(),Toast.LENGTH_SHORT).show();
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        TextView name;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.username_item);
        }
    }
}

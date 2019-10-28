package com.example.myapplication.Fragment.Menu2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Menu3.OtherUserHomePage;
import com.example.myapplication.Fragment.Menu4.ItemData;
import com.example.myapplication.R;
import com.example.myapplication.ServerUri;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.ViewHolder> {
    String iuri = new ServerUri().uri+"image/";

    private ArrayList<UserSearchItem> items;
    Context context;

    @NonNull
    @Override
    public UserSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull UserSearchAdapter.ViewHolder viewHolder, int position) {
        UserSearchItem item = items.get(position);
        Glide.with(viewHolder.itemView.getContext()).load(iuri+"ProFileImage/"+item.getUserImg()).placeholder(new ColorDrawable(Color.WHITE)).skipMemoryCache(false).centerCrop().error(R.mipmap.ic_launcher).into(viewHolder.useriv);
        viewHolder.userid.setText(item.getUserId());
    }

    public void setItems(ArrayList<UserSearchItem> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView useriv;
        TextView userid;


        public ViewHolder(View itemView){
            super(itemView);

            useriv = (CircleImageView) itemView.findViewById(R.id.userimg);
            userid = (TextView) itemView.findViewById(R.id.userid);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent it = new Intent(context, OtherUserHomePage.class);
            String userid = items.get(getAdapterPosition()).getUserId();

            it.putExtra("userid",userid);
            context.startActivity(it);
        }
    }



}

package com.example.myapplication3;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    String user_id;
    ArrayList<ChatData> itemList = new ArrayList<>();

    final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("a h:mm", Locale.getDefault());

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_chat, parent, false);
        ViewHolder viewHolder = new ViewHolder(ItemView);
        user_id = SaveSharedPreference.getNowUserName(parent.getContext());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int position) {
        ChatData chat_Data = itemList.get(position);

        if(chat_Data.getSenderName().equals(user_id)) // 내꺼
        {
//            mTxtUserName_other = (TextView) itemView.findViewById(R.id.txt_userName_other);
//            mTxtMessage_other = (TextView) itemView.findViewById(R.id.txt_message_other);
//            mTxtMessage_me = (TextView) itemView.findViewById(R.id.txt_message_me);
//            mTxtTime_other = (TextView) itemView.findViewById(R.id.txt_time_other);
//            mTxtTime_me = (TextView) itemView.findViewById(R.id.txt_time_me);

            viewHolder.mTxtUserName_other.setVisibility(View.GONE);
            viewHolder.mTxtMessage_other.setVisibility(View.GONE);
            viewHolder.mTxtTime_other.setVisibility(View.GONE);
            viewHolder.circleImageView.setVisibility(View.GONE);

            viewHolder.mTxtMessage_me.setVisibility(View.VISIBLE);
            viewHolder.mTxtTime_me.setVisibility(View.VISIBLE);

            viewHolder.mTxtMessage_me.setText(chat_Data.getMessage());
            viewHolder.mTxtTime_me.setText(mSimpleDateFormat.format(chat_Data.getTime()));


        }
        else // 남의꺼
        {
            viewHolder.mTxtMessage_me.setVisibility(View.GONE);
            viewHolder.mTxtTime_me.setVisibility(View.GONE);

            viewHolder.mTxtMessage_other.setVisibility(View.VISIBLE);
            viewHolder.mTxtUserName_other.setVisibility(View.VISIBLE);
            viewHolder.mTxtTime_other.setVisibility(View.VISIBLE);
            viewHolder.circleImageView.setVisibility(View.VISIBLE);


            viewHolder.mTxtUserName_other.setText(chat_Data.getSenderName());
            viewHolder.mTxtMessage_other.setText(chat_Data.getMessage());
            viewHolder.mTxtTime_other.setText(mSimpleDateFormat.format(chat_Data.getTime()));
            //viewHolder.circleImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void SetItems(ArrayList<ChatData> items) { this.itemList = items;}
    public void addChatData(ChatData chatData){
        this.itemList.add(chatData);
        notifyItemChanged(getItemCount());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtUserName_other;
        TextView mTxtMessage_other;
        TextView mTxtTime_other;
        CircleImageView circleImageView;

        TextView mTxtMessage_me;
        TextView mTxtTime_me;


        public ViewHolder(View itemView){
            super(itemView);

            mTxtUserName_other = (TextView) itemView.findViewById(R.id.txt_userName_other);
            mTxtMessage_other = (TextView) itemView.findViewById(R.id.txt_message_other);
            mTxtMessage_me = (TextView) itemView.findViewById(R.id.txt_message_me);
            mTxtTime_other = (TextView) itemView.findViewById(R.id.txt_time_other);
            mTxtTime_me = (TextView) itemView.findViewById(R.id.txt_time_me);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.user_img);
        }

    }

}


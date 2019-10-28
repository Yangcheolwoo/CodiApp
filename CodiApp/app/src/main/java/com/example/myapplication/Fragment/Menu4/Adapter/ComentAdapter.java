package com.example.myapplication.Fragment.Menu4.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Menu4.ComentData;
import com.example.myapplication.Entry.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ServerUri;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ComentAdapter extends RecyclerView.Adapter<ComentAdapter.ViewHolder> {

    private Activity activity;
    ArrayList<ComentData> items = new ArrayList<>();
    private MainActivity ac;
    String iuri = new ServerUri().uri+"image/";


//    public rw_nc_comment_Adapter(Activity activity){
//        this.activity = activity;
//    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    public void setItems(ArrayList<ComentData> items){
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout. coment_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ComentAdapter.ViewHolder viewHolder, final int position) {
        ComentData item =items.get(position);

        // 데이터 결합
        viewHolder.id.setText(item.getUserId());
        viewHolder.time.setText(item.getTime());
        viewHolder.comm.setText(item.getComm());
        Glide.with(viewHolder.itemView.getContext()).load(iuri+"ProFileImage/"+item.getUserProFileImage()).placeholder(new ColorDrawable(Color.WHITE)).skipMemoryCache(false).centerCrop().error(R.mipmap.ic_launcher).into(viewHolder.userimg);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView time;
        TextView comm;
        CircleImageView userimg;

        public ViewHolder(View itemView){
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.nc_comm_id);
            time = (TextView) itemView.findViewById(R.id.nc_comm_time);
            comm = (TextView) itemView.findViewById(R.id.nc_com_comm);
            userimg = (CircleImageView) itemView.findViewById(R.id.profile_image);
        }
    }


}

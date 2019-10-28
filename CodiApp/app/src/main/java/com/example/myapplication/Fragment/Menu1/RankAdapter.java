package com.example.myapplication.Fragment.Menu1;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Menu4.HttpConnection.LikeInfodb;
import com.example.myapplication.Fragment.Menu4.ShowContext;
import com.example.myapplication.R;
import com.example.myapplication.SaveSharedPreference;
import com.example.myapplication.ServerUri;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    String uri = new ServerUri().uri+"image/";

    private ArrayList<RankData> items = new ArrayList<>();
    int showcontext = 1000;
    String counter;
    String nuserid;
    Context context;
    Fragment fragment;
    public RankAdapter(Fragment context){
        fragment = context;
    }

    @NonNull
    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        nuserid = SaveSharedPreference.getNowUserName(parent.getContext());
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.ViewHolder viewHolder, final int position) {
        RankData item = items.get(position);
        Glide.with(viewHolder.itemView.getContext()).load(uri+item.getBoardDB()+"/"+item.getImgPath()).centerCrop().into(viewHolder.iv);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.userid.setText(item.getId());
        viewHolder.count.setText(item.getLikeCount());
        viewHolder.rank.setText(""+(position+1));
        LikeButtonCheck(viewHolder.like,position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setItems(ArrayList<RankData> items){
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iv;
        TextView tvTitle;
        TextView userid;
        TextView rank;
        TextView count;
        CheckBox like;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            iv = itemView.findViewById(R.id.ruserim);
            tvTitle = itemView.findViewById(R.id.rtitle);
            like = itemView.findViewById(R.id.like);
            count = itemView.findViewById(R.id.counter);
            userid = itemView.findViewById(R.id.ruserid);
            rank = itemView.findViewById(R.id.rank);

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeFlag(getAdapterPosition());
                    updatelike(count,getAdapterPosition(),counter);
                    FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.detach(fragment).attach(fragment).commit();


                }
            });
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent it = new Intent(context, ShowContext.class);
            String boardid = items.get(getAdapterPosition()).getBoardId();
            String userid = items.get(getAdapterPosition()).getId();
            String userimg = items.get(getAdapterPosition()).getImgPath();
            String username = items.get(getAdapterPosition()).getTitle();
            String usercontent = items.get(getAdapterPosition()).getContent();
            String usertime = items.get(getAdapterPosition()).getTime();
            String boardname = items.get(getAdapterPosition()).getBoardDB();

            it.putExtra("boardid",boardid);
            it.putExtra("userid",userid);
            it.putExtra("img",userimg);
            it.putExtra("name",username);
            it.putExtra("content",usercontent);
            it.putExtra("usertime",usertime);
            it.putExtra("boardname",boardname);
            context.startActivity(it);
        }
    }
    public void likeFlag(int position){
        try {
            LikeInfodb task = new LikeInfodb();
            String boardid = items.get(position).getBoardId();
            String boardname = items.get(position).getBoardDB();
            counter = task.execute(boardid, nuserid,boardname).get();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
    public void updatelike(TextView count,int position,String counter){
        //Toast.makeText(context,counter,Toast.LENGTH_LONG).show();
        items.get(position).setLikeCount(counter);
        count.setText(items.get(position).getLikeCount());

    }
    public void LikeButtonCheck(CheckBox cb,int position) {
        if(items.get(position).getToggle().equals("0") || items.get(position).getToggle().equals("")) {
            cb.setChecked(false);
            //Toast.makeText(context,"빈하트",Toast.LENGTH_SHORT).show();
            items.get(position).setToggle("1");
        }else{
            cb.setChecked(true);
            //Toast.makeText(context,"하트",Toast.LENGTH_SHORT).show();
            items.get(position).setToggle("0");

        }
    }

}

package com.example.myapplication.Fragment.Menu4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Menu3.OtherUserHomePage;
import com.example.myapplication.Fragment.Menu4.HttpConnection.EditBoarddb;
import com.example.myapplication.Fragment.Menu4.HttpConnection.LikeInfodb;
import com.example.myapplication.Fragment.Menu4.ItemData;
import com.example.myapplication.Fragment.Menu4.ShowContext;
import com.example.myapplication.R;
import com.example.myapplication.SaveSharedPreference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NeedBoardAdapter extends RecyclerView.Adapter<NeedBoardAdapter.ViewHolder>{
        String uri = "http://220.68.233.35:80/Codi/image/";
        ArrayList<ItemData> items = new ArrayList<>();
        String counter;
        String userid;
        Context context;

    @NonNull
    @Override
    public NeedBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        userid = SaveSharedPreference.getNowUserName(parent.getContext());
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NeedBoardAdapter.ViewHolder viewHolder, final int position) {
        ItemData item = items.get(position);
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(viewHolder.itemView.getContext()).load(uri+item.getBoardDB()+"/"+item.getImgPath()).placeholder(new ColorDrawable(Color.WHITE)).skipMemoryCache(false).centerCrop().into(viewHolder.iv);
        //Glide.with(viewHolder.itemView.getContext()).load(uri+"ProFileImage/"+item.getUserProFileImage()).placeholder(new ColorDrawable(Color.WHITE)).skipMemoryCache(false).centerCrop().error(R.mipmap.ic_launcher).into(viewHolder.useriv);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.count.setText(item.getLikeCount());
        viewHolder.userid.setText(item.getId());
        LikeButtonCheck(viewHolder.like,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

//    public void ItemAdd(String userid,String boardid, String title, String content, String time, String imgpath, String boardname, String likecount){
//        ItemData item= new ItemData();
//        item.boardid = boardid;
//        item.id=userid;
//        item.title =title;
//        item.content = content;
//        item.time =time;
//        item.imgpath=imgpath;
//        item.boardname = boardname;
//        item.likecount = likecount;
//
//        items.add(item);
//    }
    public void setItems(ArrayList<ItemData> items){
        this.items = items;
    }
    public void addItem(ItemData items) {
        this.items.add(items);
        notifyItemChanged(getItemCount());
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iv;
        //CircleImageView useriv;
        TextView tvTitle;
        TextView count;
        TextView userid;
        CheckBox cbDelete;
        CheckBox like;


    public ViewHolder(View itemView) {
        super(itemView);


        iv = itemView.findViewById(R.id.dailyim);
        //useriv = itemView.findViewById(R.id.userimg);
        tvTitle = itemView.findViewById(R.id.title);
        cbDelete = itemView.findViewById(R.id.delete);
        like = itemView.findViewById(R.id.like);
        count = itemView.findViewById(R.id.counter);
        userid = itemView.findViewById(R.id.userid);


//        cbDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(),"삭제",Toast.LENGTH_SHORT).show();
//                removeServer(getAdapterPosition());
//                removeAt(v,getAdapterPosition());
//
//            }
//        });
//        like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                likeFlag(getAdapterPosition());
//                updatelike(count,getAdapterPosition(),counter);
//            }
//        });
        cbDelete.setOnClickListener(this);
        like.setOnClickListener(this);
        userid.setOnClickListener(this);
        //useriv.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.delete:{
                removeServer(getAdapterPosition());
                removeAt(v,getAdapterPosition());
                break;
            }
            case R.id.like:{
                likeFlag(getAdapterPosition());
                updatelike(count,getAdapterPosition(),counter);
                break;
            }
            case R.id.userid: case R.id.userimg:{
                //Context context = v.getContext();
                Intent it = new Intent(context, OtherUserHomePage.class);
                String userid = items.get(getAdapterPosition()).getId();

                it.putExtra("userid",userid);
                context.startActivity(it);
                break;
            }
            default:{
                //Context context = v.getContext();
                Intent it = new Intent(context, ShowContext.class);

                String boardid = items.get(getAdapterPosition()).getBoardId();
                String userid = items.get(getAdapterPosition()).getId();
                String img = items.get(getAdapterPosition()).getImgPath();
                String userimg = items.get(getAdapterPosition()).getUserProFileImage();
                String username = items.get(getAdapterPosition()).getTitle();
                String usercontent = items.get(getAdapterPosition()).getContent();
                String usertime = items.get(getAdapterPosition()).getTime();
                String boardname = items.get(getAdapterPosition()).getBoardDB();
                //Toast.makeText(context,"보드이름 " +items.get(position).getBoardDB()+"이미지 이름 "+items.get(position).getImgPath(),Toast.LENGTH_LONG).show();

                it.putExtra("boardid",boardid);
                it.putExtra("userid",userid);
                it.putExtra("img",img);
                it.putExtra("userimg",userimg);
                it.putExtra("name",username);
                it.putExtra("content",usercontent);
                it.putExtra("usertime",usertime);
                it.putExtra("boardname",boardname);
                context.startActivity(it);
                break;
            }
        }

    }
}
    public void removeAt(View v,int position){
        //Toast.makeText(v.getContext(),"삭제",Toast.LENGTH_SHORT).show();
        items.remove(position);
        notifyItemRemoved(position);
        //notifyItemRangeChanged(position,items.size());
    }
    public void removeServer(int position){
        EditBoarddb task = new EditBoarddb();

        String userid = items.get(position).getId();
        String userimg = items.get(position).getImgPath();
        String boardname = items.get(position).getBoardDB();
        String boardid = items.get(position).getBoardId();
        task.execute(boardname,userid,userimg,boardid);
    }
    public void likeFlag(int position){
        try {
            LikeInfodb task = new LikeInfodb();
            String boardid = items.get(position).getBoardId();
            String boardname = items.get(position).getBoardDB();
            counter = task.execute(boardid, userid,boardname).get();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
    public void updatelike(TextView count,int position,String counter){
        //Toast.makeText(context,counter,Toast.LENGTH_LONG).show();
        //items.get(position).likecount = counter;
        items.get(position).setLikeCount(counter);
        count.setText(items.get(position).getLikeCount());
    }
    public void LikeButtonCheck(CheckBox cb, int position) {
        try {
            if(items.get(position).getToggle().equals("0")) {
                cb.setChecked(false);
                //Toast.makeText(context,"빈하트",Toast.LENGTH_SHORT).show();
                items.get(position).setToggle("1");
            }else if(items.get(position).getToggle().equals("1")){
                cb.setChecked(true);
                //Toast.makeText(context,"하트",Toast.LENGTH_SHORT).show();
                items.get(position).setToggle("0");

            }
        }catch (Exception e){

        }
    }


}


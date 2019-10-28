package com.example.myapplication.Fragment.Menu3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Menu4.ShowContext;
import com.example.myapplication.R;
import com.example.myapplication.ServerUri;

import java.util.ArrayList;

public class CodiImageAdapter extends RecyclerView.Adapter<CodiImageAdapter.ViewHolder> {


    ArrayList<UserData> items = new ArrayList<>();
    String uri = new ServerUri().uri+"/image/";

    Context context;

//    public CodiImageAdapter(Context context){
//        this.context = context;
//    }

    @NonNull

    @Override
    public CodiImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.codi_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CodiImageAdapter.ViewHolder viewholder,final int position) {
        UserData item = items.get(position);


        Glide.with(viewholder.itemView.getContext()).load(uri+item.getBoardDB()+"/"+item.getImgPath()).placeholder(new ColorDrawable(Color.WHITE)).skipMemoryCache(false).into(viewholder.image);


    }
    public void setItems(ArrayList<UserData> items){
        this.items = items;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        SquareImageView image;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.item_codi_image);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent it = new Intent(context, ShowContext.class);

                    String boardid = items.get(getAdapterPosition()).getBoardId();
                    String userid = items.get(getAdapterPosition()).getId();
                    String boardimg = items.get(getAdapterPosition()).getImgPath();
                    String username = items.get(getAdapterPosition()).getTitle();
                    String usercontent = items.get(getAdapterPosition()).getContent();
                    String usertime = items.get(getAdapterPosition()).getTime();
                    String userimg = items.get(getAdapterPosition()).getUserProFileImage();
                    String boardname = items.get(getAdapterPosition()).getBoardDB();
                    //Toast.makeText(context,"보드이름 " +items.get(position).getBoardDB()+"이미지 이름 "+items.get(position).getImgPath(),Toast.LENGTH_LONG).show();

                    it.putExtra("boardid",boardid);
                    it.putExtra("userid",userid);
                    it.putExtra("img",boardimg);
                    it.putExtra("name",username);
                    it.putExtra("content",usercontent);
                    it.putExtra("usertime",usertime);
                    it.putExtra("userimg",userimg);
                    it.putExtra("boardname",boardname);
                    context.startActivity(it);
                }
            });
        }

    }

}

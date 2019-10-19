package com.example.myapplication.Fragment.Menu4;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Menu1.RankDataDecoration;
import com.example.myapplication.Fragment.Menu4.Adapter.ComentAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShowContextIntoData extends Fragment {
    TextView userId,userId2,time,usercontext;
    ImageView ContentImage;
    CircleImageView userImage;
    String boardid;
    String boardname;
    RankDataDecoration deco;

    RecyclerView recyclerView;
    ComentAdapter adapter = new ComentAdapter();
    ArrayList<ComentData> items;

    public ShowContextIntoData() {
        // Required empty public constructor
    }

    public static ShowContextIntoData newInstance() {
        ShowContextIntoData fragment = new ShowContextIntoData();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_context_into_data, container, false);

        userId = (TextView) view.findViewById(R.id.userid);
        userId2 = (TextView) view.findViewById(R.id.userid2);
        time = (TextView) view.findViewById(R.id.time);
        usercontext = (TextView) view.findViewById(R.id.usercontext);
        userImage = (CircleImageView) view.findViewById(R.id.userimage);
        ContentImage = (ImageView) view.findViewById(R.id.image);

        Bundle bundle = getArguments();
        if(bundle != null) {
            boardid = bundle.getString("boardid");
            String userid = bundle.getString("userid");
            userId.setText(userid);
            userId2.setText(userid);

            String scontext = bundle.getString("content");
            usercontext.setText(scontext);

            String stime = bundle.getString("time");
            time.setText(stime);

            boardname = bundle.getString("boardname");

            String imagepath = bundle.getString("imagepath");
            Glide.with(this).load("http://220.68.233.35:80/Codi/image/" + boardname + "/" + imagepath).fitCenter().into(ContentImage);

            String userimg = bundle.getString("userimg");
            Glide.with(this).load("http://220.68.233.35:80/Codi/image/ProFileImage/" + userimg).centerCrop().error(R.mipmap.ic_launcher).into(userImage);



        }

        deco = new RankDataDecoration(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        if(deco != null){
            recyclerView.removeItemDecoration(deco);
        }
        recyclerView.addItemDecoration(deco);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.setItems(new BringComent(boardid,boardname,getContext()).getItems());
        recyclerView.setAdapter(adapter);


        return view;
    }

}

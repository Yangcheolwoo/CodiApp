package com.example.myapplication.Fragment.Menu1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class DayRank extends Fragment {
    RecyclerView recyclerView;
    RankDataDecoration deco;
    TextView rankmenu;
    String type = "date";
    RankAdapter adapter = new RankAdapter(this);
    int flag=0;

    public DayRank() {
        // Required empty public constructor
    }

    public static DayRank newInstance() {
        DayRank fragment = new DayRank();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_rank,container,false);
        deco = new RankDataDecoration(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.rank_recyclerview);
        if(deco != null){
            recyclerView.removeItemDecoration(deco);
        }
        recyclerView.addItemDecoration(deco);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setItems(new BringRankData(type, getContext()).getItems());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(flag != 1) {
//            adapter.setItems(new BringRankData(type, getContext()).getItems());
//            recyclerView.setAdapter(adapter);
//            //Toast.makeText(getContext(), "resume발생", Toast.LENGTH_SHORT).show();
//        }
//        flag =0;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //Toast.makeText(getContext(),"resultCode ="+resultCode+"\nrequestCode =" + requestCode,Toast.LENGTH_LONG).show();
//        if(resultCode == 3000 ){
//            if(requestCode == 1000){
//                flag = 1;
//            }
//        }
//    }
}

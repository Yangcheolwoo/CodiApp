package com.example.myapplication.Fragment.Menu4.Boards;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Fragment.Menu4.Adapter.NeedBoardAdapter;
import com.example.myapplication.Fragment.Menu4.AddContext;
import com.example.myapplication.Fragment.Menu4.BringNeedData;
import com.example.myapplication.Fragment.Menu4.ItemDataDecoration;
import com.example.myapplication.R;


public class NeedCodiBoard extends Fragment {

    String boardname = "need";
    ItemDataDecoration deco;
    RecyclerView recyclerView;
    Button Add;
    NeedBoardAdapter adapter = new NeedBoardAdapter();
    int flag=0;

    public NeedCodiBoard() {
        // Required empty public constructor
    }

    public static NeedCodiBoard newInstance() {
        return new NeedCodiBoard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_need_codi_board,container,false);
        // fragment_board

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        deco = new ItemDataDecoration(getContext());
        if(deco != null){
            recyclerView.removeItemDecoration(deco);
        }
        recyclerView.addItemDecoration(deco);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Add = view.findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapterclass.onAddItem(adapter);
                Intent it = new Intent(getContext(), AddContext.class);
                it.putExtra("boardname",boardname);
                startActivityForResult(it,1000);
            }
        });
        //Toast.makeText(getContext(),"onCreate발생",Toast.LENGTH_LONG).show();

        adapter.setItems(new BringNeedData(boardname,getContext()).getItems());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(),"onResume",Toast.LENGTH_SHORT).show();
//        adapter.setItems(new BringData(boardname,getContext()).getItems()); //
//        recyclerView.setAdapter(adapter);
        //Toast.makeText(getContext(),"resume발생",Toast.LENGTH_LONG).show();


        if(flag != 0) {
            //Toast.makeText(getContext(),"다시 붙이기",Toast.LENGTH_LONG).show();
            adapter.setItems(new BringNeedData(boardname,getContext()).getItems()); //
            recyclerView.setAdapter(adapter);
            flag = 0;
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Toast.makeText(getContext(),"resultCode ="+resultCode+"\nrequestCode =" + requestCode,Toast.LENGTH_LONG).show();
        if(resultCode == 3000 ){
            if(requestCode == 1000){
                flag = 1;
            }
        }
    }

}

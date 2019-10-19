package com.example.myapplication.Fragment.Menu2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.myapplication.Fragment.Menu1.RankDataDecoration;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserSearchItem> searchlist = new ArrayList<>();
    ArrayList<UserSearchItem> resultlist = new ArrayList<>();
    RankDataDecoration deco;
    EditText search;
    UserSearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_activity);
        deco = new RankDataDecoration(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        if(deco != null){
            recyclerView.removeItemDecoration(deco);
        }
        recyclerView.addItemDecoration(deco);

        adapter = new UserSearchAdapter();
        search = (EditText) findViewById(R.id.search);


        searchlist.addAll(new BringSearchUserItem(getApplicationContext()).getItems());

        resultlist.addAll(searchlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter.setItems(searchlist);
        recyclerView.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = search.getText().toString();
                search(text);
            }
        });

    }
    public void search(String charText){
        searchlist.clear();

        if(charText.length() == 0){
            searchlist.addAll(resultlist);
        }
        else{
            for(int i=0; i<resultlist.size(); i++){
                if(resultlist.get(i).userid.toLowerCase().contains(charText)){
                    searchlist.add(resultlist.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}

package com.example.duy.flashcard_v10;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jabbawocky on 6/26/2015.
 */
public class SearchActivity extends Activity implements View.OnClickListener{


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Helper helper;
    private Button btnsearch;
    private EditText txtsearch;
    private List<Info> mContentItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);

        mRecyclerView = (RecyclerView) findViewById(R.id.searchview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        txtsearch = (EditText) findViewById(R.id.txtsearch);
        btnsearch = (Button) findViewById(R.id.btn_search);
        helper = new Helper(this);

        btnsearch.setOnClickListener(this);
        LoadData();
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id)
        {
            case R.id.btn_search:{
                Search();
            }
        }
     }

    public void Search(){
        Cursor cur = helper.CheckWord(txtsearch.getText().toString());
        if(cur.getCount() == 0){
            Toast.makeText(SearchActivity.this,"No Result Found",Toast.LENGTH_LONG).show();
        }
        else
        {
            getData(cur);
        }
    }

    public void getData(Cursor cur){
        mContentItems.clear();
        while (cur.moveToNext()) {
            mContentItems.add(new Info(cur.getString(1)
                    , cur.getString(2)
                    , Integer.parseInt(cur.getString(3))
                    , cur.getString(0)
                    , cur.getInt(4)));
        }
    }

    public void LoadData(){
//        new Initialize().execute();
        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems, SearchActivity.this));

        mRecyclerView.setAdapter(mAdapter);
        // Register to Main
        MaterialViewPagerHelper.registerRecyclerView(SearchActivity.this, mRecyclerView, null);
    }

}





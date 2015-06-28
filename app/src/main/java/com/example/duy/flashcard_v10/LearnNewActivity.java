package com.example.duy.flashcard_v10;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jabbawocky on 6/26/2015.
 */
public class LearnNewActivity extends Activity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Helper helper;
    private List<Info> mContentItems = new ArrayList<>();
//    private TextView txtword;
//    private TextView txtcate;
//    private TextView txtmean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learnlayout);
//        txtword = (TextView) findViewById(R.id.txtword);
//        txtcate = (TextView) findViewById(R.id.txtcate);
//        txtmean = (TextView) findViewById(R.id.txtmean);

        mRecyclerView = (RecyclerView) findViewById(R.id.learnview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LearnNewActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        helper = new Helper(this);
        helper.adddata2();
        Cursor cur = helper.GetAllData2();

        if(cur.getCount() == 0) {
            Toast.makeText(LearnNewActivity.this,"Can Not Load Table",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            getData(cur);
        }

        LoadData();
    }



    public void getData(Cursor cur) {
        mContentItems.clear();
        while (cur.moveToNext()) {
            mContentItems.add(new Info(cur.getString(1) //Word
                    ,cur.getString(2)   // Cate
                    , cur.getString(3)  // Meaning
                    , cur.getInt(4)     // Time
                    , cur.getInt(5)     // Priority
            ));
        }
    }

    public void LoadData() {
//        new Initialize().execute();
        mAdapter = new RecyclerViewMaterialAdapter(new LearningAdapter(mContentItems, LearnNewActivity.this));

        mRecyclerView.setAdapter(mAdapter);
        // Register to Main
        MaterialViewPagerHelper.registerRecyclerView(LearnNewActivity.this, mRecyclerView, null);
    }
}

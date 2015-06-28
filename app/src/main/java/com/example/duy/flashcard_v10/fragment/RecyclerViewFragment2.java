package com.example.duy.flashcard_v10.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.example.duy.flashcard_v10.Helper;
import com.example.duy.flashcard_v10.Info;
import com.example.duy.flashcard_v10.R;
import com.example.duy.flashcard_v10.TestRecyclerViewAdapter;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duy on 6/26/2015.
 */
public class RecyclerViewFragment2 extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Helper helper;
    private List<Info> mContentItems = new ArrayList<>();
    private Context mcontext;
    public static RecyclerViewFragment2 newInstance() {
        return new RecyclerViewFragment2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        LoadData();
    }

    public void LoadData(){
        helper = new Helper(getActivity());
        mcontext = getActivity().getApplicationContext();

        getData();

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems,mcontext));

        mRecyclerView.setAdapter(mAdapter);
        // Register to Main
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    public void getData(){
        Cursor res = helper.GetAllData();
        mContentItems.clear();
        if(res.getCount() == 0) {
            mContentItems.add(new Info("Create Word","",0,"",0));
        }
        else {
            while (res.moveToNext()) {
                if(res.getInt(4)!=0) {
                    mContentItems.add(new Info(res.getString(1)
                            , res.getString(2)
                            , 0
                            , res.getString(0)
                            , Integer.parseInt(res.getString(4))));
                }
            }
        }

    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            LoadData();
        }
    }


}



package com.example.duy.flashcard_v10.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duy.flashcard_v10.Helper;
import com.example.duy.flashcard_v10.Info;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.example.duy.flashcard_v10.R;
import com.example.duy.flashcard_v10.TestRecyclerViewAdapter;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duy on 22/06/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Helper helper;
    private List<Info> mContentItems = new ArrayList<>();
    private Context mcontext;
//    ProgressDialog pDlg;
    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
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
        helper = new Helper(getActivity());
        mcontext = getActivity().getApplicationContext();
//        pDlg = new ProgressDialog(mcontext);
//        pDlg.setMessage("Loading...");

    }

    public void LoadData(){
//        new Initialize().execute();
        getData();
        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mContentItems, mcontext));

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
                mContentItems.add(new Info(res.getString(1)
                        ,res.getString(2)
                        ,Integer.parseInt(res.getString(3))
                        , res.getString(0)
                        , res.getInt(4)));
            }
        }

    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            LoadData();
        }
    }// Update fragment

//    private class Initialize extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//
//            pDlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
//
//                public void onCancel(DialogInterface dialog) {
//                    // TODO Auto-generated method stub
//                      Initialize.this.cancel(false);
//                }
//            });
//            pDlg.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            // TODO Auto-generated method stub
//
//            try {
//                getData();
//            }
//            catch(Exception e)
//            {
//                Toast.makeText(mcontext, "Can not load database", Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//            try {
//
////                pDlg.dismiss();
//            }
//            catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }
//
//    }// Asyntask

}


package com.example.duy.flashcard_v10;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duy.flashcard_v10.fragment.RecyclerViewFragment;
import com.example.duy.flashcard_v10.fragment.ScrollFragment;
import com.example.duy.flashcard_v10.fragment.ScrollFragment2;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by duy on 22/06/15.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.WorkViewHolder> {

    List<Info> contents;

    static final int TYPE_CELL = 1;
    Context mContext;
    static boolean flag = false;
    public TestRecyclerViewAdapter(List<Info> contents,Context mContext) {
        this.contents = contents;
        this.mContext = mContext;
    }// Get Data from fragment

    public static class WorkViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtword;
        TextView txtmean;
        WorkViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_views);
            txtword = (TextView)itemView.findViewById(R.id.txtword);
            txtmean = (TextView)itemView.findViewById(R.id.txtmean);
        }

    }// Get id from view

    @Override
    public int getItemViewType(int position) {
        switch (position) {
//            case 0:
//                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public WorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
//            case TYPE_HEADER: {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_item_card_big, parent, false);
//                return new RecyclerView.ViewHolder(view) {
//                };
//            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                WorkViewHolder pvh = new WorkViewHolder(view);
                return pvh;
//                return new RecyclerView.ViewHolder(view) {
//                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final WorkViewHolder holder, int i) {
        switch (getItemViewType(i)) {
            case TYPE_CELL:{
                holder.txtword.setText(contents.get(i).Word);
                holder.txtmean.setText(contents.get(i).Meaning);
                holder.txtmean.setVisibility(View.INVISIBLE);
                final int j=i;
                holder.cv.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(contents.get(j).Meaning.isEmpty())
                                {
                                    return;
                                }
                                if(flag == false) {
                                    holder.txtmean.setVisibility(View.VISIBLE);
                                    flag = true;
                                }
                                else {
                                    holder.txtmean.setVisibility(View.INVISIBLE);
                                    flag = false;
                                }
                            }
                        }
                );
                holder.cv.setOnLongClickListener(
                        new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Intent intent = new Intent(v.getContext(), EditActivity.class);
//                                Bundle myData = new Bundle();
//                                myData.putString("Word",holder.Word.getText().toString());
//                                intent.putExtras(myData);
                                intent.putExtra("id", contents.get(j).getid());
                                intent.putExtra("isChecked",contents.get(j).getPriority());
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                v.getContext().startActivity(intent);
                                return false;
                            }
                        }
                );


            }
        }
    }// Set the values to the view

//    public void swapItems(List<Info> Infos) {
//        this.contents = Infos;
//        notifyDataSetChanged();
//    } reload data test01


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
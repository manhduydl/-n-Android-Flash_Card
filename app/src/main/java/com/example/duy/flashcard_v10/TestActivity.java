package com.example.duy.flashcard_v10;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

/**
 * Created by Jabbawocky on 6/26/2015.
 */
public class TestActivity extends Activity implements View.OnClickListener{
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private TextView txtword;
    private Helper helper;
    private String id;

    private String[] strsen = {"Organization", "Tire", "Shampoo", "Request", "Birthday", "Hospital", "Elevator", "Japan", "Football", "Student"};
    private String[] strsvn = {"Tổ chức", "lốp xe", "Dầu gội" , "yêu cầu", "sinh nhật", "Bệnh viện", "Thang máy", "Nhật Bản" , "Bóng đá", "Sinh viên"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

     super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);
        txtword = (TextView) findViewById(R.id.testword);

//        helper = new Helper(this);
//
//        Intent mIntentHandler = getIntent();
//        Bundle mBundle = mIntentHandler.getExtras();
//
//        id = mBundle.getString("id");
//
//        Cursor cur = helper.getData(id);
//
//        if(cur.moveToFirst() == false){
//            Toast.makeText(TestActivity.this, "No data found", Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//
//        }

    }
    @Override
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case R.id.btnA:{

            }
        }
    }
}
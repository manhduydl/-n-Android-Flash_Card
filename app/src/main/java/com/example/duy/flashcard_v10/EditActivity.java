package com.example.duy.flashcard_v10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Jabbawocky on 6/25/2015.
 */
public class EditActivity extends Activity{

    private Button btn_save;
    private Button btn_delete;
    private EditText txtWord;
    private EditText txtMean;
    private Helper helper;
    private CheckBox ck;
    private int isChecked;
    private String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_scroll2);

        txtWord = (EditText) findViewById(R.id.edttxt_word);
        txtMean = (EditText) findViewById(R.id.edttxt_mean);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        ck = (CheckBox) findViewById(R.id.ck_add);
        helper = new Helper(this);

        Intent mIntentHandler = getIntent();
        Bundle mBundle = mIntentHandler.getExtras();

        id = mBundle.getString("id");

        Cursor cur = helper.getData(id);

        if(cur.moveToFirst() == false){
            Toast.makeText(EditActivity.this,"No data found",Toast.LENGTH_LONG).show();
        }
        else {
            isChecked = cur.getInt(4);
            if (isChecked==1){
                ck.setChecked(true);
            }
            else
                ck.setChecked(false);
            txtWord.setText(cur.getString(1));
            txtMean.setText(cur.getString(2));
        }
        Update();
        Delete();


    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event " + event.getRawX() + "," + event.getRawY() + " " + x + "," + y + " rect " + w.getLeft() + "," + w.getTop() + "," + w.getRight() + "," + w.getBottom() + " coords " + scrcoords[0] + "," + scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    public void Update(){
        btn_save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean IsUpdate = helper.updateData( String.valueOf(id)
                                , txtWord.getText().toString()
                                , txtMean.getText().toString()
                                , ck.isChecked());
                        if(IsUpdate == true){
                            Toast.makeText(EditActivity.this, "Update Success!!!", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(EditActivity.this,"Update Failed!!!",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }// Update Word and Meaning

    public void Delete(){
        btn_delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean IsDelete = helper.DeleteData(String.valueOf(id));
                        if(IsDelete == true)
                        {
                            Intent callback = new Intent(v.getContext(), MainActivity.class);
                            Toast.makeText(EditActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(callback);
                        }
                        else
                            Toast.makeText(EditActivity.this,"Delete data failed",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }// Delete Word
}

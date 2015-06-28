package com.example.duy.flashcard_v10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jabbawocky on 6/24/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "EngReminder.db";
    public static  final int dbVersion = 3;
    public static final String TABLE_NAME = "Flash";

    public static final String Col_ID = "ID";
    public static final String Col_Word = "WORD";
    public static final String Col_M = "MEANING";
    public static final String Col_Time = "TIME";
    public static final String Col_Priority = "PRIORITY";
    public static final String Col_Cate = "CATEGORY";
    // Ky tu
    public static final String Type_int = " INTEGER ";
    public static final String Type_text = " TEXT ";
    public static final String COMMA = ",";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    Col_ID + Type_int+" PRIMARY KEY AUTOINCREMENT, " +
                    Col_Word + Type_text + COMMA +
                    Col_M + Type_text + COMMA +
                    Col_Time + Type_int + COMMA +
                    Col_Priority + Type_int +
                    // Can be extended
                    ")";
    public static final String TABLE_NAME2 = " InWord ";
    public static final String CREATE_TABLE2 =
            "CREATE TABLE " + TABLE_NAME2 + "(" +
                    Col_ID + Type_int+" PRIMARY KEY AUTOINCREMENT, " +
                    Col_Word + Type_text + COMMA +
                    Col_Cate + Type_text + COMMA +
                    Col_M + Type_text + COMMA +
                    Col_Time + Type_int + COMMA +
                    Col_Priority + Type_int +
                    // Can be extended
                    ")";
    private List<Info> mContentItems = new ArrayList<>();
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS" + TABLE_NAME;

    public static String[] Columns = {Col_ID,Col_Word,Col_M,Col_Time,Col_Priority};


    public DatabaseHelper(Context context){

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //
    }

    public boolean insertData(String Word, String Meaning, int priority)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues Content = new ContentValues();
        Content.put(Col_Word,Word);
        Content.put(Col_M,Meaning);
        Content.put(Col_Time,0);
        Content.put(Col_Priority,priority);
        //Content.put(Col_Time,time.toString());
        long insert = db.insert(TABLE_NAME, null, Content);
        if(insert == -1)
        {
            return false;
        }
        else
            return true;
    }
    public Cursor GetAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(TABLE_NAME,
                null,        // Columns
                null,           // Where "Clause"
                null,           // args for Whereclause
                null,           //Groupby
                null,           //having
                null);          //Order
        //db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public Cursor getData(String id)
    {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor res = db.query(TABLE_NAME
                ,Columns
                ," id = ? "
                ,new String[]{id}
                ,null
                ,null
                ,null);
        //db.rawQuery("select * from " + TABLE_NAME + "where ID = " + id, null);
        return res;
    }
    public boolean updateData(String id, String Word, String Meaning, boolean ischecked )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Word",Word);
        content.put("Meaning",Meaning);
        if(ischecked) {
            content.put("PRIORITY", 1);
        }
        else
            content.put("PRIORITY", 0);
        long update = db.update(TABLE_NAME,content," id= ? ",new String[]{id});
        return true;
    }
    public Cursor CheckWord( String Word)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLE_NAME
                ,Columns
                ," WORD LIKE ? "
                ,new String[]{ Word}
                ,null
                ,null
                ,null);
        return cur;
    }
    public Cursor IsExist(String Word, String Meaning)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLE_NAME
                ,Columns
                ," WORD LIKE ? AND MEANING LIKE ? "
                ,new String[]{ Word, Meaning}
                ,null
                ,null
                ,null);
        return cur;
    }

    public boolean DeleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete(TABLE_NAME," id = ? ",new String[]{id});
        if(delete !=0) {
            return true;
        }
        return false;
    }

    public void addData2(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues Content = new ContentValues();
        mContentItems.add(new Info("eligible","a.","Đủ tư cách",0,0));
        mContentItems.add(new Info("flexible","a.","linh động",0,0));
        mContentItems.add(new Info("retire","v.","nghỉ hưu",0,0));
        mContentItems.add(new Info("vest","v.","trao quyền",0,0));
        mContentItems.add(new Info("wage","n.","tiền lương",0,0));
        mContentItems.add(new Info("achievement","n.","thành tựu",0,0));
        mContentItems.add(new Info("contribute","v.","đóng góp",0,0));
        mContentItems.add(new Info("obvious","a.","rõ ràng",0,0));
        mContentItems.add(new Info("productive","a.","có năng suất",0,0));
        mContentItems.add(new Info("comfort","v.","an ủi",0,0));

        for(int i=0; i < mContentItems.size(); i++) {
            Content.put(Col_Word, mContentItems.get(i).Word);
            Content.put(Col_Cate, mContentItems.get(i).cate);
            Content.put(Col_M, mContentItems.get(i).Meaning);
            Content.put(Col_Time, 0);
            Content.put(Col_Priority, 0);
            db.insert(TABLE_NAME2, null, Content);
            Content.clear();
        }
    }
    public Cursor GetAllData2()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(TABLE_NAME2,
                null,        // Columns
                null,           // Where "Clause"
                null,           // args for Whereclause
                null,           //Groupby
                null,           //having
                null);          //Order
        //db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}

package com.example.duy.flashcard_v10;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Jabbawocky on 6/24/2015.
 */
public class Helper {
    private DatabaseHelper obj;

    public Helper(Context context){
        obj = new DatabaseHelper(context);
    }

    public boolean IsEmpty(String Word, String Meaning){
        if(Word.isEmpty() || Meaning.isEmpty())
        {
            return true;
        }
        return false;
    }

    public boolean IsExist(String Word, String Meaning){
        Cursor cur = obj.IsExist(Word, Meaning);
        if(cur.getCount() != 0)
            return true;
        return false;
    }

    public String insertData(String Word, String Meaning, int priority){
        if(IsEmpty(Word, Meaning))
        {
            return "Data is empty";
        }
        else if(IsExist(Word, Meaning))
        {
            return "Data already exist";
        }
        else {
            boolean insert = obj.insertData(Word, Meaning, priority);
            if(insert == true)
                return "Data inserted";
            else
                return "Data is not inserted";
        }
    }

    public Cursor GetAllData(){
        return obj.GetAllData();
    }

    public Cursor getData(String id)
    {
        return obj.getData(id);
    }

    public boolean updateData(String id, String Word, String Meaning, boolean ischecked ){
        return obj.updateData(id, Word, Meaning, ischecked );
    }

    public Cursor CheckWord( String Meaning)
    {
        return obj.CheckWord(Meaning);
    }

    public boolean DeleteData(String id)
    {
        return obj.DeleteData(id);
    }

    public void adddata2(){
        obj.addData2();
    }

//    ID Word Cate Meaning Time Priority
    public Cursor GetAllData2(){
        return obj.GetAllData2();
    }

}


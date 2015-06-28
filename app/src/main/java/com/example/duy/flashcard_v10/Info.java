package com.example.duy.flashcard_v10;

/**
 * Created by Jabbawocky on 6/24/2015.
 */
public class Info {
    String Word;
    String Meaning;
    int time;
    int priority;
    private String id;
    String cate;
    public Info(String Word, String Meaning, int time, String id, Integer priority) {
        this.Word = Word;
        this.Meaning = Meaning;
        this.time = time;
        setid(id);
        this.priority= priority;
    }
    public Info(String Word, String cate, String Meaning, int time, Integer priority) {
        this.Word = Word;
        this.cate = cate;
        this.Meaning = Meaning;
        this.time = time;
        this.priority= priority;
    }
    public int getPriority(){
        return priority;
    }
    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }
}

package com.example.duy.flashcard_v10;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Duy on 6/26/2015.
 */
public class info_parcel implements Parcelable{
    String Word;
    String Meaning;
    int time;
    int priority;
    private String id;

    public info_parcel(String Word, String Meaning, int time, String id, Integer priority) {
        this.Word = Word;
        this.Meaning = Meaning;
        this.time = time;
        setid(id);
        this.priority = priority;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public info_parcel(Parcel in) {
        this.id = in.readString();
        this.Word = in.readString();
        this.Meaning = in.readString();
        this.priority = in.readInt();
        this.time = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(Word);
        dest.writeString(Meaning);
        dest.writeInt(priority);
        dest.writeInt(time);
    }
    public static final Parcelable.Creator<info_parcel> CREATOR = new Parcelable.Creator<info_parcel>() {

        public info_parcel createFromParcel(Parcel in) {
            return new info_parcel(in);
        }

        public info_parcel[] newArray(int size) {
            return new info_parcel[size];
        }
    };
}

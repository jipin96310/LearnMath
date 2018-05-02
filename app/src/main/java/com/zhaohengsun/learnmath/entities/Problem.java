package com.zhaohengsun.learnmath.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.zhaohengsun.learnmath.contracts.ProblemContract;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class Problem  implements Parcelable{
    public long id;

    public String head;

    public String body;

    public long score;

    public long difficulty;

    public String category;

    public Problem(long idtext) {
        this.id = idtext;
    }

    public Problem() {
    }
    public Problem( String head,String body,long score,long difficulty,String category) {

        this.head = head;
        this.body = body;
        this.score = score;
        this.difficulty = difficulty;
        this.category = category;
    }

    public Problem(Cursor cursor) {
        //  init from cursor

        this.id = ProblemContract.getId(cursor);
        this.head = ProblemContract.getHead(cursor);
        this.body = ProblemContract.getBody(cursor);
        this.score = ProblemContract.getScore(cursor);
        this.difficulty = ProblemContract.getDifficulty(cursor);
        this.category = ProblemContract.getCategory(cursor);
    }

    public void writeToProvider(ContentValues out) {
        //  write to ContentValues

        ProblemContract.putHead(out,this.head);
        ProblemContract.putBody(out,this.body);

        ProblemContract.putScore(out,this.score);

        ProblemContract.putDifficulty(out,this.difficulty);
        ProblemContract.putCategory(out,this.category);


    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.head);
        dest.writeString(this.body);
        dest.writeLong(this.score);
        dest.writeLong(this.difficulty);
        dest.writeString(this.category);
    }

    protected Problem(Parcel in) {
        this.head = in.readString();
        this.body = in.readString();
        this.score = in.readLong();
        this.difficulty = in.readLong();
        this.category = in.readString();
    }

    public static final Creator<Problem> CREATOR = new Creator<Problem>() {
        @Override
        public Problem createFromParcel(Parcel source) {
            return new Problem(source);
        }

        @Override
        public Problem[] newArray(int size) {
            return new Problem[size];
        }
    };
}

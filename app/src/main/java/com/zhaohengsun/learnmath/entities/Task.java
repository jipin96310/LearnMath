package com.zhaohengsun.learnmath.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.zhaohengsun.learnmath.contracts.TaskContract;

/**
 * Created by Zhaoheng Sun on 2018/2/25.
 */

public class Task implements Parcelable{


    public long id;

    public String studentName;

    public Problem[] problems;

    public String isFinished;

    public long timeLimit;
    public Task( String studentName,Problem[] problems,String isFinished,long timeLimit) {

        this.studentName = studentName;
        this.problems = problems;
        this.isFinished = isFinished;
        this.timeLimit = timeLimit;

    }

    public Task(Cursor cursor) {
        //  init from cursor


        String[] problems = TaskContract.getProblems(cursor);

        Problem[] problemsarr = new Problem[problems.length];
        for(int i =0;i<problemsarr.length;i++){
            problemsarr[i] = new Problem(Long.parseLong(problems[i]));

        }

        this.id = TaskContract.getId(cursor);
        this.studentName = TaskContract.getStudentName(cursor);
        this.problems = problemsarr;
        this.isFinished = TaskContract.getIsFinished(cursor);
        this.timeLimit = TaskContract.getTimelimit(cursor);


    }

    public void writeToProvider(ContentValues out) {
        // TODO write to ContentValues

        TaskContract.putStudentName(out,this.studentName);
        StringBuffer sb = new StringBuffer();

        Problem[] problemst = this.problems;

        for(int i =0;i<problemst.length;i++){
        sb.append(problemst[i].score);
            Log.d("task", "writeToProvider: "+problemst[i].score);

        if(i!=problemst.length-1)sb.append("|");
        }



        TaskContract.putProblems(out,sb.toString());


        TaskContract.putIsFinished(out,this.isFinished);

        TaskContract.putTimelimit(out,this.timeLimit);

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.studentName);
        dest.writeTypedArray(this.problems, flags);
        dest.writeString(this.isFinished);
        dest.writeLong(this.timeLimit);
    }

    protected Task(Parcel in) {
        this.id = in.readLong();
        this.studentName = in.readString();
        this.problems = in.createTypedArray(Problem.CREATOR);
        this.isFinished = in.readString();
        this.timeLimit = in.readLong();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}

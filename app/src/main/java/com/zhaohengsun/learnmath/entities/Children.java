package com.zhaohengsun.learnmath.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.zhaohengsun.learnmath.contracts.ChildrenContract;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class Children implements Parcelable{

    public long id;

    public String name;

    public String schooltype;


    public long points;


    public Children( String name,String schooltype) {

        this.name = name;
        this.schooltype = schooltype;
        this.points  =   0;

    }

    public Children(Cursor cursor) {
        //  init from cursor
    //
       this.id = ChildrenContract.getId(cursor);
       this.name = ChildrenContract.getName(cursor);
       this.schooltype = ChildrenContract.getSchooltype(cursor);
       this.points   = ChildrenContract.getPoints(cursor);

    }

    public void writeToProvider(ContentValues out) {
        //  write to ContentValues

        ChildrenContract.putName(out,this.name);
        ChildrenContract.putSchooltype(out,this.schooltype);
        ChildrenContract.putPoints(out,this.points);

    //

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.schooltype);
        dest.writeLong(this.points);
    }

    public Children() {
    }

    protected Children(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.schooltype = in.readString();
        this.points   = in.readLong();
    }

    public static final Creator<Children> CREATOR = new Creator<Children>() {
        @Override
        public Children createFromParcel(Parcel source) {
            return new Children(source);
        }

        @Override
        public Children[] newArray(int size) {
            return new Children[size];
        }
    };
}

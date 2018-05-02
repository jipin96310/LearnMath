package com.zhaohengsun.learnmath.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class MainItem implements Parcelable{

    public long id;
    public String title;
    public String article;



    public MainItem(long id,String title,String article){

        this.id = id;
        this.title = title;
        this.article = article;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.article);
    }

    protected MainItem(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.article = in.readString();
    }

    public static final Creator<MainItem> CREATOR = new Creator<MainItem>() {
        @Override
        public MainItem createFromParcel(Parcel source) {
            return new MainItem(source);
        }

        @Override
        public MainItem[] newArray(int size) {
            return new MainItem[size];
        }
    };
}

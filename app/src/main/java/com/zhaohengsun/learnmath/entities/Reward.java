package com.zhaohengsun.learnmath.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.zhaohengsun.learnmath.contracts.RewardContract;

/**
 * Created by Zhaoheng Sun on 2018/4/25.
 */

public class Reward implements Parcelable {


    public long id;

    public String rewardName;
    public long rewardPoints;
    public String recipientNanme;

    public Reward(String rewardName, long  rewardPoints) {

        this.rewardName = rewardName;
        this.rewardPoints = rewardPoints;
        this.recipientNanme  =   "?";

    }

    public Reward(Cursor cursor) {
        //  init from cursor
        //
        this.id = RewardContract.getId(cursor);
        this.rewardName = RewardContract.getRewardName(cursor);
        this.rewardPoints = RewardContract.getRewardPoints(cursor);
        this.recipientNanme   = RewardContract.getRecipientName(cursor);

    }

    public void writeToProvider(ContentValues out) {
        //  write to ContentValues
     //   RewardContract.putId(out,this.id);
        RewardContract.putRewardName(out,this.rewardName);
        RewardContract.putRewardPoints(out,this.rewardPoints);
        RewardContract.putRecipientName(out,this.recipientNanme);

        //

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.rewardName);
        dest.writeLong(this.rewardPoints);
        dest.writeString(this.recipientNanme);
    }

    protected Reward(Parcel in) {
        this.id = in.readLong();
        this.rewardName = in.readString();
        this.rewardPoints = in.readLong();
        this.recipientNanme = in.readString();
    }

    public static final Creator<Reward> CREATOR = new Creator<Reward>() {
        @Override
        public Reward createFromParcel(Parcel source) {
            return new Reward(source);
        }

        @Override
        public Reward[] newArray(int size) {
            return new Reward[size];
        }
    };
}

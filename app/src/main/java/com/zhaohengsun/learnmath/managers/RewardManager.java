package com.zhaohengsun.learnmath.managers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.zhaohengsun.learnmath.async.AsyncContentResolver;
import com.zhaohengsun.learnmath.async.IContinue;
import com.zhaohengsun.learnmath.async.IEntityCreator;
import com.zhaohengsun.learnmath.async.QueryBuilder;
import com.zhaohengsun.learnmath.contracts.RewardContract;
import com.zhaohengsun.learnmath.entities.Reward;

import java.util.Set;

/**
 * Created by Zhaoheng Sun on 2018/4/25.
 */

public class RewardManager extends Manager<Reward> {

    private static final int LOADER_ID = 4;

    private static final IEntityCreator<Reward> creator = new IEntityCreator<Reward>() {
        @Override
        public Reward create(Cursor cursor) {
            return new Reward(cursor);
        }
    };

    private AsyncContentResolver contentResolver;

    public RewardManager(Context context) {
        super(context, creator, LOADER_ID);
        contentResolver = new AsyncContentResolver(context.getContentResolver());
    }

    public void getAllRewardsAsync(QueryBuilder.IQueryListener<Reward> listener) {
        //  use QueryBuilder to complete this

        if(listener==null) {
            this.executeQuery(RewardContract.CONTENT_URI, listener);
        }else {

            String[]   projection =    new String[]{RewardContract.ID,RewardContract.REWARD_NAME,RewardContract.REWARD_POINTS,RewardContract.RECIPIENT_NAME};
            this.reexecuteQuery(RewardContract.CONTENT_URI, projection, null, null, listener);

        }

    }

    public void getRewardAsync(long id, IContinue<Reward> callback) {
        //
        String[]   projection =    new String[]{RewardContract.ID,RewardContract.REWARD_NAME,RewardContract.REWARD_POINTS,RewardContract.RECIPIENT_NAME};
        ContentResolver cr = getSyncResolver();

        String[] select = new String[]{id+""};
        Cursor c = cr.query(RewardContract.CONTENT_URI(id),projection,RewardContract.ID+"=?",select,null,null);

        if(c.moveToFirst()) {
         getAsyncResolver().queryAsync(RewardContract.CONTENT_URI(id), projection,RewardContract.ID+"=?",select,null,null);

        }


    }



    public void persistAsync(final Reward reward, IContinue<Uri> callback) {
        //


        ContentValues values = new ContentValues();
        reward.writeToProvider(values);
        contentResolver.insertAsync(RewardContract.CONTENT_URI,
                values,  callback
        );




    }

    public void deleteRewardsAsync(Set<Long> toBeDeleted, IContinue<Uri> callback) {
        /**

         Long[] ids = new Long[toBeDeleted.size()];
         toBeDeleted.toArray(ids);
         String[] args = new String[ids.length];

         StringBuilder sb = new StringBuilder();
         if (ids.length > 0) {
         //sb.append(AuthorContract.ID);
         sb.append("=?");
         args[0] = ids[0].toString();
         for (int ix=1; ix<ids.length; ix++) {
         sb.append(" or ");
         sb.append(AuthorContract.ID);
         sb.append("=?");
         args[ix] = ids[ix].toString();
         }
         }
         String select = sb.toString();

         contentResolver.deleteAsync(BookContract.CONTENT_URI, select, args,callback);
         **/
    }

}

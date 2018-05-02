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
import com.zhaohengsun.learnmath.contracts.ChildrenContract;
import com.zhaohengsun.learnmath.entities.Children;

import java.util.Set;

/**
 * Created by Zhaoheng Sun on 2018/2/25.
 */

public class ChildrenManager extends Manager<Children> {

    private static final int LOADER_ID = 2;

    private static final IEntityCreator<Children> creator = new IEntityCreator<Children>() {
        @Override
        public Children create(Cursor cursor) {
            return new Children(cursor);
        }
    };

    private AsyncContentResolver contentResolver;

    public ChildrenManager(Context context) {
        super(context, creator, LOADER_ID);
        contentResolver = new AsyncContentResolver(context.getContentResolver());
    }

    public void getAllChildrenAsync(QueryBuilder.IQueryListener<Children> listener) {
        //  use QueryBuilder to complete this

        if(listener==null) {
            this.executeQuery(ChildrenContract.CONTENT_URI, listener);
        }else {

            String[]  projection = new String[]{ChildrenContract.ID,ChildrenContract.NAME, ChildrenContract.SCHOOLTYPE,ChildrenContract.POINTS};
            this.reexecuteQuery(ChildrenContract.CONTENT_URI, projection, null, null, listener);

        }

    }

    public void getChildAsync(long id, IContinue<Children> callback) {
        //
        String[]  projection = new String[]{ChildrenContract.ID,ChildrenContract.NAME, ChildrenContract.SCHOOLTYPE,ChildrenContract.POINTS};

        ContentResolver cr = getSyncResolver();

        String[] select = new String[]{id+""};
        Cursor c = cr.query(ChildrenContract.CONTENT_URI(id),projection,ChildrenContract.ID+"=?",select,null,null);

        if(c.moveToFirst()) {
            getAsyncResolver().queryAsync(ChildrenContract.CONTENT_URI(id), projection,ChildrenContract.ID+"=?",select,null,null);

        }



    }

    public void persistAsync(final Children children, IContinue<Uri> callback) {
        //


        ContentValues values = new ContentValues();
        children.writeToProvider(values);
        contentResolver.insertAsync(ChildrenContract.CONTENT_URI,
                values,  callback
        );




    }

    public void deleteChildrenAsync(Set<Long> toBeDeleted, IContinue<Uri> callback) {
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
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
import com.zhaohengsun.learnmath.contracts.ProblemContract;
import com.zhaohengsun.learnmath.entities.Problem;

import java.util.Set;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class ProblemManager extends Manager<Problem> {

    private static final int LOADER_ID = 1;

    private static final IEntityCreator<Problem> creator = new IEntityCreator<Problem>() {
        @Override
        public Problem create(Cursor cursor) {
            return new Problem(cursor);
        }
    };

    private AsyncContentResolver contentResolver;

    public ProblemManager(Context context) {
        super(context, creator, LOADER_ID);
        contentResolver = new AsyncContentResolver(context.getContentResolver());
    }

    public void getAllBooksAsync(QueryBuilder.IQueryListener<Problem> listener) {
        //  use QueryBuilder to complete this

        if(listener==null) {
            this.executeQuery(ProblemContract.CONTENT_URI, listener);
        }else {

            String[]  projection = new String[]{ProblemContract.ID,ProblemContract.HEAD, ProblemContract.BODY, ProblemContract.SCORE, ProblemContract.DIFFICULTY,ProblemContract.CATEGORY};
            this.reexecuteQuery(ProblemContract.CONTENT_URI, projection, null, null, listener);

        }

    }

    public void getBookAsync(long id, IContinue<Problem> callback) {
        //
        String[]  projection = new String[]{ProblemContract.ID,ProblemContract.HEAD, ProblemContract.BODY, ProblemContract.SCORE, ProblemContract.DIFFICULTY,ProblemContract.CATEGORY};

        ContentResolver cr = getSyncResolver();

        String[] select = new String[]{id+""};
        Cursor c = cr.query(ProblemContract.CONTENT_URI(id),projection,ProblemContract.ID+"=?",select,null,null);

        if(c.moveToFirst()) {
            getAsyncResolver().queryAsync(ProblemContract.CONTENT_URI(id), projection,ProblemContract.ID+"=?",select,null,null);

        }



    }

    public void persistAsync(final Problem book, IContinue<Uri> callback) {
        //




        ContentValues values = new ContentValues();
        book.writeToProvider(values);
        contentResolver.insertAsync(ProblemContract.CONTENT_URI,
                values,  callback
        );




    }

    public void deleteBooksAsync(Set<Long> toBeDeleted, IContinue<Uri> callback) {
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

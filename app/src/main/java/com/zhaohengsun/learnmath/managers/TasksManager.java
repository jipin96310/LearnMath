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
import com.zhaohengsun.learnmath.contracts.TaskContract;
import com.zhaohengsun.learnmath.entities.Task;

import java.util.Set;

/**
 * Created by Zhaoheng Sun on 2018/2/25.
 */

public class TasksManager extends Manager<Task> {

    private static final int LOADER_ID = 3;

    private static final IEntityCreator<Task> creator = new IEntityCreator<Task>() {
        @Override
        public Task create(Cursor cursor) {
            return new Task(cursor);
        }
    };

    private AsyncContentResolver contentResolver;

    public TasksManager(Context context) {
        super(context, creator, LOADER_ID);
        contentResolver = new AsyncContentResolver(context.getContentResolver());
    }

    public void getAllTasksAsync(QueryBuilder.IQueryListener<Task> listener) {
        //  use QueryBuilder to complete this

        if(listener==null) {
            this.executeQuery(TaskContract.CONTENT_URI, listener);
        }else {

            String[]  projection = new String[]{TaskContract.ID,TaskContract.STUDENTNAME, TaskContract.PROBLEMS,TaskContract.ISFINISHED,TaskContract.TIMELIMIT};
            this.reexecuteQuery(TaskContract.CONTENT_URI, projection, null, null, listener);

        }

    }

    public void getTaskAsync(long id, IContinue<Task> callback) {
        //
        String[]  projection = new String[]{TaskContract.ID,TaskContract.STUDENTNAME, TaskContract.PROBLEMS,TaskContract.ISFINISHED,TaskContract.TIMELIMIT};
        ContentResolver cr = getSyncResolver();

        String[] select = new String[]{id+""};
        Cursor c = cr.query(TaskContract.CONTENT_URI(id),projection,TaskContract.ID+"=?",select,null,null);

        if(c.moveToFirst()) {
            getAsyncResolver().queryAsync(TaskContract.CONTENT_URI(id), projection,TaskContract.ID+"=?",select,null,null);

        }


    }







    public void persistAsync(final Task task, IContinue<Uri> callback) {
        //


        ContentValues values = new ContentValues();
        task.writeToProvider(values);
        contentResolver.insertAsync(TaskContract.CONTENT_URI,
                values,  callback
        );




    }

    public void deleteTasksAsync(Set<Long> toBeDeleted, IContinue<Uri> callback) {
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

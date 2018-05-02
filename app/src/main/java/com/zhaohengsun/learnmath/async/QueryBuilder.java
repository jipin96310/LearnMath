package com.zhaohengsun.learnmath.async;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.zhaohengsun.learnmath.contracts.ChildrenContract;
import com.zhaohengsun.learnmath.contracts.ProblemContract;
import com.zhaohengsun.learnmath.contracts.RewardContract;
import com.zhaohengsun.learnmath.contracts.TaskContract;
import com.zhaohengsun.learnmath.managers.TypedCursor;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class QueryBuilder<T> implements LoaderManager.LoaderCallbacks<Cursor> {

    public static interface IQueryListener<T> {

        public void handleResults(TypedCursor<T> results);

        public void closeResults();

    }
    String tag;
    Context context;
    Uri uri;
    int loaderID;
    IEntityCreator<T> creator;
    IQueryListener<T> listener;

    private QueryBuilder(String tag,
                         Context context,
                         Uri uri,
                         int loaderID,
                         IEntityCreator<T> creator,
                         IQueryListener<T> listener) {
        this.tag = tag;
        this.context = context;
        this.uri = uri;
        this.loaderID = loaderID;
        this.creator = creator;
        this.listener = listener;

    }

    //  complete the implementation of this

    public static <T> void executeQuery(String tag,
                                        Activity context, Uri uri,
                                        int loaderID,
                                        IEntityCreator<T> creator,
                                        IQueryListener<T> listener) {


        Log.d("current", "executeQuery:start ");
        QueryBuilder<T> qb = new QueryBuilder<T>(tag, context,
                uri, loaderID,
                creator, listener);
        LoaderManager lm = context.getLoaderManager();
        lm.initLoader(loaderID, null, qb);
    }

    public static <T> void executeQuery(String tag, Activity context,
                                        Uri uri,int loaderID, String[] projection,
                                        String selection, String[] selectionArgs,
                                        IEntityCreator<T> creator,
                                        IQueryListener<T> listener) {
        Log.d("current", "executeQuery:start ");

        QueryBuilder<T> qb = new QueryBuilder<T>(tag, context,
                uri, loaderID,
                creator, listener);
        LoaderManager lm = context.getLoaderManager();
        lm.initLoader(loaderID, null, qb);
    }
    public static <T> void reexecuteQuery(String tag, Activity context,
                                          Uri uri,int loaderID, String[] projection,
                                          String selection, String[] selectionArgs,
                                          IEntityCreator<T> creator,
                                          IQueryListener<T> listener) {
        Log.d("current", "reexecuteQuery:Restart ");

        QueryBuilder<T> qb = new QueryBuilder<T>(tag, context,
                uri, loaderID,
                creator, listener);
        LoaderManager lm = context.getLoaderManager();
        lm.restartLoader(loaderID, null, qb);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        // context.getContentResolver().query()
        switch (loaderID){

            case 1:

                String[]  projection = new String[]{ProblemContract.ID,ProblemContract.HEAD, ProblemContract.BODY, ProblemContract.SCORE, ProblemContract.DIFFICULTY,ProblemContract.CATEGORY};


                return new CursorLoader(context,ProblemContract.CONTENT_URI
                        , projection, null, null, null);

            case 2:

                 projection = new String[]{ChildrenContract.ID,ChildrenContract.NAME,ChildrenContract.SCHOOLTYPE};

                return new CursorLoader(context,ChildrenContract.CONTENT_URI
                        , projection, null, null, null);


            case 3:
                projection =    new String[]{TaskContract.ID,TaskContract.STUDENTNAME, TaskContract.PROBLEMS,TaskContract.ISFINISHED,TaskContract.TIMELIMIT};
                return new CursorLoader(context,TaskContract.CONTENT_URI
                        , projection, null, null, null);



            case 4:
                projection =    new String[]{RewardContract.ID,RewardContract.REWARD_NAME,RewardContract.REWARD_POINTS,RewardContract.RECIPIENT_NAME};
                return new CursorLoader(context, RewardContract.CONTENT_URI
                        , projection, null, null, null);
        }


        return null;
    }

    public void onLoadFinished(Loader<Cursor> loader,
                               Cursor cursor) {
        if (loader.getId() == loaderID) {
            listener
                    .handleResults(new TypedCursor<T>(cursor, creator));

        } else {
            throw new IllegalStateException("Unexpected loader callback");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == loaderID) {

            listener.closeResults();
        } else {
            throw new IllegalStateException
                    ("Unexpected loader callback");
        }

    }
}


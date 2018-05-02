package com.zhaohengsun.learnmath.async;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class SimpleQueryBuilder<T> implements IContinue<Cursor>{

    private IEntityCreator<T> helper;
    private ISimpleQueryListener<T> listener;

    private SimpleQueryBuilder(
            IEntityCreator<T> helper,
            ISimpleQueryListener<T> listener) {
        this.helper = helper;
        this.listener = listener;
    }


    public interface ISimpleQueryListener<T> {

        public void handleResults(List<T> results);

    }

    //  Complete the implementation of this

    @Override
    public void kontinue(Cursor value) {
        // complete this

        List<T> instances = new ArrayList<T>();
        if (value.moveToFirst()) {
            do {
                T instance = helper.create(value);
                instances.add(instance);
            } while (value.moveToNext());
        }
        value.close();
        listener.handleResults(instances);
    }


    public static <T> void executeQuery(Activity context,
                                        Uri uri,
                                        IEntityCreator<T> helper,
                                        ISimpleQueryListener<T> listener) {
        SimpleQueryBuilder<T> qb =
                new SimpleQueryBuilder<T>(helper, listener);
        AsyncContentResolver resolver = new
                AsyncContentResolver(context.getContentResolver());
        resolver.queryAsync(uri, null, null, null, null, qb);
    }


    public static <T> void executeQuery(Activity context,
                                        Uri uri,
                                        String[] projection,
                                        String selection, String[] selectionArgs,
                                        IEntityCreator<T> helper,
                                        ISimpleQueryListener<T> listener) {
        SimpleQueryBuilder<T> qb =
                new SimpleQueryBuilder<T>(helper, listener);
        AsyncContentResolver resolver = new
                AsyncContentResolver(context.getContentResolver());
        resolver.queryAsync(uri, projection, selection, selectionArgs, null, qb);
    }


}

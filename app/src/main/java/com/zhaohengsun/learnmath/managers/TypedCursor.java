package com.zhaohengsun.learnmath.managers;

import android.database.ContentObserver;
import android.database.Cursor;

import com.zhaohengsun.learnmath.async.IEntityCreator;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class TypedCursor<T> {

    private Cursor cursor;

    private IEntityCreator<T> creator;

    public int getCount() {
        return cursor.getCount();
    }

    public boolean moveToFirst() {
        return cursor.moveToFirst();
    }

    public boolean moveToNext() {
        return cursor.moveToNext();
    }

    public T getEntity() {
        return creator.create(cursor);
    }

    public Cursor getCursor() {
        return cursor;
    }

    public T create(Cursor cursor) {
        return creator.create(cursor);
    }

    public void close() {
        cursor.close();
    }

    public void registerContentObserver(ContentObserver observer) {
        cursor.registerContentObserver(observer);
    }

    public void unregisterContentObserver(ContentObserver observer) {
        cursor.unregisterContentObserver(observer);
    }

    public TypedCursor(Cursor cursor, IEntityCreator<T> creator) {
        this.cursor = cursor;
        this.creator = creator;
    }

}

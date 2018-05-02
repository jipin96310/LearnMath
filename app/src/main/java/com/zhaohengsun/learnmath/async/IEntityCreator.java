package com.zhaohengsun.learnmath.async;

import android.database.Cursor;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public interface  IEntityCreator<T> {

    public T create(Cursor cursor);

}


package com.zhaohengsun.learnmath.util;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.R;

/**
 * Created by Zhaoheng Sun on 2018/2/27.
 */

public class TestAdapter extends ResourceCursorAdapter {

    protected final static int ROW_LAYOUT = R.layout.test_item;

    public TestAdapter(Context context, Cursor cursor) {
        super(context, ROW_LAYOUT, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView headLine = (TextView) view.findViewById(R.id.test_head);

        TextView categoryLine = (TextView)view.findViewById(R.id.test_category);

        TextView scoreLine = (TextView)view.findViewById(R.id.test_score);
        // TextView authorLine = (TextView) view.findViewById(android.R.id.text2);

        EditText inputLine = (EditText)view.findViewById(R.id.your_answer);
        headLine.setText( cursor.getString(cursor.getColumnIndex("head")));
        categoryLine.setText(cursor.getString(cursor.getColumnIndex("category")));
        scoreLine.setText(cursor.getString(cursor.getColumnIndex("score")));

        //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



    }
}


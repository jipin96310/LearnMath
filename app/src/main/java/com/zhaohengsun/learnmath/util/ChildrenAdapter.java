package com.zhaohengsun.learnmath.util;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.R;

/**
 * Created by Zhaoheng Sun on 2018/2/25.
 */

public class ChildrenAdapter extends ResourceCursorAdapter {

    protected final static int ROW_LAYOUT = R.layout.child_manage_item;

    public ChildrenAdapter(Context context, Cursor cursor) {
        super(context, ROW_LAYOUT, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameLine = (TextView) view.findViewById(R.id.child_name);


        TextView schoolLine = (TextView)view.findViewById(R.id.child_school);

        //TextView difficultyLine = (TextView)view.findViewById(R.id.problem_difficulty);
        // TextView authorLine = (TextView) view.findViewById(android.R.id.text2);

        nameLine.setText( cursor.getString(cursor.getColumnIndex("name")));
        schoolLine.setText(cursor.getString(cursor.getColumnIndex("schooltype")));
       // difficultyLine.setText(cursor.getString(cursor.getColumnIndex("difficulty")));

        //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



    }
}

package com.zhaohengsun.learnmath.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.R;

/**
 * Created by Zhaoheng Sun on 2018/2/26.
 */

public class TaskAdapter extends ResourceCursorAdapter {

    protected final static int ROW_LAYOUT = R.layout.task_list_item;

    public TaskAdapter(Context context, Cursor cursor) {
        super(context, ROW_LAYOUT, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView isFinishedLine = (TextView) view.findViewById(R.id.isfinished_text);

        TextView studentnameLine = (TextView)view.findViewById(R.id.studentname_text);

        TextView timelimitLine = (TextView)view.findViewById(R.id.timelimit_text);
        // TextView authorLine = (TextView) view.findViewById(android.R.id.text2);
        String isfinished =   cursor.getString(cursor.getColumnIndex("isfinished"));

        if( isfinished.equals("true")) {
            isFinishedLine.setText("Completed");
            isFinishedLine.setTextColor(Color.parseColor("#32CD32"));

        }
        else {
            isFinishedLine.setText("Not Completed");
            isFinishedLine.setTextColor(Color.parseColor("#FF3030"));
        }
        studentnameLine.setText(cursor.getString(cursor.getColumnIndex("studentname")));
        timelimitLine.setText(cursor.getLong(cursor.getColumnIndex("timelimit"))+" Min");

        //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



    }
}

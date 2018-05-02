package com.zhaohengsun.learnmath.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.R;

/**
 * Created by Zhaoheng Sun on 2018/2/26.
 */

public class ChildModeAdapter extends ResourceCursorAdapter {

    protected final static int ROW_LAYOUT = R.layout.child_mode_item;

    public ChildModeAdapter(Context context, Cursor cursor) {
        super(context, ROW_LAYOUT, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        RelativeLayout cv = (RelativeLayout)view.findViewById(R.id.child_mode_card);
        TextView nameLine = (TextView) view.findViewById(R.id.childname_text);
        TextView articleLine = (TextView)view.findViewById(R.id.article_text);
        TextView remainLine = (TextView) view.findViewById(R.id.remain_test);
        TextView testLine = (TextView) view.findViewById(R.id.textView8);
        //TextView difficultyLine = (TextView)view.findViewById(R.id.problem_difficulty);
        // TextView authorLine = (TextView) view.findViewById(android.R.id.text2);

        nameLine.setText( cursor.getString(cursor.getColumnIndex("name")));
        long id = cursor.getLong(cursor.getColumnIndex("_id"));


        if(id == 1) {
            nameLine.setTextColor(Color.parseColor("#F0FFF0"));
            cv.setBackgroundColor(Color.parseColor("#FFC125"));
            remainLine.setText("2");
            testLine.setText("tests.");
        }else if(id == 2){
            nameLine.setTextColor(Color.parseColor("#EED5D2"));
            cv.setBackgroundColor(Color.parseColor("#A2CD5A"));

            remainLine.setText("0");
            testLine.setText("test.");


        }else{
            nameLine.setTextColor(Color.parseColor("#DCDCDC"));
            cv.setBackgroundColor(Color.parseColor("#8EE5EE"));
            testLine.setText("test.");

        }
       // schoolLine.setText(cursor.getString(cursor.getColumnIndex("schooltype")));
        // difficultyLine.setText(cursor.getString(cursor.getColumnIndex("difficulty")));

        //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



    }
}

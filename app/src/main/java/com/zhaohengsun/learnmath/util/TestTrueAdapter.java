package com.zhaohengsun.learnmath.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.R;

import java.util.ArrayList;

/**
 * Created by Zhaoheng Sun on 2018/4/27.
 */

public class TestTrueAdapter extends ResourceCursorAdapter {

    protected final static int ROW_LAYOUT = R.layout.test_true_item;
    ArrayList<String> ans;
    public TestTrueAdapter(Context context, Cursor cursor, ArrayList<String> answer) {
        super(context, ROW_LAYOUT, cursor, 0);
        this.ans = answer;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView headLine = (TextView) view.findViewById(R.id.test_head);

        TextView categoryLine = (TextView)view.findViewById(R.id.test_category);

        TextView scoreLine = (TextView)view.findViewById(R.id.test_score);
        // TextView authorLine = (TextView) view.findViewById(android.R.id.text2);

        TextView yourAnswer = (TextView)view.findViewById(R.id.your_final_answer);
        TextView correctAnswer = (TextView)view.findViewById(R.id.correct_answer);

        String your_ans = ans.get(cursor.getPosition());
        String correct_answer = cursor.getString(cursor.getColumnIndex("body"));


        headLine.setText( cursor.getString(cursor.getColumnIndex("head")));
        categoryLine.setText(cursor.getString(cursor.getColumnIndex("category")));
        scoreLine.setText(cursor.getString(cursor.getColumnIndex("score")));
        yourAnswer.setText("Your Answer: "+ your_ans);
        correctAnswer.setText("Correct Answer: " + correct_answer);

        if(your_ans.equals(correct_answer))yourAnswer.setTextColor(Color.parseColor("#32CD32"));
        else yourAnswer.setTextColor(Color.parseColor("#EE0000"));


        //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



    }
}

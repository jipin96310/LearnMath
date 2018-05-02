package com.zhaohengsun.learnmath.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.R;
import com.zhaohengsun.learnmath.contracts.ProblemContract;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class ProblemAdapter extends ResourceCursorAdapter {

    protected final static int ROW_LAYOUT = R.layout.problem_item;

    public ProblemAdapter(Context context, Cursor cursor) {
        super(context, ROW_LAYOUT, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView headLine = (TextView) view.findViewById(R.id.problem_head);

        TextView categoryLine = (TextView)view.findViewById(R.id.problem_category);

        TextView difficultyLine = (TextView)view.findViewById(R.id.problem_difficulty);

        TextView truedifficultyLine = (TextView)view.findViewById(R.id.true_difficulty);

       // TextView authorLine = (TextView) view.findViewById(android.R.id.text2);

       headLine.setText( cursor.getString(cursor.getColumnIndex("head")));
        categoryLine.setText(cursor.getString(cursor.getColumnIndex("category")));
        difficultyLine.setText(cursor.getString(cursor.getColumnIndex(ProblemContract.SCORE))+" Points ");


        String diff = cursor.getString(cursor.getColumnIndex(ProblemContract.DIFFICULTY));

        truedifficultyLine.setText("Difficulty: " + diff);

        switch (diff){
            case "1":
                truedifficultyLine.setTextColor(Color.parseColor("#FFDAB9"));
                break;
            case "2":
                truedifficultyLine.setTextColor(Color.parseColor("#9ACD32"));
                break;

            case "3":
                truedifficultyLine.setTextColor(Color.parseColor("#63B8FF"));
                break;

            case "4":
                truedifficultyLine.setTextColor(Color.parseColor("#8B4789"));
                break;
            case "5":
                truedifficultyLine.setTextColor(Color.parseColor("#FF7F00"));
                break;
            default:

                break;


        }



      //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



    }
}

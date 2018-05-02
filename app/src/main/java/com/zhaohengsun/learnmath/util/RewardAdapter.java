package com.zhaohengsun.learnmath.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.R;

/**
 * Created by Zhaoheng Sun on 2018/4/26.
 */

public class RewardAdapter extends ResourceCursorAdapter {

    protected final static int ROW_LAYOUT = R.layout.reward_item;

    public RewardAdapter(Context context, Cursor cursor) {
        super(context, ROW_LAYOUT, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameLine = (TextView) view.findViewById(R.id.reward_name);

        TextView pointsLine = (TextView)view.findViewById(R.id.reward_points);

        TextView redeemLine = (TextView)view.findViewById(R.id.redeem_name);


        // TextView authorLine = (TextView) view.findViewById(android.R.id.text2);

        nameLine.setText( cursor.getString(cursor.getColumnIndex("reward_name")));
        pointsLine.setText(cursor.getString(cursor.getColumnIndex("reward_points")));

       String redeem_name =  cursor.getString(cursor.getColumnIndex("recipient_name"));
        redeemLine.setTextSize(16);
       if(!redeem_name.equals("?")){

           redeemLine.setTextColor(Color.parseColor("#32CD32"));
           redeemLine.setText("Redeemed by: " + redeem_name);
       }else{

           redeemLine.setTextColor(Color.parseColor("#EE0000"));
           redeemLine.setText("Not Redeemed.");
       }

        //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



    }
}
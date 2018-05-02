package com.zhaohengsun.learnmath;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.zhaohengsun.learnmath.async.ChildModeTaskActivity;
import com.zhaohengsun.learnmath.contracts.ChildrenContract;

public class ChildeModeMainActivity extends AppCompatActivity {


    private String cur_child_name = "";
    private long cur_child_id = -1;
    private CardView tutorial_card;
    private Cursor cur_c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_childe_mode_main);

        Intent it = getIntent();
        String name = it.getStringExtra("name");
        long id = it.getLongExtra("id",-1);
        cur_child_name = name;
        cur_child_id = id;


        tutorial_card = findViewById(R.id.tutorial_card);
        tutorial_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChildeModeMainActivity.this, MathTutorialActivity.class);



                startActivity(intent);
            }
        });

        TextView tv = (TextView)findViewById(R.id.current_points);
        ContentResolver cr = getContentResolver();
        cur_c = cr.query(ChildrenContract.CONTENT_URI(cur_child_id),null,null,null,null);
        if(cur_c.moveToFirst())
        tv.setText(cur_c.getLong(cur_c.getColumnIndex("points"))+"");




    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView tv = (TextView)findViewById(R.id.current_points);
        ContentResolver cr = getContentResolver();
        cur_c = cr.query(ChildrenContract.CONTENT_URI(cur_child_id),null,null,null,null);
        if(cur_c.moveToFirst())
            tv.setText(cur_c.getLong(cur_c.getColumnIndex("points"))+"");
    }

    public void onTest(View v){
        Intent intent = new Intent(ChildeModeMainActivity.this, ChildModeTaskActivity.class);


        intent.putExtra("name",cur_child_name);
       intent.putExtra("cur_child_id",cur_child_id);

        startActivity(intent);

    }

    public void onReward(View v){
        Intent intent = new Intent(ChildeModeMainActivity.this, ChildRewardActivity.class);

        intent.putExtra("id",cur_child_id);

        startActivity(intent);


    }





}

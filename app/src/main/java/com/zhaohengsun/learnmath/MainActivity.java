package com.zhaohengsun.learnmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhaohengsun.learnmath.entities.MainItem;
import com.zhaohengsun.learnmath.util.MainAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private MainAdapter ma;
    private ArrayList<MainItem> mainlist;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.mainList);
        mainlist = new ArrayList<MainItem>();
        MainItem ma1 = new MainItem(1,"I want to teach math.","Teacher Mode");
        MainItem ma2 = new MainItem(2,"I want to learn math.","Student Mode");
        mainlist.add(ma1);
        mainlist.add(ma2);

        ma = new MainAdapter(this,mainlist);


        lv.setAdapter(ma);

        lv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i){

            case 0:
                Intent pi = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(pi);

                break;

            case 1:
                Intent ci = new Intent(MainActivity.this,ChildModeActivity.class);
                startActivity(ci);
                break;

        }


    }

    public void onFeedBack(View v){

        Intent pi = new Intent(MainActivity.this,FeedBackActivity.class);
        startActivity(pi);

    }

}

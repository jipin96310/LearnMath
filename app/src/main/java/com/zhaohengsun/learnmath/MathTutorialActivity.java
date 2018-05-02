package com.zhaohengsun.learnmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhaohengsun.learnmath.entities.MainItem;
import com.zhaohengsun.learnmath.util.TutorialAdapter;

import java.util.ArrayList;

public class MathTutorialActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private TutorialAdapter ma;
    private ArrayList<MainItem> mainlist;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_tutorial);



        lv = (ListView)findViewById(R.id.tutorial_list);
        mainlist = new ArrayList<MainItem>();
        MainItem ma1 = new MainItem(1,"Grade 1 Tutorial","1");
        MainItem ma2 = new MainItem(2,"Grade 2 Tutorial","2");
        MainItem ma3 = new MainItem(3,"Grade 3 Tutorial","3");
        MainItem ma4 = new MainItem(4,"Grade 4 Tutorial","4");
        MainItem ma5 = new MainItem(5,"Grade 5 Tutorial","5");


        mainlist.add(ma1);
        mainlist.add(ma2);
        mainlist.add(ma3);
        mainlist.add(ma4);
        mainlist.add(ma5);


        ma = new TutorialAdapter(this,mainlist);


        lv.setAdapter(ma);

        lv.setOnItemClickListener(this);




    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent pi = new Intent(MathTutorialActivity.this,TutorialReadActivity.class);
                startActivity(pi);





    }
}

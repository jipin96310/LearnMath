package com.zhaohengsun.learnmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);




    }



    public void onProblem(View v){
        Intent intent = new Intent(ParentActivity.this,ProblemManage.class);
        startActivity(intent);


    }

    public void onChildren(View v){
        Intent intent = new Intent(ParentActivity.this,ChildrenManageActivity.class);
        startActivity(intent);


    }

    public void onTask(View v){
        Intent intent = new Intent(ParentActivity.this,TasksActivity.class);
        startActivity(intent);


    }

    public void onReward(View v){

        Intent intent = new Intent(ParentActivity.this,AddRewardActivity.class);
        startActivity(intent);
    }

}

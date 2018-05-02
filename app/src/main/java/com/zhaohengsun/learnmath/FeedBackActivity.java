package com.zhaohengsun.learnmath;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FeedBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        Button submit_button = (Button)findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler2=new Handler(Looper.getMainLooper());
                handler2.post(  new Runnable(){
                    public void run(){
                        Toast.makeText(getApplicationContext(),"Your feedback has been received!Thank you!",Toast.LENGTH_SHORT);
                    }
                });


            }
        });
    }

    public void onToast(View v){


    }


}

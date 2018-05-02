package com.zhaohengsun.learnmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaohengsun.learnmath.entities.Reward;

public class AddRewardAddActivity extends AppCompatActivity {
    static final private int ADD_RESULT = 1;
    public static final String REWARD_RESULT_KEY = "reward_result";
    private TextView reward_name_add;
    private TextView reward_points_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reward_add);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //  provide ADD and CANCEL options

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);

        Button addnewButton = (Button)menu.findItem(R.id.add_add).getActionView();

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //
        // ADD:

        //
        switch(item.getItemId()) {


            case R.id.add_add:
                Reward reward = addReward();
                if(reward!=null){
                    Intent intent = getIntent();

                    intent.putExtra(REWARD_RESULT_KEY,reward);

                    AddRewardAddActivity.this.setResult(ADD_RESULT, intent);

                    AddRewardAddActivity.this.finish();
                }
                else{

                    Toast.makeText(this,"Please fill all the details and try again",Toast.LENGTH_SHORT).show();
                }

                break;


            default:
        }
        return false;
    }

    public Reward addReward(){
        //  Just build a Book object with the search criteria and return that.
        reward_name_add =(TextView)findViewById(R.id.reward_name_add);
        reward_points_add = (TextView)findViewById(R.id.reward_points_add);


        String name = reward_name_add.getText().toString();
        String points = reward_points_add.getText().toString();



        if(name!=""&&points!="") {



            Reward r = new Reward(name,Long.valueOf(points));


            return r;
        }

        return null;
    }


}

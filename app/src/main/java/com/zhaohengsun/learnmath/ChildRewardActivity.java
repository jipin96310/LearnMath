package com.zhaohengsun.learnmath;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaohengsun.learnmath.async.QueryBuilder;
import com.zhaohengsun.learnmath.contracts.ChildrenContract;
import com.zhaohengsun.learnmath.contracts.RewardContract;
import com.zhaohengsun.learnmath.entities.Children;
import com.zhaohengsun.learnmath.entities.Reward;
import com.zhaohengsun.learnmath.managers.RewardManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;
import com.zhaohengsun.learnmath.util.RewardAdapter;
public class ChildRewardActivity extends AppCompatActivity implements QueryBuilder.IQueryListener<Reward>{
    private String cur_child_name = "";
    private ListView lv;
    private RewardManager rewardManager;
    private RewardAdapter rewardAdapter;
    private long child_points = 0;
    TextView child_mode_points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_reward);


        rewardAdapter = new RewardAdapter(this,null);
        Intent it = getIntent();
        final long id = it.getLongExtra("id",-1);
        Log.d("REWARD", "onCreate: "+id);


        final ContentResolver cr = getContentResolver();
        final Cursor c = cr.query(ChildrenContract.CONTENT_URI(id),null,null,null,null);




        if(c.moveToFirst()) {
            child_mode_points = (TextView) findViewById(R.id.child_reward_points);

            child_points = c.getLong(c.getColumnIndex("points"));
            child_mode_points.setText("Your Points: " + String.valueOf(child_points));


        }

        lv = (ListView) findViewById(R.id.child_reward_list);

        lv.setAdapter(rewardAdapter);


        rewardManager = new RewardManager(this);
        rewardManager.getAllRewardsAsync(this);

       final  AlertDialog.Builder builder=new Builder(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int posit, final long ld) {

               Cursor ci = rewardAdapter.getCursor();
               ci.moveToPosition(posit);
              final long reward_points = ci.getLong(ci.getColumnIndex("reward_points"));



                //DIALOG part
                Log.d("rewardpos", "onClick: "+posit);
                builder.setIcon(R.mipmap.ic_launcher);//
                builder.setTitle("Message");
                builder.setMessage("Do you want to use your points to exchange this gift?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {




                        final Cursor current_child = cr.query(ChildrenContract.CONTENT_URI(id),null,null,null,null);//find the latest child info
                        current_child.moveToFirst();
                        Children child_copy = new Children(current_child);

                        if(reward_points<=child_copy.points) {

                            child_copy.points -= reward_points;

                            ContentValues cvinnner = new ContentValues();
                            child_copy.writeToProvider(cvinnner);
                            cr.update(ChildrenContract.CONTENT_URI(id), cvinnner, null, null);

                            //update reward!!!
                            ContentValues cvreward = new ContentValues();
                            Cursor reward_cursor =  rewardAdapter.getCursor();
                            reward_cursor.moveToPosition(posit);
                            Reward cur_reward = new Reward(reward_cursor);
                            cur_reward.recipientNanme = child_copy.name;
                            cur_reward.writeToProvider(cvreward);
                            Log.d("RewardActivity", "onClick: "+posit + "+"+cur_reward.recipientNanme);
                            cr.update(RewardContract.CONTENT_URI(posit+1), cvreward, null, null);
                            //rewardAdapter.notifyDataSetChanged();
                            rewardManager.getAllRewardsAsync(ChildRewardActivity.this);

                            child_mode_points.setText("Your Points: " + child_copy.points);


                   Toast.makeText(ChildRewardActivity.this, "Congratulation!You can now ask the gift from your teacher!", Toast.LENGTH_SHORT).show();

                        }else if(reward_points>child_copy.points){

                            Toast.makeText(ChildRewardActivity.this, "You don't have enough points,keep working!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });
                AlertDialog b=builder.create();
                b.show();





                //if(reward_points>child_points) Toast.makeText(getApplicationContext(),"No Enough Points, Keep Working!",Toast.LENGTH_SHORT);
              // else if(reward_points<=child_points){


             //  }



            }
        });



    }


    public void onDialog(View view)
    {
        //
    }

    @Override
    public void handleResults(TypedCursor<Reward> results) {
        //  update the adapter

        // Log.d(TAG, "handleResults: ");
        if (results.moveToFirst()) {


            rewardAdapter.swapCursor(null);
            rewardAdapter.swapCursor(results.getCursor());

        }else {

            rewardAdapter.swapCursor(null);

        }

        rewardAdapter.notifyDataSetChanged();
    }

    @Override
    public void closeResults() {
        //  update the adapter

    }

}

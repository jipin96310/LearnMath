package com.zhaohengsun.learnmath;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.zhaohengsun.learnmath.async.QueryBuilder;
import com.zhaohengsun.learnmath.contracts.ChildrenContract;
import com.zhaohengsun.learnmath.contracts.ProblemContract;
import com.zhaohengsun.learnmath.contracts.TaskContract;
import com.zhaohengsun.learnmath.entities.Children;
import com.zhaohengsun.learnmath.entities.Problem;
import com.zhaohengsun.learnmath.entities.Task;
import com.zhaohengsun.learnmath.managers.ProblemManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;
import com.zhaohengsun.learnmath.util.TestAdapter;
import com.zhaohengsun.learnmath.util.TestTrueAdapter;

import java.util.ArrayList;

public class ChildTestActivity extends AppCompatActivity implements QueryBuilder.IQueryListener<Problem> {
    static final private int CONFRIM_RES = 1;
    private ListView lv;
    private ListView truelv;
    private ProblemManager problemManager;
    private TestAdapter testAdapter;
    private TestTrueAdapter testTrueAdapter;
    static final private String PROBLEMS  = "PROBLEMS";
    static final private String TASK_ID  = "task_id";
    private int selectPosition = -1;
    private Problem selectedProblem;
    private long cur_id;

    static final private String TIME_LIMIT = "timelimit";
    public static String CONFIRM_RES = "confirm_res";
    static final private int ADD_RESULT = 1;
    public CountDownTimer timer;
    private Task cur_task;
    private long child_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_test);

        Intent it = getIntent();
        String cur_problems = it.getStringExtra(PROBLEMS);
         cur_id = it.getLongExtra(TASK_ID,0);
        final long time_limit = it.getLongExtra(TIME_LIMIT,0);
        cur_task = it.getParcelableExtra("cur_task");
        child_id = it.getLongExtra("cur_child_id",-1);

        Log.d("curid", "onCreate: "+ cur_id);
        Log.d("problems", "onCreate: "+ cur_problems);
        Log.d("child_id", "onCreate: "+ child_id);





            String[] problems = cur_problems.split("|");
            String[] args = new String[problems.length];


        StringBuilder sb = new StringBuilder();
        if (problems.length > 0) {
            sb.append(ProblemContract.ID);
            sb.append("=?");
            args[0] = problems[0].toString();
            for (int ix=1; ix<problems.length; ix++) {
                sb.append(" or ");
                sb.append(ProblemContract.ID);
                sb.append("=?");
                args[ix] = problems[ix];
            }
        }
        String select = sb.toString();





          ContentResolver cr = getContentResolver();
        String[]  projection = new String[]{ProblemContract.ID,ProblemContract.HEAD, ProblemContract.BODY, ProblemContract.SCORE, ProblemContract.DIFFICULTY,ProblemContract.CATEGORY};
       Cursor c = cr.query(ProblemContract.CONTENT_URI,projection,select,args,null);
      //  cproblem = c;

        testAdapter = new TestAdapter(this,c);


        lv = (ListView)findViewById(R.id.child_test_list);
        lv.setAdapter(testAdapter);

        final TextView tv = (TextView)findViewById(R.id.count_down_timer);

        timer  = new CountDownTimer(time_limit*60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                tv.setText(""+millisUntilFinished/(1000*60)+"M"+(millisUntilFinished/1000)%60+"S Left");
            }

            @Override
            public void onFinish() {
                tv.setText("Time Out");
            }
        }.start();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //  inflate a menu with ADD and CHECKOUT options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.confirm_menu, menu);

        Button addnewButton = (Button)menu.findItem(R.id.confirm_confirm).getActionView();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {

            //  ADD provide the UI for adding a book
            case R.id.confirm_confirm:
                Intent addIntent = getIntent();
                addIntent.putExtra(TASK_ID,cur_id);
                ChildTestActivity.this.setResult(CONFRIM_RES,addIntent);


                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setIcon(R.mipmap.ic_launcher);//
                builder.setTitle("Message");
                builder.setMessage("Do you want to submit this test?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                     final long score =  Oncal();


                       ActionMenuItemView bt = (ActionMenuItemView)findViewById(R.id.confirm_confirm) ;

                       ContentResolver cr = getContentResolver();
                        ContentValues cv = new ContentValues();
                        cur_task.isFinished = "true";
                        cur_task.writeToProvider(cv);

                        cr.update(TaskContract.CONTENT_URI(cur_id),cv,null,null);


                        ContentResolver cr2 = getContentResolver();
                        Cursor child_cursor = cr2.query(ChildrenContract.CONTENT_URI(child_id),null,null,null,null);

                        if(child_cursor.moveToFirst()) {
                            Children cur_child = new Children(child_cursor);

                            cur_child.points += score;

                            ContentValues cv2 = new ContentValues();
                            cur_child.writeToProvider(cv2);
                            cr2.update(ChildrenContract.CONTENT_URI(child_id), cv2, null, null);

                        }

                       //bt.setVisibility(View.GONE);
                        bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = getIntent();

                                intent.putExtra("score",score);

                                ChildTestActivity.this.setResult(ADD_RESULT, intent);

                                ChildTestActivity.this.finish();

                            }
                        });
                       // Button addnewButton = (Button)menu.findItem(R.id.confirm_confirm).getActionView();





                        //  cproblem = c;









                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                });
                AlertDialog b=builder.create();
                b.show();




                break;


        }
        return false;
    }

    public long Oncal(){
        long total = 0;
        int po = 0;

       Cursor c_cur =  testAdapter.getCursor();
        ArrayList<String> as = new ArrayList<String>();


        if(c_cur.moveToFirst()){
          do{

               EditText et = (EditText) lv.getChildAt(po).findViewById(R.id.your_answer);
                String answer = et.getText().toString();
                as.add(answer);

              Log.d("ANswer", "Oncal: "+answer);
              Log.d("real", "Oncal: "+c_cur.getString(c_cur.getColumnIndex("body")));


                if(answer.equals(c_cur.getString(c_cur.getColumnIndex("body"))))
                    total+=c_cur.getLong(c_cur.getColumnIndex("score"));

              po++;
          }while(c_cur.moveToNext());





            testTrueAdapter = new TestTrueAdapter(ChildTestActivity.this,c_cur,as);
            lv = (ListView)findViewById(R.id.child_test_list);
            lv.setAdapter(testTrueAdapter);
            //lv.deferNotifyDataSetChanged();

          //  testAdapter = new TestTrueAdapter(this,c_cur);


          //  Log.d("LV", "Oncal: "+truelv.getAdapter().getCount());
          for(int i =0;i<testTrueAdapter.getCount();i++) {


            // tvg.setText("Your Answer:" + as.get(i));

          }


            timer.cancel();
            TextView tv = (TextView)findViewById(R.id.count_down_timer);
            tv.setText("Congratulation! You get " + total + " points!");
           tv.setTextSize(32);






        }

            return total;
    }




    @Override
    public void handleResults(TypedCursor<Problem> results) {
        //  update the adapter

        // Log.d(TAG, "handleResults: ");
        if (testTrueAdapter == null) {
            if (results.moveToFirst()) {


                testAdapter.swapCursor(null);
                testAdapter.swapCursor(results.getCursor());

            } else {

                testAdapter.swapCursor(null);

            }

            testAdapter.notifyDataSetChanged();
        }else{
            if (results.moveToFirst()) {


                testTrueAdapter.swapCursor(null);
                testTrueAdapter.swapCursor(results.getCursor());

            } else {

                testTrueAdapter.swapCursor(null);

            }

            testTrueAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public void closeResults() {
        //  update the adapter

    }

}

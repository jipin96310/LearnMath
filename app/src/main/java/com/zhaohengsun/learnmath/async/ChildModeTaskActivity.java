package com.zhaohengsun.learnmath.async;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhaohengsun.learnmath.ChildTestActivity;
import com.zhaohengsun.learnmath.R;
import com.zhaohengsun.learnmath.contracts.TaskContract;
import com.zhaohengsun.learnmath.entities.Task;
import com.zhaohengsun.learnmath.managers.TasksManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;
import com.zhaohengsun.learnmath.util.TaskAdapter;

public class ChildModeTaskActivity extends AppCompatActivity implements QueryBuilder.IQueryListener<Task>,AdapterView.OnItemClickListener{

    private ListView lv;
    private TasksManager tasksManager;
    private TaskAdapter taskAdapter;
    static final private int START_REQUEST  = 1;
    static final private String PROBLEMS  = "PROBLEMS";
    static final private String TASK_ID  = "task_id";
    static final private String TIME_LIMIT = "timelimit";
    private long child_id;
    private Cursor c;
    private String student_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_mode_task);

        lv = (ListView)findViewById(R.id.child_mode_task_list);

        Intent it = getIntent();
        String name = it.getStringExtra("name");
        student_name = name;
        child_id = it.getLongExtra("cur_child_id",-1);

        Log.d("name", "onCreate: "+name);

        String[]  projection = new String[]{TaskContract.ID,TaskContract.STUDENTNAME, TaskContract.PROBLEMS,TaskContract.ISFINISHED,TaskContract.TIMELIMIT};
        ContentResolver cr = getContentResolver();

        String[] select = new String[]{name};


         c = cr.query(TaskContract.CONTENT_URI,projection,TaskContract.STUDENTNAME+"=?",select,null,null);

        taskAdapter = new TaskAdapter(this,c);
        lv.setAdapter(taskAdapter);

        lv.setOnItemClickListener(this);




    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Cursor temp = taskAdapter.getCursor();

        temp.moveToPosition(i);


        Task cur_task = new Task(temp);

        String problems = temp.getString(temp.getColumnIndex("problems"));
        long taskId = temp.getLong(temp.getColumnIndex("_id"));
        long timeLimit = temp.getLong(temp.getColumnIndex("timelimit"));


        Intent ittemp = new Intent(ChildModeTaskActivity.this, ChildTestActivity.class);
        ittemp.putExtra(PROBLEMS,problems);
        ittemp.putExtra(TASK_ID,taskId);
        ittemp.putExtra(TIME_LIMIT,timeLimit);
        ittemp.putExtra("cur_child_id",child_id);

        ittemp.putExtra("cur_task",cur_task);

        startActivityForResult(ittemp,START_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        // Use ADD_REQUEST and CHECKOUT_REQUEST codes to distinguish the cases.
        switch(requestCode) {
            case START_REQUEST:
                // ADD: add the book that is returned to the shopping cart.
                //  It is okay to do this on the main thread for BookStoreWithContentProvider

                //

                if(resultCode == START_REQUEST) {
                    Bundle data = intent.getExtras();
                    long score = data.getLong("score");
                    Log.d(score+"get", "onActivityResult: ");
                   // taskAdapter.notifyDataSetChanged();
                   // Toast.makeText(getApplicationContext(),score+"get",Toast.LENGTH_SHORT);
                    String[]  projection = new String[]{TaskContract.ID,TaskContract.STUDENTNAME, TaskContract.PROBLEMS,TaskContract.ISFINISHED,TaskContract.TIMELIMIT};
                    ContentResolver cr = getContentResolver();

                    String[] select = new String[]{student_name};


                    c = cr.query(TaskContract.CONTENT_URI,projection,TaskContract.STUDENTNAME+"=?",select,null,null);
                    taskAdapter.swapCursor(c);
                    taskAdapter.notifyDataSetChanged();


                }

                break;
            // TODO

        }

    }

    @Override
    public void handleResults(TypedCursor<Task> results) {
        //  update the adapter
        Cursor c = results.getCursor();


        Log.d("child_mode_task", "handleResults: "+results.getCursor().getCount());
        //
        if (results.moveToFirst()) {

            taskAdapter.swapCursor(null);
            taskAdapter.swapCursor(results.getCursor());

        }else {

            taskAdapter.swapCursor(null);

        }

        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void closeResults() {
        //  update the adapter

    }



}

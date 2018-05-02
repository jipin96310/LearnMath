package com.zhaohengsun.learnmath;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.async.QueryBuilder;
import com.zhaohengsun.learnmath.contracts.ProblemContract;
import com.zhaohengsun.learnmath.entities.Problem;
import com.zhaohengsun.learnmath.managers.ProblemManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskProblemActivity extends AppCompatActivity implements QueryBuilder.IQueryListener<Problem> {
    static final private int ADD_REQUEST = 1;
    private ListView lv;
    private ProblemManager problemManager;
    private TaskProblemAdapter taskProblemAdapter;
    private int selectPosition = -1;
    private Problem selectedProblem;
    private static   HashMap<Integer, Boolean> isSelected;
    private Cursor cacheC;
    private static ArrayList<String> pId;
    public static String CONFIRM_RES = "condirm_res";
    static final private int ADD_RESULT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_problem);



        taskProblemAdapter = new TaskProblemAdapter(this,null);

        lv = (ListView)findViewById(R.id.task_problem_list);
        lv.setAdapter(taskProblemAdapter);


        problemManager = new ProblemManager(this);
        problemManager.getAllBooksAsync(this);
      cacheC = taskProblemAdapter.getCursor();
        pId = new ArrayList<String>();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               selectPosition = position;
                taskProblemAdapter.notifyDataSetChanged();
                Cursor c = taskProblemAdapter.getCursor();
                c.moveToPosition(position);

               // selectedProblem = new Problem(c);
                 if(isSelected.get(position)==false){isSelected.put(position,true);


                     pId.add(String.valueOf(c.getLong(c.getColumnIndex("_id"))));

                     for(String cur:pId){
                         Log.d("curPid", cur+"");

                     }

                 }
                 else {


                     isSelected.put(position,false);

                     for(int i =0;i<pId.size();i++){
                        if(pId.get(i).equals( String.valueOf(c.getLong(c.getColumnIndex("_id")))))pId.remove(i);

                     }


                     for(String cur:pId){
                         Log.d("curPid", cur+"");

                     }

                 }


                //  Log.d("addtask", "onItemClick: "+selectedChild.name);
            }
        });


    }


    public static class ViewHolder {
        TextView tv;
        CheckBox cb;
    }




    public class TaskProblemAdapter extends ResourceCursorAdapter {
        Context context;


        LayoutInflater mInflater;
        protected final static int ROW_LAYOUT = R.layout.task_problem_item;

        public TaskProblemAdapter(Context context, Cursor cursor) {
            super(context, ROW_LAYOUT, cursor, 0);
            this.context = context;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            isSelected = new HashMap<Integer, Boolean>();
            initDate();
        }
        private void initDate() {


            ContentResolver cr  =getContentResolver();
            Cursor c = cr.query(ProblemContract.CONTENT_URI,null,null,null,null);


            for (int i = 0; i < c.getCount(); i++) {
                isSelected.put(i, false);
            }
        }


        @Override
        public void bindView(View view, Context context, Cursor cursor) {

          //  TextView childLine = (TextView) view.findViewById(R.id.task_childname);


          //  childLine.setText( cursor.getString(cursor.getColumnIndex("name")));


            //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            TaskProblemActivity.TaskProblemAdapter.ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.task_problem_item,parent,false);
                viewHolder = new TaskProblemActivity.TaskProblemAdapter.ViewHolder();
                viewHolder.name = (TextView)convertView.findViewById(R.id.task_problem_head);
                viewHolder.select = (RadioButton)convertView.findViewById(R.id.task_problem_radio);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (TaskProblemActivity.TaskProblemAdapter.ViewHolder)convertView.getTag();
            }
            Cursor c = taskProblemAdapter.getCursor();
            // c.moveToPosition(position);
            c.moveToPosition(position);

            Problem newp =  new Problem(c);

            viewHolder.name.setText(newp.head);

               viewHolder.select.setChecked(isSelected.get(position));


            return convertView;
        }

        public class ViewHolder{
            TextView name;
            RadioButton select;
        }




    }
    @Override
    public void handleResults(TypedCursor<Problem> results) {
        //  update the adapter

        // Log.d(TAG, "handleResults: ");
        if (results.moveToFirst()) {


            taskProblemAdapter.swapCursor(null);
            taskProblemAdapter.swapCursor(results.getCursor());

        }else {

            taskProblemAdapter.swapCursor(null);

        }

        taskProblemAdapter.notifyDataSetChanged();
    }

    @Override
    public void closeResults() {
        //  update the adapter

    }



    public void onConfirm(View v){

        Intent it = getIntent();
        it.putStringArrayListExtra(CONFIRM_RES,pId);
        TaskProblemActivity.this.setResult(ADD_RESULT, it);

        TaskProblemActivity.this.finish();

    }
}

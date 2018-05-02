package com.zhaohengsun.learnmath;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.zhaohengsun.learnmath.async.QueryBuilder;
import com.zhaohengsun.learnmath.entities.Children;
import com.zhaohengsun.learnmath.entities.Problem;
import com.zhaohengsun.learnmath.entities.Task;
import com.zhaohengsun.learnmath.managers.ChildrenManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity implements QueryBuilder.IQueryListener<Children>{
    static final private int ADD_REQUEST = 1;
    private ListView lv;
    private ChildrenManager childrenManager;
    private TaskChildAdapter taskChildAdapter;
    private int selectPosition = -1;
    private Children selectedChild;
    private ArrayList<String> idList;
  private boolean isClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        idList = new ArrayList<String>();

        taskChildAdapter = new TaskChildAdapter(this,null);

        lv = (ListView)findViewById(R.id.task_child_list);
        lv.setAdapter(taskChildAdapter);


        childrenManager = new ChildrenManager(this);
        childrenManager.getAllChildrenAsync(this);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isClick = true;
                selectPosition = position;
                taskChildAdapter.notifyDataSetChanged();
                Cursor c = taskChildAdapter.getCursor();
                c.moveToPosition(position);

                selectedChild = new Children(c);
                Log.d("addtask", "onItemClick: "+selectedChild.name);
            }
        });



    }






    public void onAddproblems(View v){

        Intent it = new Intent(AddTaskActivity.this,TaskProblemActivity.class);
        startActivityForResult(it,ADD_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        // Use ADD_REQUEST and CHECKOUT_REQUEST codes to distinguish the cases.
        switch(requestCode) {
            case ADD_REQUEST:
                // ADD: add the book that is returned to the shopping cart.
                //  It is okay to do this on the main thread for BookStoreWithContentProvider

                //

                if (resultCode == ADD_REQUEST) {
                    Bundle data = intent.getExtras();
                    idList = data.getStringArrayList(TaskProblemActivity.CONFIRM_RES);
                    Log.d("res get back", "onActivityResult: success"+idList
                    );

                }
                break;
        }
        }






    @Override
    public void handleResults(TypedCursor<Children> results) {
        //  update the adapter

        // Log.d(TAG, "handleResults: ");
        if (results.moveToFirst()) {


            taskChildAdapter.swapCursor(null);
            taskChildAdapter.swapCursor(results.getCursor());

        }else {

            taskChildAdapter.swapCursor(null);

        }

        taskChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void closeResults() {
        //  update the adapter

    }

    public class TaskChildAdapter extends ResourceCursorAdapter {
        Context context;

        LayoutInflater mInflater;
        protected final static int ROW_LAYOUT = R.layout.task_child_item;

        public TaskChildAdapter(Context context, Cursor cursor) {
            super(context, ROW_LAYOUT, cursor, 0);
            this.context = context;
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }



        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView childLine = (TextView) view.findViewById(R.id.task_childname);


            childLine.setText( cursor.getString(cursor.getColumnIndex("name")));


            //  String authors = cursor.getString(cursor.getColumnIndex("authors"));



        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.task_child_item,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView)convertView.findViewById(R.id.task_childname);
                viewHolder.select = (RadioButton)convertView.findViewById(R.id.task_child_radio);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            Cursor c = taskChildAdapter.getCursor();
           // c.moveToPosition(position);
            c.moveToPosition(position);

            Children newc =  new Children(c);

            viewHolder.name.setText(newc.name);
            if(selectPosition == position){
                viewHolder.select.setChecked(true);
            }
            else{
                viewHolder.select.setChecked(false);
            }
            return convertView;
        }

    public class ViewHolder{
        TextView name;
        RadioButton select;
    }




    }



    public void onConfirmTask(View v){

        TextView tx = (TextView)findViewById(R.id.search_limit_time);



        if(isClick&&idList!=null&&idList.size()>0&&tx.getText().toString()!=null){

          //  ContentResolver cr = getContentResolver();
            Problem[] problems = new Problem[idList.size()];

            for(int i =0;i<idList.size();i++){

                problems[i] = new Problem("1","1",Long.parseLong(idList.get(i)),5,"1");
                Log.d("addtaskActivity", "onConfirmTask: " +problems[i].score);

            }

            //ContentValues cv = new ContentValues();
            Task t = new Task(selectedChild.name,problems,"false",Long.parseLong(tx.getText().toString()));
           // t.writeToProvider(cv);


           // cr.insert(TaskContract.CONTENT_URI,cv);
            Log.d(" insert", "onConfirmTask: yes!!!!!!!");

            Intent it = getIntent();
            // AddTaskActivity.this.set
            it.putExtra("res",t);
            AddTaskActivity.this.setResult(ADD_REQUEST,it);

            AddTaskActivity.this.finish();

        }



    }




}

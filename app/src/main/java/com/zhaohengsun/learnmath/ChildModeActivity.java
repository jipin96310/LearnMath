package com.zhaohengsun.learnmath;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zhaohengsun.learnmath.async.QueryBuilder;
import com.zhaohengsun.learnmath.entities.Children;
import com.zhaohengsun.learnmath.managers.ChildrenManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;
import com.zhaohengsun.learnmath.util.ChildModeAdapter;

public class ChildModeActivity extends AppCompatActivity implements QueryBuilder.IQueryListener<Children>{
    static final private int ADD_REQUEST = 1;
    private ListView lv;
    private ChildrenManager childrenManager;
    private ChildModeAdapter childrenAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_mode);

        childrenAdapter = new ChildModeAdapter(this,null);

        lv = (ListView)findViewById(R.id.childmode_list);
        lv.setAdapter(childrenAdapter);


        childrenManager = new ChildrenManager(this);
        childrenManager.getAllChildrenAsync(this);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ChildModeActivity.this, ChildeModeMainActivity.class);
                Cursor c = childrenAdapter.getCursor();

                intent.putExtra("name",c.getString(c.getColumnIndex("name")));
                intent.putExtra("id",c.getLong(c.getColumnIndex("_id")));
                startActivityForResult(intent,ADD_REQUEST);


            }
        });

    }








    @Override
    public void handleResults(TypedCursor<Children> results) {
        //  update the adapter

        // Log.d(TAG, "handleResults: ");
        if (results.moveToFirst()) {


            childrenAdapter.swapCursor(null);
            childrenAdapter.swapCursor(results.getCursor());

        }else {

            childrenAdapter.swapCursor(null);

        }

        childrenAdapter.notifyDataSetChanged();
    }

    @Override
    public void closeResults() {
        //  update the adapter

    }



}

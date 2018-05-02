package com.zhaohengsun.learnmath;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.zhaohengsun.learnmath.async.IContinue;
import com.zhaohengsun.learnmath.async.QueryBuilder;
import com.zhaohengsun.learnmath.entities.Children;
import com.zhaohengsun.learnmath.managers.ChildrenManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;
import com.zhaohengsun.learnmath.util.ChildrenAdapter;

public class ChildrenManageActivity extends AppCompatActivity implements QueryBuilder.IQueryListener<Children> {
    static final private int ADD_REQUEST = 1;
    private ListView lv;
    private ChildrenManager childrenManager;
    private ChildrenAdapter childrenAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_manage);

        childrenAdapter = new ChildrenAdapter(this,null);

        lv = (ListView)findViewById(R.id.children_list);
        lv.setAdapter(childrenAdapter);


        childrenManager = new ChildrenManager(this);
        childrenManager.getAllChildrenAsync(this);


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

                if(resultCode == ADD_REQUEST) {
                    Bundle data = intent.getExtras();
                    Children children = data.getParcelable(AddChildrenActivity.CHILDREN_RESULT_KEY);

                    childrenManager.persistAsync(children, new IContinue<Uri>() {
                       public void kontinue(Uri uri) {

                            childrenManager.getAllChildrenAsync(ChildrenManageActivity.this);
                        }
                   } );

                }

                break;
            // TODO

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //  inflate a menu with ADD and CHECKOUT options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);

        Button addnewButton = (Button)menu.findItem(R.id.add_add).getActionView();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {

            //  ADD provide the UI for adding a book
            case R.id.add_add:
                Intent addIntent = new Intent(this, AddChildrenActivity.class);
                startActivityForResult(addIntent, ADD_REQUEST);
                break;


        }
        return false;
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

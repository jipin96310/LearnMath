package com.zhaohengsun.learnmath;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.zhaohengsun.learnmath.async.IContinue;
import com.zhaohengsun.learnmath.async.QueryBuilder;
import com.zhaohengsun.learnmath.contracts.RewardContract;
import com.zhaohengsun.learnmath.entities.Reward;
import com.zhaohengsun.learnmath.managers.RewardManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;
import com.zhaohengsun.learnmath.util.RewardAdapter;

public class AddRewardActivity extends AppCompatActivity implements QueryBuilder.IQueryListener<Reward>{
    private RewardManager rewardManager;
    private RewardAdapter rewardAdapter;
    private ListView lv;
    static final private int ADD_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reward);


        rewardAdapter = new RewardAdapter(this,null);

        lv = (ListView) findViewById(R.id.reward_list);

        lv.setAdapter(rewardAdapter);


        rewardManager = new RewardManager(this);
        rewardManager.getAllRewardsAsync(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            int cur_id = 0;
            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Here you can do something when items are
                // selected/de-selected,such as update the title in the CAB
                cur_id = position;



            }

            private void setSubtitle(ActionMode mode) {

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                //	int id = (int)info.id;
                Cursor c = rewardAdapter.getCursor();
                lv.getAdapter().getItem(cur_id);

                switch (item.getItemId()) {

                    case R.id.menu_delete:

                        c.moveToPosition(cur_id);
                        int rowid2 = c.getInt(c.getColumnIndex("_id"));

                        ContentResolver cr = getContentResolver();
                        cr.delete(RewardContract.CONTENT_URI(rowid2),null,null);

                        if (c != null) {
                            rewardManager.getAllRewardsAsync(AddRewardActivity.this);

                        }

                        mode.finish();
                        return true;
                    default:mode.finish();
                        return false;
                }

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are
                // deselected/unchecked.
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
            }
        });


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
                Intent addIntent = new Intent(this, AddRewardAddActivity.class);
                startActivityForResult(addIntent, ADD_REQUEST);
                break;


        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        // Use ADD_REQUEST and CHECKOUT_REQUEST codes to distinguish the cases.
        switch(requestCode) {
            case ADD_REQUEST:


                if(resultCode == ADD_REQUEST) {
                    Bundle data = intent.getExtras();
                    Reward task = data.getParcelable(AddRewardAddActivity.REWARD_RESULT_KEY);


                    rewardManager.persistAsync(task, new IContinue<Uri>() {
                        public void kontinue(Uri uri) {
                            // Message.id = get;
                            rewardManager.getAllRewardsAsync(AddRewardActivity.this);
                        }
                    } );

                }

                break;


        }

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

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
import com.zhaohengsun.learnmath.entities.Problem;
import com.zhaohengsun.learnmath.managers.ProblemManager;
import com.zhaohengsun.learnmath.managers.TypedCursor;
import com.zhaohengsun.learnmath.util.ProblemAdapter;

public class ProblemManage extends AppCompatActivity implements QueryBuilder.IQueryListener<Problem> {
    static final private int ADD_REQUEST = 1;
    private ListView lv;
    private ProblemManager problemManager;
    private ProblemAdapter problemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_manage);

        problemAdapter = new ProblemAdapter(this,null);

        lv = (ListView) findViewById(R.id.problem_list);

        lv.setAdapter(problemAdapter);


        problemManager = new ProblemManager(this);
        problemManager.getAllBooksAsync(this);


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
                    Problem problem = data.getParcelable(AddProblemActivity.PROBLEM_RESULT_KEY);

                    problemManager.persistAsync(problem, new IContinue<Uri>() {
                        public void kontinue(Uri uri) {
                            // Message.id = get;
                            problemManager.getAllBooksAsync(ProblemManage.this);
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
        inflater.inflate(R.menu.random_add_menu, menu);

        Button addnewButton = (Button)menu.findItem(R.id.random_add_add).getActionView();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {

            //  ADD provide the UI for adding a book
            case R.id.random_add_add:
                Intent addIntent = new Intent(this, AddProblemActivity.class);
                startActivityForResult(addIntent, ADD_REQUEST);
                break;
            case R.id.random_add_generate:

                Problem problem1 = new Problem("A large box contains 18 small boxes and each small box contains 25 chocolate bars. How many chocolate bars are in the large box? ","450 chocolate bars",10,3,"Grade 5");
                Problem problem2 = new Problem("1+2+3=?","6",5,3,"Grade2");
                Problem problem3 = new Problem("The cost of buying a tall building is one hundred twenty one million dollars. Write this number in standard form.","$121,000,000",5,4,"Grade 4");
                Problem problem4 = new Problem("2+4*2=?","10",5,2,"Grade 2");
                Problem problem5 = new Problem("Kim can walk 4 kilometers in one hour. How long does it take Kim to walk 18 kilometers?","4 hours and 30 minutes",5,2,"Grade 5");
                Problem problem6 = new Problem("Convert 5/10 to decimal.","0.5",5,2,"Grade 3");
                Problem problem7 = new Problem("1+2*3=?","7",5,1,"Grade 2");
                Problem problem8 = new Problem("Tina works 15 hours a week (Monday to Friday). Last week she worked 3 1/2 hours on Monday, 4 hours on Tuesday, 2 1/6 hours on Wednesday and 1 1/2 on Thursday. How many hours did she work on Friday? .","3 5/6",20,5,"Grade 6");


                problemManager.persistAsync(problem1, new IContinue<Uri>() {
                    public void kontinue(Uri uri) {
                        // Message.id = get;

                    }
                } );
                problemManager.persistAsync(problem2, new IContinue<Uri>() {
                    public void kontinue(Uri uri) {
                        // Message.id = get;

                    }
                } );
                problemManager.persistAsync(problem3, new IContinue<Uri>() {
                    public void kontinue(Uri uri) {
                        // Message.id = get;

                    }
                } );
                problemManager.persistAsync(problem4, new IContinue<Uri>() {
                    public void kontinue(Uri uri) {
                        // Message.id = get;

                    }
                } );
                problemManager.persistAsync(problem5, new IContinue<Uri>() {
                    public void kontinue(Uri uri) {
                        // Message.id = get;

                    }
                } );



                problemManager.persistAsync(problem6, new IContinue<Uri>() {
                    public void kontinue(Uri uri) {
                        // Message.id = get;
                        problemManager.getAllBooksAsync(ProblemManage.this);
                    }
                } );
                problemManager.persistAsync(problem7, new IContinue<Uri>() {
                    public void kontinue(Uri uri) {
                        // Message.id = get;
                        problemManager.getAllBooksAsync(ProblemManage.this);
                    }
                } );
                problemManager.persistAsync(problem8, new IContinue<Uri>() {
                    public void kontinue(Uri uri) {
                        // Message.id = get;
                        problemManager.getAllBooksAsync(ProblemManage.this);
                    }
                } );


        }
        return false;
    }

    @Override
    public void handleResults(TypedCursor<Problem> results) {
        //  update the adapter

       // Log.d(TAG, "handleResults: ");
        if (results.moveToFirst()) {



            problemAdapter.swapCursor(null);
            problemAdapter.swapCursor(results.getCursor());


        }else {

            problemAdapter.swapCursor(null);

        }


        problemAdapter.notifyDataSetChanged();
    }

    @Override
    public void closeResults() {
        //  update the adapter

    }

}

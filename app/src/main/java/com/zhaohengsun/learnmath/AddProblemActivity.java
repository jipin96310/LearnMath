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

import com.zhaohengsun.learnmath.entities.Problem;

public class AddProblemActivity extends AppCompatActivity {
    public static final String PROBLEM_RESULT_KEY = "problem_result";
    static final private int ADD_RESULT = 1;

    private TextView search_head;
    private TextView search_body;
    private TextView search_category;
    private TextView search_score;
    private TextView search_difficulty;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
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
                Problem problem = addProblem();
                if(problem!=null){
                    Intent intent = getIntent();

                    intent.putExtra(PROBLEM_RESULT_KEY,problem);

                    AddProblemActivity.this.setResult(ADD_RESULT, intent);

                    AddProblemActivity.this.finish();
                }
                else{

                    Toast.makeText(this,"Please fill all the details and try again",Toast.LENGTH_SHORT).show();
                }

                break;


            default:
        }
        return false;
    }




    public Problem addProblem(){
        //  Just build a Book object with the search criteria and return that.
        search_head =(TextView)findViewById(R.id.search_head);
         search_body = (TextView)findViewById(R.id.search_body);
        search_category = (TextView) findViewById(R.id.search_category);
        search_score = (TextView) findViewById(R.id.search_score);
       search_difficulty = (TextView) findViewById(R.id.search_difficulty);




        String head = search_head.getText().toString();
        String body = search_body.getText().toString();
        String category = search_category.getText().toString();
        long score = Long.parseLong(search_score.getText().toString());
        long difficulty = Long.parseLong(search_difficulty.getText().toString());


        if(head!=""&&body!=""&&category!=""&&score>=0&&difficulty>=0) {



          Problem p = new Problem(head,body,score,difficulty,category);


            return p;
        }

        return null;
    }





}

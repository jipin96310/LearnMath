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

import com.zhaohengsun.learnmath.entities.Children;

public class AddChildrenActivity extends AppCompatActivity {
    public static final String CHILDREN_RESULT_KEY = "children_result";
    static final private int ADD_RESULT = 1;

    private TextView search_name;
    private TextView search_schooltype;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_children);



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
                Children children = addChildren();
                if(children!=null){
                    Intent intent = getIntent();

                    intent.putExtra(CHILDREN_RESULT_KEY,children);

                    AddChildrenActivity.this.setResult(ADD_RESULT, intent);

                    AddChildrenActivity.this.finish();
                }
                else{

                    Toast.makeText(this,"Please fill all the details and try again",Toast.LENGTH_SHORT).show();
                }

                break;


            default:
        }
        return false;
    }









    public Children addChildren(){
        //  Just build a Book object with the search criteria and return that.
        search_name =(TextView)findViewById(R.id.search_name);
        search_schooltype = (TextView)findViewById(R.id.search_school);



        String name = search_name.getText().toString();
        String school = search_schooltype.getText().toString();



        if(name!=""&&school!="") {



            Children p = new Children(name,school);


            return p;
        }

        return null;
    }
}

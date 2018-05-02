package com.zhaohengsun.learnmath.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaohengsun.learnmath.R;
import com.zhaohengsun.learnmath.entities.MainItem;

import java.util.ArrayList;

/**
 * Created by Zhaoheng Sun on 2018/4/27.
 */

public class MainAdapter extends ArrayAdapter<MainItem> {

    public MainAdapter(Context context, ArrayList<MainItem> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MainItem mi = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.main_list, parent, false);
        }
        // Lookup view for data population
        TextView title_text = (TextView) convertView.findViewById(R.id.tutorial_text);
        TextView article_text = (TextView) convertView.findViewById(R.id.article_text);
        ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
        // Populate the data into the template view using the data object
        title_text.setText(mi.title);
        article_text.setText(mi.article);
       switch ((int)mi.id){
           case 1:
               iv.setImageResource(R.drawable.teacher);
               break;
           case 2:
               iv.setImageResource(R.drawable.student);
               break;


       }
        // Return the completed view to render on screen
        return convertView;
    }

}

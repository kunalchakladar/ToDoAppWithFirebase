package com.example.kunal.todoapp;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Kunal on 7/26/2017.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList arrayList;

    public CustomAdapter(Context context, ArrayList arrayList) {

        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.task_layout, viewGroup, false);

        TextView textView = (TextView) view.findViewById(R.id.myTaskTextView);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        textView.setText(arrayList.get(i).toString());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    Toast.makeText(context, "Checked", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Unchecked", Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }
}

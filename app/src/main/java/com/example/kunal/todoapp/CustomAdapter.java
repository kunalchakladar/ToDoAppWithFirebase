package com.example.kunal.todoapp;


import android.content.Context;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * Created by Kunal on 7/26/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList arrayList;
    private ArrayList keys;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference taskRef = database.getReference("tasks");
    //private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //private String uid = user.getUid();


    public CustomAdapter(Context context, ArrayList arrayList, ArrayList keys) {

        this.arrayList = arrayList;
        this.context = context;
        this.keys = keys;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = inflater.inflate(R.layout.task_layout, viewGroup, false);

        TextView textView = view.findViewById(R.id.myTaskTextView);
        final CheckBox checkBox = view.findViewById(R.id.checkBox);
        textView.setText(arrayList.get(i).toString());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                   // Toast.makeText(context, "Checked" + "and tapped on " + arrayList.get(i) + "\nand key is " + keys.get(i), Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure to remove this task?");
                    builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {

                            taskRef.child(keys.get(i).toString()).removeValue();
                            Toast.makeText(context, "Your task has been deleted", Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int j) {

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
                else
                    Toast.makeText(context, "Unchecked", Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }
}

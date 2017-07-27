package com.example.kunal.todoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TODOActivity extends AppCompatActivity {

    private static final String TAG = "TODOActivity";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference taskRef = database.getReference("tasks");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    ArrayList keys = new ArrayList();

    ListView taskLists;
    ArrayList<String> arrayList;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);


        taskLists = (ListView) findViewById(R.id.taskList);
        arrayList = new ArrayList<>();
        customAdapter = new CustomAdapter(this, arrayList, keys);
        taskLists.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TODOActivity.this, LoginActivity.class));
                finish();
            }
        });



        final LayoutInflater layoutInflater = this.getLayoutInflater();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(TODOActivity.this, "tapped", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onChildAdded: " + "running2");
                AlertDialog.Builder builder = new AlertDialog.Builder(TODOActivity.this);
                builder.setTitle("Add Task");

                View layout = layoutInflater.inflate(R.layout.dialog_add_task, null);

                builder.setView(layout);
                final EditText task = layout.findViewById(R.id.taskEditText);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        //Toast.makeText(TODOActivity.this, task.getText().toString(), Toast.LENGTH_SHORT).show();
                        Tasks myTask = new Tasks();
                        myTask.setTask(task.getText().toString());
                        myTask.setUid(uid);
                        taskRef.push().setValue(myTask);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        taskRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String tasks = dataSnapshot.getValue(String.class);
                Log.d(TAG, "onChildAdded: " + dataSnapshot.getValue());
                Log.d(TAG, "onChildAdded: " + "running");
                String key = dataSnapshot.getKey();

                Tasks myTask = dataSnapshot.getValue(Tasks.class);

                if(uid.equals(myTask.getUid())){

                    //add to the list
                    arrayList.add(myTask.getTask());
                    keys.add(key);

                    Log.d(TAG, "onChildAdded: Keys" + keys);
                    Log.d(TAG, "onChildAdded: " + arrayList);

                }
                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved: " + dataSnapshot.getValue());
                String key = dataSnapshot.getKey();

                Tasks myTask = dataSnapshot.getValue(Tasks.class);
                Log.d(TAG, "onChildRemoved: Deleted" + myTask.getTask());
                arrayList.remove(myTask.getTask());
                keys.remove(key);

                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

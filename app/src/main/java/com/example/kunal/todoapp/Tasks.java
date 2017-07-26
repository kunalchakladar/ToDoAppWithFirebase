package com.example.kunal.todoapp;

/**
 * Created by Kunal on 7/26/2017.
 */

public class Tasks {

    String task, uid;

    public Tasks() {
    }

    public Tasks(String task, String uid) {
        this.task = task;
        this.uid = uid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

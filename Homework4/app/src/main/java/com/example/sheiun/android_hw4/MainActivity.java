package com.example.sheiun.android_hw4;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.sheiun.android_hw4.database.Course;
import com.example.sheiun.android_hw4.database.MyDbOpenHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public MyDbOpenHelper dbHelper;
    private MyAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDbOpenHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.class_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(MainActivity.this, dbHelper.getCourseList(), dbHelper);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    public void onFAB(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle(R.string.add_tsak);

        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(DialogInterface dialog, int id) {
                EditText title = (EditText) ((AlertDialog) dialog).findViewById(R.id.etTitle);
                Spinner spinner = (Spinner) ((AlertDialog) dialog).findViewById(R.id.spWeek);
                TimePicker timePicker = (TimePicker) ((AlertDialog) dialog).findViewById(R.id.tpTime);

                String time = String.format("%1$02d:%2$02d", timePicker.getHour(), timePicker.getMinute());
                Course course = new Course(title.getText().toString(), spinner.getSelectedItem().toString(), time);
                dbHelper.addNewCourse(course);
                adapter.update(dbHelper.getCourseList());
            }
        });
        mBuilder.setNegativeButton("Cancel", null);

        View mView = getLayoutInflater().inflate(R.layout.dialog_addtask, null);
        mBuilder.setView(mView);

        final AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}

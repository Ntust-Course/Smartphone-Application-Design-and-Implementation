package com.example.sheiun.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;


public class ChildActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        TextView tv = this.findViewById(R.id.tv_display);
        Intent intent = getIntent();
        if (intent.hasExtra(intent.EXTRA_TEXT)) {
            String text = intent.getStringExtra(intent.EXTRA_TEXT);
            tv.setText(getResources().getString(R.string.id) + " : " + text);
        }
    }
}

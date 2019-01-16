package com.example.sheiun.hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Item> myDataset = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Item item = new Item();
            item.setCheck(false);
            item.setText(Integer.toString(i));
            myDataset.add(item);
        }

        this.myAdapter = new MyAdapter(myDataset);
        RecyclerView mList = (RecyclerView) findViewById(R.id.rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.SELECT) {
            if (myAdapter.currentSelectedItems.size() > 0) {
                Toast.makeText(getApplicationContext(), "You select " + myAdapter.getList(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "沒有選不要亂按", Toast.LENGTH_SHORT).show();

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


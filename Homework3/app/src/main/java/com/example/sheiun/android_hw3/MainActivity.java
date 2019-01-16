package com.example.sheiun.android_hw3;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sheiun.android_hw3.data.MyContract;

public class MainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private Spinner mPeopleSpinner;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setting Drop List
        Spinner dropdown = findViewById(R.id.edit_people);
        String[] items = new String[]{"1", "2", "3", "4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        // Setting rw
        RecyclerView waitlistRecyclerView;
        // Set local attributes to corresponding views
        waitlistRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mNameEditText = (EditText) this.findViewById(R.id.edit_name);
        mPhoneEditText = (EditText) this.findViewById(R.id.edit_phone);
        mPeopleSpinner = (Spinner) this.findViewById(R.id.edit_people);
        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Not Need DbHelper
        // Set Cursor
        Cursor cursor = getAllGuests();
        // Create an adapter for that cursor to display the data
        mAdapter = new MyAdapter(this, cursor);
        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);
        // Create an item touch helper to handle swiping items off the list
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                // remove
                removeGuest(id);
                // update
                mAdapter.swapCursor(getAllGuests());
            }

        }).attachToRecyclerView(waitlistRecyclerView);

    }

    public void addToWaitlist(View view) {
        if (mNameEditText.getText().toString().matches("") ||
                mPhoneEditText.getText().toString().matches("")) {
            return;
        }
        int people = 1;
        try {
            people = Integer.parseInt(mPeopleSpinner.getSelectedItem().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to people text to number: " + ex.getMessage());
        }

        addNewGuest(mNameEditText.getText().toString(), mPhoneEditText.getText().toString(), people);

        // Update the cursor in the adapter to trigger UI to display the new list
        mAdapter.swapCursor(getAllGuests());

        //clear UI text fields
        mPeopleSpinner.clearFocus();
        mNameEditText.getText().clear();
        mPhoneEditText.getText().clear();
        mPeopleSpinner.setSelection(0);
    }

    private Cursor getAllGuests() {
        ContentResolver cr = this.getContentResolver();
        Cursor cursor = cr.query(MyContract.WaitlistEntry.CONTENT_URI, null, null, null, null);
        Log.d(LOG_TAG, MyContract.WaitlistEntry.CONTENT_URI.getAuthority());
        cursor.moveToFirst();
        Log.d(LOG_TAG, String.valueOf(cursor));
        return cursor;
    }

    private void addNewGuest(String name, String phone, int people) {
        ContentValues cv = new ContentValues();
        cv.put(MyContract.WaitlistEntry.COLUMN_NAME, name);
        cv.put(MyContract.WaitlistEntry.COLUMN_PHONE, phone);
        cv.put(MyContract.WaitlistEntry.COLUMN_PEOPLE, people);
        getContentResolver().insert(MyContract.WaitlistEntry.CONTENT_URI, cv);
    }

    private void removeGuest(long id) {
        String stringId = Long.toString(id);
        Uri uri = MyContract.WaitlistEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        getContentResolver().delete(uri, null, null);
    }
}

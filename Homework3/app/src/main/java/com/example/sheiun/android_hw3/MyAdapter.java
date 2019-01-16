package com.example.sheiun.android_hw3;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sheiun.android_hw3.data.MyContract;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public MyAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recyclerview_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String name = mCursor.getString(mCursor.getColumnIndex(MyContract.WaitlistEntry.COLUMN_NAME));
        String phone = mCursor.getString(mCursor.getColumnIndex(MyContract.WaitlistEntry.COLUMN_PHONE));
        int people = mCursor.getInt(mCursor.getColumnIndex(MyContract.WaitlistEntry.COLUMN_PEOPLE));
        // COMPLETED (6) Retrieve the id from the cursor and
        long id = mCursor.getLong(mCursor.getColumnIndex(MyContract.WaitlistEntry._ID));

        holder.name.setText(name);
        holder.phone.setText(phone);
        holder.people.setText(String.valueOf(people));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView phone;
        TextView people;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            people = (TextView) itemView.findViewById(R.id.people);
        }

    }
}

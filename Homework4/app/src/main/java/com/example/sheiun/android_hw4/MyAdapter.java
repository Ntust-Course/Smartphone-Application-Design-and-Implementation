package com.example.sheiun.android_hw4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.sheiun.android_hw4.database.Course;
import com.example.sheiun.android_hw4.database.MyDbOpenHelper;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Course> mCourseList;
    private Context mContext;
    private MyDbOpenHelper dbHelper;

    public MyAdapter(Context context, List<Course> mCourseList, MyDbOpenHelper dbHelper) {
        this.mCourseList = mCourseList;
        this.mContext = context;
        this.dbHelper = dbHelper;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.class_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String title = mCourseList.get(position).getTitle();
        String week = mCourseList.get(position).getWeek();
        String time = mCourseList.get(position).getTime().toString();
        boolean enable = mCourseList.get(position).getEnabled();

        long id = mCourseList.get(position).getId();

        holder.title.setText(title);
        holder.week.setText(week);
        holder.time.setText(time);
        holder.itemView.setTag(id);

        holder.title.setChecked(enable);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean c = ((CheckedTextView) view).isChecked();
                holder.title.setChecked(!c);
                mCourseList.get(position).setEnabled(!c);
                dbHelper.updateCourse(mCourseList.get(position).getId(), !c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

    public void update(List<Course> courseList) {
        if (courseList != null) {
            mCourseList = courseList;
            this.notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckedTextView title;
        TextView week;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (CheckedTextView) itemView.findViewById(R.id.ctvTitle);
            week = (TextView) itemView.findViewById(R.id.tvWeek);
            time = (TextView) itemView.findViewById(R.id.tvTime);
        }

    }

}
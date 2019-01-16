package com.example.sheiun.hw2;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by SheiUn on 2018/4/18.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Item> mData;

    public MyAdapter(List<Item> data) {
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public CheckBox mCheckBox;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
            mCheckBox = (CheckBox) v.findViewById(R.id.info_chcekbox);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
        final Item item = mData.get(position);
        holder.mTextView.setText(item.getText());
        holder.mCheckBox.setChecked(item.isCheck());
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = ((CheckBox) view).isChecked();
                holder.mCheckBox.setChecked(b);
                mData.get(position).setCheck(b);
                if (b)
                    currentSelectedItems.add(item.getText());
                else
                    currentSelectedItems.remove(item.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<String> currentSelectedItems = new ArrayList<>();

    public String getList() {
        Collections.sort(this.currentSelectedItems, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
        return TextUtils.join(",", this.currentSelectedItems);
    }
}

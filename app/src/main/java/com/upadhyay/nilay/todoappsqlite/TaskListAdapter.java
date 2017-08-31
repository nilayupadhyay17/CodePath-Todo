package com.upadhyay.nilay.todoappsqlite;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static android.R.attr.priority;

/**
 * Created by nilay on 8/18/2017.
 */

public class TaskListAdapter extends ArrayAdapter<Todo>{

    private LayoutInflater mInflater;
    private List<Todo> mTasks = null;
    private int layoutResource;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;
    //private String mAppend;

    public TaskListAdapter(@NonNull Context context, @LayoutRes int resource,@NonNull List<Todo> tasks) {
        super(context, R.layout.list_item, tasks);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
       // mAppend = append;
        this.mTasks = tasks;
    }
    private static class ViewHolder{
        TextView taskname;
        TextView taskpriority;
    }

    @Override
    public void add(Todo object) {
        this.mTasks.add(object);
        //super.add(object);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.e("get view","getview");
        final ViewHolder holder;
        Todo todo = getItem(position);
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.taskname = (TextView)convertView.findViewById(R.id.viewTask);
            holder.taskpriority = (TextView)convertView.findViewById(R.id.main_tvTaskPriority);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        String name = getItem(position).getNote();
        String priority = String.valueOf(getItem(position).getPriority());
       // Log.e("Task List Adapter",String.valueOf(position));
        holder.taskname.setText(name);
        holder.taskpriority.setText(String.valueOf(priority));
        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);
        return convertView;
    }

    public void selectView(int position, boolean value){
        if(value){

            Log.d("IDSELECTED",getItem(position).getId()+"");
            mSelectedItemsIds.put(position,value);
        }
        else {
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        Log.e("toggle",String.valueOf(!mSelectedItemsIds.get(position)));
        selectView(position, !mSelectedItemsIds.get(position));
    }

    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }
    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}

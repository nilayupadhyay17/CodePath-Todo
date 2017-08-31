package com.upadhyay.nilay.todoappsqlite;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nilay on 8/10/2017.
 */

public class EditTaskDialog extends DialogFragment {
    private int mYear, mMonth, mDay;
    private EditText etTaskName, etTaskDate;
    private TextView tvTaskName, tvTaskDate;
    private Spinner spPriority, spStatus;
    String taskDate, taskName, taskpriortiy, taskstatus;
    List<String> prioritesList;
    List<String> statusList;
    String name, date, status, priority;
    int id;
    DatabaseHelper databaseHelper;
    Cursor rs;
    EditTaskDialogListener listener;
    int year, month, day;
    Bundle args;
    int position;
    public EditTaskDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getActivity());

        //setHasOptionsMenu(true);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Log.e("Dismiss", "called");
    }

    public static EditTaskDialog newInstance(String title) {
        EditTaskDialog frag = new EditTaskDialog();
        //Bundle args = new Bundle();
        //args.putString("title", title);
        // frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_contact,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //listener = (ViewTaskFragment)getTargetFragment();
            listener = (EditTaskDialogListener) getTargetFragment();
            //Log.e(TAG, "listener called" );
        } catch (ClassCastException cse) {
            //Log.e(TAG, "onAttach: ClassCastException: " + cse.getMessage() );
        }

    }

    private void saveDialog() {
        //setDate();
        Log.e("Add", "save");
        Todo todoadd;
        taskName = etTaskName.getText().toString();
      // if(!taskName.isEmpty()) {
        taskDate = etTaskDate.getText().toString();
        taskpriortiy = spPriority.getSelectedItem().toString();
        taskstatus = spStatus.getSelectedItem().toString();
        todoadd = new Todo(taskName, taskDate, taskstatus, taskpriortiy);
        //Bundle extras = getArguments();

        if (this.getTag().equalsIgnoreCase("fragment_edit_task")) {
            if (databaseHelper.updateTask(todoadd, id)) {
                todoadd.setId(id);
                // EditTaskDialogListener listener = (EditTaskDialogListener) getActivity();
               // id = id - 1;
                listener.onFinishEditTaskDialog(todoadd, position, false);
                Toast.makeText(getActivity(), "Task Updated", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        } else {
            long id = databaseHelper.addTask(todoadd);
            int ids =(int) id;
            todoadd.setId((int) id);
            if (id != -1) {
                Toast.makeText(getActivity(), "Task Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error Adding", Toast.LENGTH_SHORT).show();
            }

            listener.onFinishEditTaskDialog(todoadd, ids, true);
            dismiss();
        }
    /*}
        else{
            Toast.makeText(getActivity(), "Enter task at first", Toast.LENGTH_SHORT).show();
        }*/

    }

    private void discardDialog(){
        dismiss();

    }
    public interface EditTaskDialogListener {
        void onFinishEditTaskDialog(Todo todo, int id,boolean type);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
          args= new Bundle();
        year =  c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);



        Log.e("Add Dialog","On Create View");
        Bundle bundle = this.getArguments();
        prioritesList = new ArrayList<String>();
        prioritesList.add("Low");
        prioritesList.add("Medium");
        prioritesList.add("High");
        statusList = new ArrayList<String>();
        statusList.add("ToDo");
        statusList.add("Done");


        View view = inflater.inflate(R.layout.dialog_add,container,false);
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
         etTaskName = (EditText) view.findViewById(R.id.etTaskName);
        etTaskDate = (EditText)view.findViewById(R.id.etDatePicker);
        tvTaskName = (TextView)view.findViewById(R.id.tvTaskName);
        tvTaskDate = (TextView)view.findViewById(R.id.tvTaskDate);
        spPriority = (Spinner)view.findViewById(R.id.spPriority);
        spStatus =   (Spinner)view.findViewById(R.id.spStatus);
        ArrayAdapter<String> pdataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,prioritesList);
        pdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(pdataAdapter);
        ArrayAdapter<String> sdataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,statusList);
        sdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(sdataAdapter);
        //setDate();
        if (bundle != null){
            Log.e("Bundle",bundle.toString());
            name = bundle.getString("Name");
            date = bundle.getString("Date");
            position = bundle.getInt("Position");
            status = bundle.getString("Status");
            priority = bundle.getString("Priority");
            id = bundle.getInt("Id");

            Log.e("id",String.valueOf(id));
            if(id >0){

                rs = databaseHelper.getTaskId(id);
                if(rs != null){
                    Log.e("result", String.valueOf(rs.getCount()));
                    while (rs.moveToNext()){
                         String name = rs.getString(rs.getColumnIndex("TaskName"));
                         String date = rs.getString(rs.getColumnIndex("TaskDate"));
                            String status = rs.getString(rs.getColumnIndex("TaskStatus"));
                        String priority = rs.getString(rs.getColumnIndex("TaskPriority"));
                        Log.e("status",String.valueOf(prioritesList.indexOf(priority)));
                        Log.e("date",date+"");
                        Log.e("priority",priority);

                        etTaskDate.setText(date);
                        etTaskName.setText(name);
                        spPriority.setSelection((prioritesList.indexOf(priority)));
                        spStatus.setSelection(statusList.indexOf(status));}
                }
                //Log.e("id",rs.getString());

                if(!rs.isClosed()){
                    rs.close();
                }

            }

        }
        else {
            //myToolbar.setTitle("New Task");
        }
        // show soft keyboard
        final InputMethodManager imm = (InputMethodManager) etTaskName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etTaskName, InputMethodManager.SHOW_IMPLICIT);
        //etTaskName.requestFocus();
        //hideSoftKeyboard(etTaskDate);

        //etTaskDate.setInputType(InputType.TYPE_NULL);
       etTaskDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int year = args.getInt("year");
               int month = args.getInt("month");
               int day = args.getInt("day");
               Log.e("year",String.valueOf(year));
               Log.e("month",String.valueOf(month));
               Log.e("day",String.valueOf(day));
               Toast.makeText(getActivity(), "clicked", Toast.LENGTH_LONG).show();
               showDatePicker();
           }
       });


        myToolbar.inflateMenu(R.menu.menu_contact);
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.e("Add"," selcted");
                int id = item.getItemId();
                if (id == R.id.save){
                    saveDialog();
                }
                if(id == R.id.discard){
                    discardDialog();
                }
                return true;
            }
        });

        return view;
    }
    public void getDate(){
        if(!((etTaskDate.getText().toString().isEmpty()))){
            String darr[] = taskDate.split("-");
            args.putInt("year",Integer.valueOf(darr[0]));
            args.putInt("month",Integer.valueOf(darr[1]));
            args.putInt("day",Integer.valueOf(darr[2]));
        }
        else {
            args.putInt("year",year);
            args.putInt("month",month);
            args.putInt("day",day);
        }

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("Add Dialog","View Created");

        /*etTaskDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideSoftKeyboard(etTaskDate);
                // showDatePicker();
                Toast.makeText(getActivity(),"clicked",Toast.LENGTH_SHORT).show();
                //showSoftKeyboard(etTaskDate);
            }
        });*/

      /*  if(name != null){
            etTaskName.setText(name);
            etTaskDate.setText(date);
            spPriority.setSelection(prioritesList.indexOf(priority));
            spStatus.setSelection(statusList.indexOf(status));
        }*/


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return  dialog;
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
       // date.setStyle(0,R.style.);
        getDate();
        date.setArguments(args);
        date.setCallBack(ondate);
        date.show(getActivity().getSupportFragmentManager(),"Date_Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            taskDate =String.valueOf(year) + "-" + String.valueOf(month)
                    + "-" + String.valueOf(dayOfMonth);
            Toast.makeText(
                    getActivity(),
                    taskDate,
                    Toast.LENGTH_LONG).show();
            etTaskDate.setText(taskDate);
        }
    };


    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        //getDialog().
        // Call super onResume after sizing
        super.onResume();
    }

}

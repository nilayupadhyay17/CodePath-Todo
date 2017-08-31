package com.upadhyay.nilay.todoappsqlite;

import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    // Database Helper
    DatabaseHelper db;
    private EditText etTaskName;
    private EditText etTaskNote;
    private Spinner spPriority;
    private Spinner spStatus;
    private TaskListAdapter adapter;
    private ListView taskList;
    private ImageView ivAddTask;
    ArrayList<Todo> todo;
    Cursor cursor;
    DatabaseHelper databaseHelper;
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        init();


      //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //taskList = (ListView)findViewById(R.id.tasksList);
       //todo = new ArrayList<>();
       // ivAddTask = (ImageView)findViewById(R.id.ivAddTask);
      //setSupportActionBar(myToolbar);
        //sgetSupportActionBar().setTitle("ti");
       // ivAddTask.setOnClickListener(this);
       //  setuptasksList();


        //showAddDialog();
        // DatabaseHelper();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);


    }


   /* private void setuptasksList() {

        databaseHelper = new DatabaseHelper(this);


       cursor = databaseHelper.getAllTasks();
        if(!cursor.moveToNext()){
            Toast.makeText(this,"There are no Tasks to show",Toast.LENGTH_SHORT).show();
        }
        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                   // Toast.makeText(this,"There are Tasks to show",Toast.LENGTH_SHORT).show();
                    todo.add(new Todo(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
                }while (cursor.moveToNext());
            }
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        adapter = new TaskListAdapter(this, R.layout.list_item, todo);
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(this);
    }
*/
    private void showAddDialog() {

        FragmentManager fm = getSupportFragmentManager();
        EditTaskDialog editTaskDialog = EditTaskDialog.newInstance("Add Dialog");

        editTaskDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);
        //editTaskDialog.setTargetFragment(this,300);
        editTaskDialog.show(fm, "fragment_view_task");

        //args.putString();

    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("Activity"," selcted");
        int id = item.getItemId();
        if(id == R.id.newtask){
            showAddDialog();
        }
        return false;
    }*/

    private void init(){
        ViewTaskFragment viewTaskFragment = new ViewTaskFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,viewTaskFragment);
        transaction.commit();

    }

  /*  @Override
    public void onClick(View v) {
      *//*  if(v.getId() == R.id.ivAddTask){
            showAddDialog();
        }*//*
    }*/

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Todo todoSelect = todo.get(position);

        FragmentManager fm = getSupportFragmentManager();
        EditTaskDialog editTaskDialog = EditTaskDialog.newInstance("Add Dialog");
        editTaskDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        //editTaskDialog.setTargetFragment(this,300);
        editTaskDialog.show(fm, "fragment_edit_task");
        Bundle bundle = new Bundle();
        bundle.putInt("Id",position+1);
        bundle.putString("Name",todoSelect.getNote());
        bundle.putString("Date",todoSelect.getCreated_at());
        bundle.putString("Status",todoSelect.getStatus());
        bundle.putString("Priority",todoSelect.getPriority());
        editTaskDialog.setArguments(bundle);
       // Log.e("clicked", todoSelect.getNote());
    }*/


}

package com.upadhyay.nilay.todoappsqlite;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.x;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewTaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTaskFragment extends Fragment implements EditTaskDialog.EditTaskDialogListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG= "ViewTaskFragment";
    private OnFragmentInteractionListener mListener;
    ArrayList<Todo> todo;
    Cursor cursor;
    DatabaseHelper databaseHelper;
    private TaskListAdapter adapter;
    private ListView taskList;
    ActionMode actionMode;
    Toolbar myToolbar;
    public ViewTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewTaskFragment newInstance(String param1, String param2) {
        ViewTaskFragment fragment = new ViewTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

       // ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tasks");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_task, container, false);
        myToolbar = (Toolbar)getActivity().findViewById(R.id.my_toolbar);
       // TextView heading = (TextView) view.findViewById(R.id.textViewToolbar);
       // heading.setText("Tasks");
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

       // myToolbar.set
        //((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
      //  ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tasks");
        setHasOptionsMenu(true);
        //smyToolbar.setVisibility(View.VISIBLE);
        //Toolbar myToolbar = (Toolbar)view.findViewById(R.id.my_toolbar);
        taskList = (ListView)view.findViewById(R.id.tasksList);
         setuptasksList();

        // navigate to add contacts fragment
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: clicked fab.");
                showAddDialog();

            }
        });
        return view;
    }
    private void showAddDialog() {
        //myToolbar.setVisibility(View.GONE);
        //this.getActivity().getSupportActionBar().hide();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        EditTaskDialog editTaskDialog = EditTaskDialog.newInstance("Add Dialog");
        editTaskDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);

        editTaskDialog.setTargetFragment(this,300);
        editTaskDialog.show(fm, "fragment_view_task");

        //args.putString();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            Log.e(TAG,"long clicked");
            onListItemSelect(position);
            //

      /*  if(actionMode == null) {
            Log.e(TAG,"action mode null");
           // actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ActionBarCallback());
        }*/
            return true;
    }
    private void onListItemSelect(int position){
        adapter.toggleSelection(position);//Toggle the selection
        boolean hasCheckedItems = adapter.getSelectedCount() > 0;//Check if any items are already selected or not
        if(hasCheckedItems && actionMode == null){
            Log.e("mode","1");
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ActionBarCallback());
        }
        else if(!hasCheckedItems && actionMode != null){
            Log.e("mode","2");
            actionMode.finish();
        }
        if(actionMode != null){
            Log.e("mode","3");
            //set action mode title on item selection
            actionMode.setTitle(String.valueOf(adapter
                    .getSelectedCount()) + " selected");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void setuptasksList() {

        databaseHelper = new DatabaseHelper(getActivity());
        todo = new ArrayList<>();

        cursor = databaseHelper.getAllTasks();
        if(!cursor.moveToNext()){
            Toast.makeText(getActivity(),"There are no Tasks to show",Toast.LENGTH_SHORT).show();
        }
        if (cursor != null ) {
            if  (cursor.moveToFirst()) {
                do {
                    // Toast.makeText(this,"There are Tasks to show",Toast.LENGTH_SHORT).show();
                    Log.d("CURSR",cursor.getInt(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(3)+cursor.getString(4));
                    todo.add(new Todo(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
                }while (cursor.moveToNext());
            }
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        adapter = new TaskListAdapter(getActivity(), R.layout.list_item, todo);
        taskList.setAdapter(adapter);
        taskList.setOnItemClickListener(this);
        taskList.setOnItemLongClickListener(this);
    }
    @Override
    public void onFinishEditTaskDialog(Todo todoadd, int id, boolean type) {
        Log.e("Task","finished" + type);
        if(type) {
            Log.e("task", "add");
            //adapter.setNotifyOnChange(true);
            todo.add(todoadd);
            adapter.notifyDataSetChanged();
        }
        else {
            //todo.add(id,todoadd);
            todo.set(id,todoadd);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(actionMode != null){
            actionMode.finish();
        }

       // if (actionMode != null)
        //    onListItemSelect(position);
        Todo todoSelect = todo.get(position);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        EditTaskDialog editTaskDialog = EditTaskDialog.newInstance("Add Dialog");
        editTaskDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);
        editTaskDialog.setTargetFragment(this, 300);
        editTaskDialog.show(fm, "fragment_edit_task");
        Log.e("id view",String.valueOf(todoSelect.getId()));
        Bundle bundle = new Bundle();
        bundle.putInt("Id", todoSelect.getId());
        bundle.putInt("Position", position);
        bundle.putString("Name", todoSelect.getNote());
        bundle.putString("Date", todoSelect.getCreated_at());
        bundle.putString("Status", todoSelect.getStatus());
        bundle.putString("Priority", todoSelect.getPriority());
        editTaskDialog.setArguments(bundle);
        // Log.e("clicked", todoSelect.getNote());


    }
    private class ActionBarCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Log.e(TAG,"on action mode");
           mode.getMenuInflater().inflate(R.menu.menu_context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("My Action Mode");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.deletask:
                     deleteRows();
                    break;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            adapter.removeSelection();
            actionMode = null;
        }
        //Delete selected rows
        public void deleteRows() {
            SparseBooleanArray selected = adapter
                    .getSelectedIds();//Get selected ids
            Log.e("size",String.valueOf(selected.size()));
           String ids[] = new String[selected.size()];
            //Loop all selected ids
            for (int i = (selected.size() - 1); i >= 0; i--) {
                if (selected.valueAt(i)) {
                    Log.e("key",String.valueOf(selected.keyAt(i)));

                    ids[i] = String.valueOf(todo.get(selected.keyAt(i)).getId());
                    //If current id is selected remove the item via key
                    todo.remove(selected.keyAt(i));
                    adapter.notifyDataSetChanged();//notify adapter

                }
            }
            databaseHelper.deleteTask(ids);
            //adapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
            actionMode.finish();//Finish action mode after use
    }
}
}

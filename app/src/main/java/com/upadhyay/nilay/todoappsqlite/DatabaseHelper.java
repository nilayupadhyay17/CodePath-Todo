package com.upadhyay.nilay.todoappsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


/**
 * Created by nilay on 8/15/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
     private static final String DATABASE_NAME = "todo.db";
    private static final String TABLE_NAME = "todo_table";
    private static final String Col0 = "ID";
    private static final String Col1 = "TaskName";
    private static final String Col2 = "TaskDate";
    private static final String Col3 = "TaskStatus";
    private static final String Col4 = "TaskPriority";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+
                    TABLE_NAME+" ( "+
                    Col0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    Col1 + " TEXT, "+
                    Col2 + " TEXT, "+
                    Col3 + " TEXT, "+
                    Col4 + " TEXT )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS"+ TABLE_NAME);
        onCreate(db);
    }
    public long addTask(Todo todo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.e("to do",String.valueOf(todo.getId()));
        contentValues.put(Col1, todo.getNote());
        contentValues.put(Col2,todo.getCreated_at());
        contentValues.put(Col3,todo.getStatus());
        contentValues.put(Col4,todo.getPriority());
        long result = db.insert(TABLE_NAME, null,contentValues);
        Log.e("result",result+"");
        return result;

    }
    public Cursor getAllTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
    public boolean updateTask(Todo todo,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col1, todo.getNote());
        contentValues.put(Col2,todo.getCreated_at());
        contentValues.put(Col3,todo.getStatus());
        contentValues.put(Col4,todo.getPriority());
        int update = db.update(TABLE_NAME,contentValues, Col0  + " = ? ", new String[] {String.valueOf(id)});
        if(update != 1) {
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getTaskId(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+TABLE_NAME+" where "+Col0+"= "+id,null);
        Log.e("count",String.valueOf(res.getCount()));
        return res;
    }
    public void deleteTask(String[] ids){
        SQLiteDatabase db = this.getWritableDatabase();
        //String args = TextUtils.join(", ", ids);

       //String whereClause = Col0 + " IN (" + TextUtils.join(",", new String["0"]) + ")";
        //Log.e("count",String.valueOf(res.getCount()));
       //String[] ar = new String[]{"0,1"};
       // db.delete(TABLE_NAME, whereClause,null);
        //db.execSQL(String.format("DELETE FROM "+TABLE_NAME +" WHERE "+Col0+" IN (%s);","0","1"));
        // int val = db.delete(TABLE_NAME, Col0  + " = "+ new String[] {String.valueOf(0)},null);
        //Log.e("val",String.valueOf(val));
       // String[] ids={"0","1"};
        String args = TextUtils.join(", ", ids);
        Log.d("args",args);
        db.execSQL(String.format("DELETE FROM "+TABLE_NAME+" WHERE "+Col0+" IN (%s);", args));

    }
    
}

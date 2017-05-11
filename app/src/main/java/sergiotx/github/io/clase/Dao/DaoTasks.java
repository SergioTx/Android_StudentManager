package sergiotx.github.io.clase.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Task;

public class DaoTasks {

    private Context context;
    private SQLiteHelper conn;

    public DaoTasks(Context context) {
        this.context = context;
        this.conn = new SQLiteHelper(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    ////////////////////////////////////////////
    // INSERT
    ////////////////////////////////////////////
    //context will be the activity calling
    public boolean insertTask(Task task) {
        Log.d("insertTask", "Subject: " + task.toString());
        SQLiteDatabase db = null;
        try {
            this.conn = new SQLiteHelper(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
            db = conn.getWritableDatabase();
            if (db == null) {
                return false;
            } else {
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.Task.COLUMN_NAME, task.getName());
                values.put(DatabaseContract.Task.COLUMN_SUBJECTID, task.getSubject().getId());
                values.put(DatabaseContract.Task.COLUMN_DATE, task.getDate().toString()); //FIXME
                values.put(DatabaseContract.Task.COLUMN_COMPLETED, task.isCompleted());
                values.put(DatabaseContract.Task.COLUMN_REMINDER, false); //TODO for now, no reminders

                long id = db.insert(DatabaseContract.Task.TABLE_NAME, null, values);
            }
            return true;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    ////////////////////////////////////////////
    // SELECT
    ////////////////////////////////////////////

    public ArrayList<Task> getAllTaks() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        SQLiteDatabase db = null;
        try {
            db = conn.getReadableDatabase();
            if (db == null) {
                return null;
            } else {
                Cursor c = db.rawQuery(DatabaseContract.Task.SQL_GET_ALL_FIELDS, null);

                int col_id = c.getColumnIndex(DatabaseContract.Task._ID);
                int col_name = c.getColumnIndex(DatabaseContract.Task.COLUMN_NAME);
                int col_subjectid = c.getColumnIndex(DatabaseContract.Task.COLUMN_SUBJECTID);
                int col_date = c.getColumnIndex(DatabaseContract.Task.COLUMN_DATE);
                int col_reminder = c.getColumnIndex(DatabaseContract.Task.COLUMN_REMINDER);
                int col_completed = c.getColumnIndex(DatabaseContract.Task.COLUMN_COMPLETED);

                while (c.moveToNext()) {
                    Task t = new Task();
                    t.setId(c.getInt(col_id));
                    t.setName(c.getString(col_name));
                    t.setDate(Utils.dateFromString(c.getString(col_date)));
                    t.setCompleted(c.getInt(col_completed) > 0); //zero or one
                    t.setReminder(c.getInt(col_reminder) > 0); //zero or one
                    t.setSubject(getSubjectById(c.getInt(col_subjectid),db));


                    tasks.add(t);
                }
            }
            Log.d("getAllTasks", "ARRAYLIST: " + tasks.toString());
            return tasks;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    //PRIVATE: only internal use -> no closing of DB
    private Subject getSubjectById(int id, SQLiteDatabase db){
        Subject subject = null;

        String sql = DatabaseContract.Subject.SQL_GET_ALL_FIELDS + " where _id = " + id;
        Cursor c = db.rawQuery(sql, null);

        int col_id = c.getColumnIndex(DatabaseContract.Subject._ID);
        int col_name = c.getColumnIndex(DatabaseContract.Subject.COLUMN_NAME);
        int col_color = c.getColumnIndex(DatabaseContract.Subject.COLUMN_COLOR);
        int col_teacher = c.getColumnIndex(DatabaseContract.Subject.COLUMN_TEACHER);

        if (c.moveToNext()) {
            subject = new Subject(c.getInt(col_id), c.getString(col_name), c.getString(col_teacher), c.getInt(col_color));
        }
        return subject;
    }

    ////////////////////////////////////////////
    // UPDATE
    ////////////////////////////////////////////
    public boolean updateSubject(Subject subject) {
        Log.d("updateSubject", "Subject: " + subject.toString());
        SQLiteDatabase db = null;
        try {
            this.conn = new SQLiteHelper(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
            db = conn.getWritableDatabase();
            if (db == null) {
                return false;
            } else {
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.Subject.COLUMN_NAME, subject.getName());
                values.put(DatabaseContract.Subject.COLUMN_COLOR, subject.getColor());
                values.put(DatabaseContract.Subject.COLUMN_TEACHER, subject.getTeacher());

                long id = db.update(DatabaseContract.Subject.TABLE_NAME, values, "_id="+subject.getId(),null);
            }
            return true;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    ////////////////////////////////////////////
    // DELETE
    ////////////////////////////////////////////



}

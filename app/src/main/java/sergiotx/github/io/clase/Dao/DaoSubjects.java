package sergiotx.github.io.clase.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

import sergiotx.github.io.clase.beans.Subject;

public class DaoSubjects {

    private Context context;
    private SQLiteHelper conn;

    public DaoSubjects(Context context) {
        this.context = context;
        this.conn = new SQLiteHelper(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    ////////////////////////////////////////////
    // INSERT
    ////////////////////////////////////////////
    //context will be the activity calling
    public boolean insertSubject(Subject subject) {
        Log.d("insertSubject", "Subject: " + subject.toString());
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

                long id = db.insert(DatabaseContract.Subject.TABLE_NAME, null, values);
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

    public ArrayList<Subject> getAllSubjects() {
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        SQLiteDatabase db = null;
        try {
            db = conn.getReadableDatabase();
            if (db == null) {
                return null;
            } else {
                Cursor c = db.rawQuery(DatabaseContract.Subject.SQL_GET_ALL_FIELDS, null);

                int col_id = c.getColumnIndex(DatabaseContract.Subject._ID);
                int col_name = c.getColumnIndex(DatabaseContract.Subject.COLUMN_NAME);
                int col_color = c.getColumnIndex(DatabaseContract.Subject.COLUMN_COLOR);
                int col_teacher = c.getColumnIndex(DatabaseContract.Subject.COLUMN_TEACHER);

                while (c.moveToNext()) {
                    subjects.add(new Subject(c.getInt(col_id), c.getString(col_name), c.getString(col_teacher), c.getInt(col_color)));
                }
            }
            Log.d("getAllSubjects", "ARRAYLIST: " + subjects.toString());
            return subjects;
        } finally {
            if (db != null) {
                db.close();
            }
        }
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

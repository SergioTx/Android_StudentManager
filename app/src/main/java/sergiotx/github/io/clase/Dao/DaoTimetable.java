package sergiotx.github.io.clase.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Task;
import sergiotx.github.io.clase.beans.Timetable;

public class DaoTimetable {

    private Context context;
    private SQLiteHelper conn;

    public DaoTimetable(Context context) {
        this.context = context;
        this.conn = new SQLiteHelper(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    ////////////////////////////////////////////
    // SELECT
    ////////////////////////////////////////////

    public Timetable[][] getFullTimetable() {
        Timetable[][] timetable = new Timetable[5][6];

        Log.d("getFullTimetable", "...");

        SQLiteDatabase db = null;
        long num = 0;
        try {
            this.conn = new SQLiteHelper(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
            db = conn.getWritableDatabase();
            if (db == null) {
                return null;
            } else {
                Cursor c = db.rawQuery(DatabaseContract.Timetable.SQL_GET_ALL_FIELDS, null);

                int col_id = c.getColumnIndex(DatabaseContract.Timetable._ID);
                int col_day = c.getColumnIndex(DatabaseContract.Timetable.COLUMN_DAY);
                int col_subjectid = c.getColumnIndex(DatabaseContract.Timetable.COLUMN_SUBJECTID);
                int col_starthour = c.getColumnIndex(DatabaseContract.Timetable.COLUMN_STARTHOUR);
                int col_endhour = c.getColumnIndex(DatabaseContract.Timetable.COLUMN_ENDHOUR);

                while (c.moveToNext()) {
                    Timetable t = new Timetable();
                    t.setDay(c.getInt(col_day));
                    t.setSubject(getSubjectById(c.getInt(col_subjectid),db));
                    t.setStartHour(c.getInt(col_starthour));
                    t.setEndHour(c.getInt(col_endhour));

                    Log.d("getTimetable",t.toString());

                    int hour = DatabaseContract.Timetable.getHourIndex(t.getStartHour());
                    Log.d("getTimetable","HOUR: " + hour);

                    timetable[t.getDay()][hour] = t;
                }
            }

            return timetable;
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
    public boolean updateTimetable(Timetable timetable) {
        Log.d("updateTimetable", "Timetable: " + timetable.toString());
        SQLiteDatabase db = null;
        try {
            this.conn = new SQLiteHelper(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
            db = conn.getWritableDatabase();
            if (db == null) {
                return false;
            } else {
                ContentValues values = new ContentValues();
                values.put(DatabaseContract.Timetable.COLUMN_SUBJECTID, timetable.getSubject().getId());

                long id = db.update(DatabaseContract.Timetable.TABLE_NAME, values, "_id="+timetable.getId(),null);
            }
            return true;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}

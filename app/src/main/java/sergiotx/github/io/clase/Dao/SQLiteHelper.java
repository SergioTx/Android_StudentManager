package sergiotx.github.io.clase.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;
import sergiotx.github.io.clase.beans.Timetable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);
        addDemoData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //for now, it only deletes old version and creates a new one
        deleteDB(db);
        createDB(db);
        addDemoData(db);
    }

    /*
    PRIVATE FUNCTIONS
     */

    /**
     * PRIVATE: creates all the tables of the database
     *
     * @param db
     */
    private void createDB(SQLiteDatabase db) {
        for (int i = 0; i < DatabaseContract.SQL_CREATE_DB_ARRAY.length; i++) {
            db.execSQL(DatabaseContract.SQL_CREATE_DB_ARRAY[i]);
        }
    }

    /**
     * PRIVATE: Deletes all the tables from the db
     *
     * @param db
     */
    private void deleteDB(SQLiteDatabase db) {
        for (int i = 0; i < DatabaseContract.SQL_DELETE_DB_ARRAY.length; i++) {
            db.execSQL(DatabaseContract.SQL_DELETE_DB_ARRAY[i]);
        }
    }

    private void addDemoData(SQLiteDatabase db) {
        int j = 0;
        Subject[] subjects = {
                new Subject(j++, "EIE", "Demo teacher", R.color.subject_amber),
                new Subject(j++, "SIGE", "Demo teacher", R.color.subject_blue),
                new Subject(j++, "DEIN", "Demo teacher", R.color.subject_bluegrey),
                new Subject(j++, "PROS", "Demo teacher", R.color.subject_brown),
                new Subject(j++, "ADAT", "Demo teacher", R.color.subject_red),
                new Subject(j++, "PROM", "Demo teacher", R.color.subject_green),
        };

        ContentValues content = null;

        for (int i = 0; i < subjects.length; i++) {
            content = new ContentValues();
            content.put(DatabaseContract.Subject.COLUMN_NAME, subjects[i].getName());
            content.put(DatabaseContract.Subject.COLUMN_COLOR, subjects[i].getColor());
            content.put(DatabaseContract.Subject.COLUMN_TEACHER, subjects[i].getTeacher());

            Log.d("addDemoData", subjects[i].getName());

            db.insert(DatabaseContract.Subject.TABLE_NAME, null, content);
        }


        //TODO FIXME those subjects have no ID
        j = 0;
        Timetable[][] timetables = {
            {
                    new Timetable(j++, subjects[1], 0, 800, 900),
                    new Timetable(j++, subjects[0], 0, 900, 1000),
                    new Timetable(j++, subjects[2], 0, 1000, 1100),
                    new Timetable(j++, subjects[3], 0, 1130, 1230),
                    new Timetable(j++, subjects[0], 0, 1230, 1330),
                    new Timetable(j++, subjects[3], 0, 1330, 1430)
            }, {
                    new Timetable(j++, subjects[1], 1, 800, 900),
                    new Timetable(j++, subjects[0], 1, 900, 1000),
                    new Timetable(j++, subjects[4], 1, 1000, 1100),
                    new Timetable(j++, subjects[5], 1, 1130, 1230),
                    new Timetable(j++, subjects[4], 1, 1230, 1330),
                    new Timetable(j++, subjects[0], 1, 1330, 1430)
            }, {
                    new Timetable(j++, subjects[1], 2, 800, 900),
                    new Timetable(j++, subjects[2], 2, 900, 1000),
                    new Timetable(j++, subjects[3], 2, 1000, 1100),
                    new Timetable(j++, subjects[4], 2, 1130, 1230),
                    new Timetable(j++, subjects[5], 2, 1230, 1330),
                    new Timetable(j++, subjects[0], 2, 1330, 1430)
            }, {
                    new Timetable(j++, subjects[0], 3, 800, 900),
                    new Timetable(j++, subjects[2], 3, 900, 1000),
                    new Timetable(j++, subjects[3], 3, 1000, 1100),
                    new Timetable(j++, subjects[4], 3, 1130, 1230),
                    new Timetable(j++, subjects[5], 3, 1230, 1330),
                    new Timetable(j++, subjects[1], 3, 1330, 1430)
            }, {
                    new Timetable(j++, subjects[1], 4, 800, 900),
                    new Timetable(j++, subjects[3], 4, 900, 1000),
                    new Timetable(j++, subjects[4], 4, 1000, 1100),
                    new Timetable(j++, subjects[0], 4, 1130, 1230),
                    new Timetable(j++, subjects[5], 4, 1230, 1330),
                    new Timetable(j++, subjects[0], 4, 1330, 1430)
            }
        };

        for (int i = 0; i < timetables.length; i++) {
            for (int k = 0; k < timetables[i].length; k++) {
                content = new ContentValues();
                content.put(DatabaseContract.Timetable.COLUMN_SUBJECTID, timetables[i][k].getSubject().getId());
                content.put(DatabaseContract.Timetable.COLUMN_DAY, timetables[i][k].getDay());
                content.put(DatabaseContract.Timetable.COLUMN_STARTHOUR, timetables[i][k].getStartHour());
                content.put(DatabaseContract.Timetable.COLUMN_ENDHOUR, timetables[i][k].getEndHour());

                /*Log.d("addDemoData", "DAY: " + i);
                Log.d("addDemoData", "HOUR: " + timetables[i][k].getStartHour());
                Log.d("addDemoData", timetables[i][k].getSubject().getName());*/

                db.insert(DatabaseContract.Timetable.TABLE_NAME, null, content);
            }
        }


    }
}

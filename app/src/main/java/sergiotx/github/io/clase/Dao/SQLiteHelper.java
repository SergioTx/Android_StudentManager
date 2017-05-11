package sergiotx.github.io.clase.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import sergiotx.github.io.clase.R;
import sergiotx.github.io.clase.beans.Subject;

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
                new Subject(j++,"Demo Subject 1","Demo teacher", R.color.subject_amber),
                new Subject(j++,"Demo Subject 2","Demo teacher",R.color.subject_blue),
                new Subject(j++,"Demo Subject 3","Demo teacher",R.color.subject_bluegrey),
                new Subject(j++,"Demo Subject 4","Demo teacher",R.color.subject_brown),
                new Subject(j++,"Demo Subject 5","Demo teacher",R.color.subject_red),
                new Subject(j++,"Demo Subject 6","Demo teacher",R.color.subject_green),
                new Subject(j++,"Demo Subject 7","Demo teacher",R.color.subject_deeppurple)
        };

        ContentValues content = null;

        for (int i = 0; i < subjects.length; i++) {
            content = new ContentValues();
            content.put(DatabaseContract.Subject.COLUMN_NAME, subjects[i].getName());
            content.put(DatabaseContract.Subject.COLUMN_COLOR, subjects[i].getColor());
            content.put(DatabaseContract.Subject.COLUMN_TEACHER, subjects[i].getTeacher());

            Log.d("addDemoData",subjects[i].getName());


            db.insert(DatabaseContract.Subject.TABLE_NAME, null, content);
        }
    }
}

package sergiotx.github.io.clase.Dao;

import android.provider.BaseColumns;


public final class DatabaseContract {
    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 18;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String BOOLEAN_TYPE = " BOOLEAN";
    private static final String COMMA_SEP = ",";
    private static final String FOREIGN_KEY = "FOREIGN KEY ";
    private static final String REFERENCES = " REFERENCES ";

    /* An array list of all the SQL create table statements */
    public static final String[] SQL_CREATE_DB_ARRAY = {
            Subject.CREATE_TABLE,
            Absence.CREATE_TABLE,
            Exam.CREATE_TABLE,
            Task.CREATE_TABLE,
            Timetable.CREATE_TABLE
    };

    public static final String[] SQL_DELETE_DB_ARRAY = {
            Absence.DELETE_TABLE,
            Exam.DELETE_TABLE,
            Task.DELETE_TABLE,
            Timetable.DELETE_TABLE,
            Subject.DELETE_TABLE
    };

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {
    }

    public static abstract class Subject implements BaseColumns {
        public static final String TABLE_NAME = "SUBJECT";

        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_COLOR = "COLOR";
        public static final String COLUMN_TEACHER = "TEACHER";

        public static final String SQL_GET_ALL_FIELDS = "SELECT " + _ID + COMMA_SEP + COLUMN_NAME + COMMA_SEP + COLUMN_COLOR + COMMA_SEP + COLUMN_TEACHER + " FROM " + TABLE_NAME;


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_COLOR + INT_TYPE + COMMA_SEP +
                COLUMN_TEACHER + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    //dates are saved as TEXT: http://stackoverflow.com/questions/7363112/best-way-to-work-with-dates-in-android-sqlite
    public static abstract class Absence implements BaseColumns {
        public static final String TABLE_NAME = "ABSENCE";
        public static final String COLUMN_SUBJECTID = "SUBJECTID";
        public static final String COLUMN_DATE = "DATE";
        public static final String COLUMN_HOURS = "HOURS";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_SUBJECTID + INT_TYPE + COMMA_SEP +
                COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_HOURS + INT_TYPE + COMMA_SEP +
                FOREIGN_KEY + "(" + COLUMN_SUBJECTID + ")" + REFERENCES + Subject.TABLE_NAME + "(" + Subject._ID + ") )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Exam implements BaseColumns {
        public static final String TABLE_NAME = "EXAM";
        public static final String COLUMN_SUBJECTID = "SUBJECTID";
        public static final String COLUMN_DATE = "DATE";
        public static final String COLUMN_HOURS = "HOURS";
        public static final String COLUMN_MARK = "MARK";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_SUBJECTID + INT_TYPE + COMMA_SEP +
                COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_HOURS + INT_TYPE + COMMA_SEP +
                COLUMN_MARK + FLOAT_TYPE + COMMA_SEP +
                FOREIGN_KEY + "(" + COLUMN_SUBJECTID + ")" + REFERENCES + Subject.TABLE_NAME + "(" + Subject._ID + ") )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Task implements BaseColumns {
        public static final String TABLE_NAME = "TASK";
        public static final String COLUMN_SUBJECTID = "SUBJECTID";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_DATE = "DATE";
        public static final String COLUMN_COMPLETED = "COMPLETED";
        public static final String COLUMN_REMINDER = "REMINDER";

        public static final String SQL_GET_ALL_FIELDS = "SELECT " + _ID + COMMA_SEP + COLUMN_NAME + COMMA_SEP + COLUMN_SUBJECTID + COMMA_SEP + COLUMN_DATE + COMMA_SEP + COLUMN_COMPLETED + COMMA_SEP + COLUMN_REMINDER + " FROM " + TABLE_NAME;

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_SUBJECTID + INT_TYPE + COMMA_SEP +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_COMPLETED + TEXT_TYPE + COMMA_SEP +
                COLUMN_REMINDER + BOOLEAN_TYPE + COMMA_SEP +
                FOREIGN_KEY + "(" + COLUMN_SUBJECTID + ")" + REFERENCES + Subject.TABLE_NAME + "(" + Subject._ID + ") )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    //Day is 0-6 (day of the week)
    public static abstract class Timetable implements BaseColumns {
        public static final String TABLE_NAME = "TIMETABLE";
        public static final String COLUMN_SUBJECTID = "SUBJECTID";
        public static final String COLUMN_DAY = "DAY";
        public static final String COLUMN_STARTHOUR = "STARTHOUR";
        public static final String COLUMN_ENDHOUR = "ENDHOUR";

        public static final int[] TIMETABLE_TIMES = {800, 900, 1000, 1130, 1230, 1330};


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_SUBJECTID + INT_TYPE + COMMA_SEP +
                COLUMN_DAY + INT_TYPE + COMMA_SEP +
                COLUMN_STARTHOUR + INT_TYPE + COMMA_SEP +
                COLUMN_ENDHOUR + INT_TYPE + COMMA_SEP +
                FOREIGN_KEY + "(" + COLUMN_SUBJECTID + ")" + REFERENCES + Subject.TABLE_NAME + "(" + Subject._ID + ") )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_GET_ALL_FIELDS = "SELECT " + _ID + COMMA_SEP + COLUMN_DAY + COMMA_SEP + COLUMN_SUBJECTID + COMMA_SEP + COLUMN_STARTHOUR + COMMA_SEP + COLUMN_ENDHOUR + " FROM " + TABLE_NAME;

        public static final int getHourIndex(int hour) {
            int index = -1;
            for (int i = 0; i < TIMETABLE_TIMES.length && index < 0; i++) {
                if (TIMETABLE_TIMES[i] == hour){
                    index = i;
                }
            }
            return index;
        }
    }
}

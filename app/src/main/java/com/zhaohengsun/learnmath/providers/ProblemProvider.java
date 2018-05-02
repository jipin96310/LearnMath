package com.zhaohengsun.learnmath.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.zhaohengsun.learnmath.contracts.ChildrenContract;
import com.zhaohengsun.learnmath.contracts.ProblemContract;
import com.zhaohengsun.learnmath.contracts.RewardContract;
import com.zhaohengsun.learnmath.contracts.TaskContract;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class ProblemProvider extends ContentProvider {

    public ProblemProvider() {
    }

    private static final String AUTHORITY = ProblemContract.AUTHORITY;

    private static final String CONTENT_PATH = ProblemContract.CONTENT_PATH;

    private static final String CONTENT_PATH_ITEM = ProblemContract.CONTENT_PATH_ITEM;

    private static final String CHILD_CONTENT_PATH = ChildrenContract.CONTENT_PATH;
    private static final String CHILD_CONTENT_PATH_ITEM = ChildrenContract.CONTENT_PATH_ITEM;

    private static final String TASK_CONTENT_PATH = TaskContract.CONTENT_PATH;

    private static final String TASK_CONTENT_PATH_ITEM = TaskContract.CONTENT_PATH_ITEM;

    private static final String REWARD_CONTENT_PATH = RewardContract.CONTENT_PATH;

    private static final String REWARD_CONTENT_PATH_ITEM = RewardContract.CONTENT_PATH_ITEM;



    private static final String DATABASE_NAME = "problems.db";

    private static final int DATABASE_VERSION = 1;

    private static final String PROBLEMS_TABLE = "problems";



    public static  final String _ID = "_id";

    public static final String HEAD = "head";

    public static final String BODY = "body";

    public static final String SCORE = "score";

    public static final String DIFFICULTY = "difficulty";

    public static final String CATEGORY = "category";


    private static final String CHILDREN_TABLE = "children";

    public static final String _ID2 = "_id";

    public static final String NAME = "name";

    public static final String SCHOOLTYPE = "schooltype";

    public static final String POINTS = "points";


    private static final String TASKS_TABLE = "tasks";
    public static final String _ID3 = "_id";
    public static final String STUDENTNAME = "studentname";
    public static final String PROBLEMS = "problems";
    public static final String ISFINISHED = "isfinished";
    public static final String TIMELIMIT = "timelimit";


    private static final String REWARDS_TABLE = "rewards";
    public static final String _ID4 = "_id";
    public static final String REWARD_NAME = "reward_name";
    public static final String REWARD_POINTS = "reward_points";
    public static final String RECIPIENT_NAME = "recipient_name";





    //public static final String book_fk = "book_fk";


    // Create the constants used to differentiate between the different URI  requests.
    private static final int ALL_ROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final int CHILDREN_ALL_ROWS = 3;
    private static final int CHILDREN_SINGLE_ROW = 4;
    private static final int TASK_ALL_ROW = 5;
    private static final int TASK_SINGLE_ROW = 6;
    private static final int REWARD_ALL_ROW = 7;
    private static final int REWARD_SINGLE_ROW = 8;

    public static class DbHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table " + PROBLEMS_TABLE + " ("
                + _ID + " integer primary key, "
                +HEAD + " text not null, "
                +BODY + " text not null, "
                +SCORE + " integer not null, "
                +DIFFICULTY + " integer not null, "
                +CATEGORY + " text not null "
                +")";

        private static final String DATABASE_CREATE2 = "create table " + CHILDREN_TABLE + " ("
                + _ID2 + " integer primary key, "
                +NAME + " text not null, "
                +SCHOOLTYPE + " text not null, "
                +POINTS + " integer not null "
                +")";


        private static final String DATABASE_CREATE3 = "create table " + TASKS_TABLE + " ("
                + _ID3 + " integer primary key, "
                +STUDENTNAME + " text not null, "
                +PROBLEMS + " text not null, "
                +ISFINISHED + " text not null, "
                +TIMELIMIT + " integer not null "

                +")";
        private static final String DATABASE_CREATE4 = "create table " + REWARDS_TABLE + " ("
                + _ID4 + " integer primary key, "
                +REWARD_NAME + " text not null, "
                +REWARD_POINTS + " integer not null, "
                +RECIPIENT_NAME + " text not null "
                +")";


        //private static final String extra =
              //  "CREATE INDEX AuthorsBookIndex ON Authors(book_fk); ";


        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //  initialize database tables
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE2);
            db.execSQL(DATABASE_CREATE3);

            db.execSQL(DATABASE_CREATE4);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //  upgrade database if necessary
            db.execSQL("DROP TABLE IF EXISTS " + PROBLEMS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CHILDREN_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + REWARDS_TABLE);
        }
    }

    private DbHelper dbHelper;

    @Override
    public boolean onCreate() {
        // Initialize your content provider on startup.
        dbHelper = new DbHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        return true;
    }

    // Used to dispatch operation based on URI
    private static final UriMatcher uriMatcher;

    // uriMatcher.addURI(AUTHORITY, CONTENT_PATH, OPCODE)
    //below TODO
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CONTENT_PATH, ALL_ROWS);
        uriMatcher.addURI(AUTHORITY, CONTENT_PATH_ITEM, SINGLE_ROW);
        uriMatcher.addURI(AUTHORITY, CHILD_CONTENT_PATH, CHILDREN_ALL_ROWS);
        uriMatcher.addURI(AUTHORITY,CHILD_CONTENT_PATH_ITEM,CHILDREN_SINGLE_ROW);
        uriMatcher.addURI(AUTHORITY, TASK_CONTENT_PATH, TASK_ALL_ROW);
        uriMatcher.addURI(AUTHORITY,TASK_CONTENT_PATH_ITEM,TASK_SINGLE_ROW);
        uriMatcher.addURI(AUTHORITY, REWARD_CONTENT_PATH, REWARD_ALL_ROW);
        uriMatcher.addURI(AUTHORITY,REWARD_CONTENT_PATH_ITEM,REWARD_SINGLE_ROW);


    }

    @Override
    public String getType(Uri uri) {
        //  Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case ALL_ROWS:
                return CONTENT_PATH;
            case SINGLE_ROW:
                return CONTENT_PATH_ITEM ;
            case CHILDREN_ALL_ROWS:
                return CHILD_CONTENT_PATH;
            case CHILDREN_SINGLE_ROW:
                return CHILD_CONTENT_PATH_ITEM;
            case TASK_ALL_ROW:
                return TASK_CONTENT_PATH;
            case TASK_SINGLE_ROW:
                return TASK_CONTENT_PATH_ITEM;
            case REWARD_ALL_ROW:
                return REWARD_CONTENT_PATH;

            case REWARD_SINGLE_ROW:
                return REWARD_CONTENT_PATH_ITEM;

            default: throw new IllegalArgumentException
                    ("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_ROWS:
                long row = db.insert(PROBLEMS_TABLE, null, values);

                if (row > 0) {
                    Uri instanceUri = ProblemContract.CONTENT_URI(row);
                    ContentResolver cr = getContext().getContentResolver();

                    cr.notifyChange(uri, null);

                    return instanceUri;
                }
                return null;
            case SINGLE_ROW:
                throw new IllegalArgumentException("insert expects a whole-table URI");


            case CHILDREN_ALL_ROWS:
                row = db.insert(CHILDREN_TABLE,null,values);
                if(row > 0){
                    Uri instanceUri = ChildrenContract.CONTENT_URI(row);
                    ContentResolver cr = getContext().getContentResolver();

                    cr.notifyChange(uri, null);

                    return instanceUri;

                }

            return null;

            case TASK_ALL_ROW:
                row = db.insert(TASKS_TABLE,null,values);
                if(row > 0){
                    Uri instanceUri = TaskContract.CONTENT_URI(row);
                    ContentResolver cr = getContext().getContentResolver();

                    cr.notifyChange(uri, null);

                    return instanceUri;

                }

                return null;

            case REWARD_ALL_ROW:
                row = db.insert(REWARDS_TABLE,null,values);
                if(row > 0){
                    Uri instanceUri = TaskContract.CONTENT_URI(row);
                    ContentResolver cr = getContext().getContentResolver();

                    cr.notifyChange(uri, null);

                    return instanceUri;

                }

                return null;



            default:
                throw new IllegalStateException("insert: bad case");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_ROWS:

                projection = new String[]{_ID,HEAD, BODY, SCORE, DIFFICULTY,CATEGORY};

                  return db.query(PROBLEMS_TABLE, projection, selection,selectionArgs, null, null, sortOrder);
            case SINGLE_ROW:

                projection = new String[]{_ID,HEAD, BODY, SCORE, DIFFICULTY,CATEGORY};
                selection = ProblemContract.ID + "=?";
                selectionArgs = new String[]{getType(uri)};
                return db.query(PROBLEMS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);

            case CHILDREN_ALL_ROWS:

                projection = new String[]{_ID2,NAME,SCHOOLTYPE,POINTS};
                return db.query(CHILDREN_TABLE, projection, null,null, null, null, sortOrder);

            case CHILDREN_SINGLE_ROW:
                projection = new String[]{_ID2,NAME,SCHOOLTYPE,POINTS};
                selection = ChildrenContract.ID + "=?";
                selectionArgs = new String[1];
                selectionArgs[0] = String.valueOf( ChildrenContract.getId(uri));
                return db.query(CHILDREN_TABLE, projection, selection, selectionArgs, null, null, sortOrder);

            case TASK_ALL_ROW:

                projection =    new String[]{TaskContract.ID,TaskContract.STUDENTNAME, TaskContract.PROBLEMS,TaskContract.ISFINISHED,TaskContract.TIMELIMIT};
                return db.query(TASKS_TABLE, projection, selection,selectionArgs, null, null, sortOrder);

            case TASK_SINGLE_ROW:

                projection =    new String[]{TaskContract.ID,TaskContract.STUDENTNAME, TaskContract.PROBLEMS,TaskContract.ISFINISHED,TaskContract.TIMELIMIT};
                return db.query(TASKS_TABLE, projection, selection,selectionArgs, null, null, sortOrder);


            case REWARD_ALL_ROW:

                projection =    new String[]{RewardContract.ID,RewardContract.REWARD_NAME,RewardContract.REWARD_POINTS,RewardContract.RECIPIENT_NAME};
                return db.query(REWARDS_TABLE, projection, selection,selectionArgs, null, null, sortOrder);

            case REWARD_SINGLE_ROW:

                projection =    new String[]{RewardContract.ID,RewardContract.REWARD_NAME,RewardContract.REWARD_POINTS,RewardContract.RECIPIENT_NAME};
                return db.query(REWARDS_TABLE, projection, selection,selectionArgs, null, null, sortOrder);



            default:
                throw new IllegalStateException("insert: bad case");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case CHILDREN_SINGLE_ROW:
                selection = ChildrenContract.ID + "=?";
                selectionArgs = new String[]{String.valueOf(ChildrenContract.getId(uri))};

                return db.update(CHILDREN_TABLE, values, selection, selectionArgs);
            case TASK_SINGLE_ROW:
                selection = TaskContract.ID + "=?";
                selectionArgs = new String[]{String.valueOf(TaskContract.getId(uri))};

                return db.update(TASKS_TABLE, values, selection, selectionArgs);
            case REWARD_SINGLE_ROW:
                selection = RewardContract.ID + "=?";
                selectionArgs = new String[]{String.valueOf(RewardContract.getId(uri))};
                
                return db.update(REWARDS_TABLE, values, selection, selectionArgs);

            default: throw new IllegalArgumentException
                    ("Unsupported URI:" + uri);
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_ROWS:

                return  db.delete(PROBLEMS_TABLE,null,null);

            case SINGLE_ROW:
                selection = ProblemContract.ID + "=?";
                return db.delete(PROBLEMS_TABLE,selection,selectionArgs);

            case CHILDREN_ALL_ROWS:

                return  db.delete(CHILDREN_TABLE,null,null);

            case CHILDREN_SINGLE_ROW:

                selection = ChildrenContract.ID + "=?";

                return db.delete(CHILDREN_TABLE,selection,selectionArgs);

            case TASK_ALL_ROW:
                return  db.delete(TASKS_TABLE,null,null);

            case TASK_SINGLE_ROW:

                selection = TaskContract.ID + "=?";
                selectionArgs = new String[]{String.valueOf(TaskContract.getId(uri))};
                return db.delete(TASKS_TABLE,selection,selectionArgs);

            case REWARD_ALL_ROW:
                return  db.delete(REWARDS_TABLE,null,null);

            case REWARD_SINGLE_ROW:

                selection = RewardContract.ID + "=?";
                selectionArgs = new String[]{String.valueOf(RewardContract.getId(uri))};
                return db.delete(REWARDS_TABLE,selection,selectionArgs);

            default: throw new IllegalArgumentException
                    ("Unsupported URI:" + uri);
        }
    }




}

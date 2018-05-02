package com.zhaohengsun.learnmath.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.regex.Pattern;

/**
 * Created by Zhaoheng Sun on 2018/2/25.
 */

public class TaskContract {


    public static final String AUTHORITY = "com.zhSun.learnMath";

    public static final Uri CONTENT_URI(String authority, String path) {
        return new Uri.Builder().scheme("content")
                .authority(authority)
                .path(path)
                .build();
    }

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Task");

    public static Uri withExtendedPath(Uri uri,
                                       String... path) {
        Uri.Builder builder = uri.buildUpon();
        for (String p : path)
            builder.appendPath(p);
        return builder.build();
    }
    public static final Uri CONTENT_URI(long id) {
        return CONTENT_URI(Long.toString(id));
    }

    public static final Uri CONTENT_URI(String id) {
        return withExtendedPath(CONTENT_URI, id);
    }

    public static final long getId(Uri uri) {
        return Long.parseLong(uri.getLastPathSegment());
    }

    public static final String CONTENT_PATH(Uri uri) {
        return uri.getPath().substring(1);
    }

    public static final String CONTENT_PATH = CONTENT_PATH(CONTENT_URI);

    public static final String CONTENT_PATH_ITEM = CONTENT_PATH(CONTENT_URI("#"));


    public static final String ID = "_id";



    public static final String STUDENTNAME = "studentname";
    public static final String PROBLEMS = "problems";
    public static final String ISFINISHED = "isfinished";
    public static final String TIMELIMIT = "timelimit";
    public static final char SEPARATOR_CHAR = '|';

    private static final Pattern SEPARATOR =
            Pattern.compile(Character.toString(SEPARATOR_CHAR), Pattern.LITERAL);

    public static String[] readStringArray(String in) {
        return SEPARATOR.split(in);
    }

    private static int idColumn = -1;

    public static long getId(Cursor cursor) {
        if (idColumn < 0) {
            idColumn =  cursor.getColumnIndexOrThrow(ID);
        }
        return cursor.getLong(idColumn);


    }
    public static void putId(ContentValues values, long id) {
        values.put(ID, id);
    }



    private static int studentnameColumn = -1;

    public static String getStudentName(Cursor cursor) {
        if (studentnameColumn < 0) {
            studentnameColumn =  cursor.getColumnIndexOrThrow(STUDENTNAME);
        }
        return  cursor.getString(studentnameColumn);
    }
    public static void putStudentName(ContentValues values, String studentname) {
        values.put(STUDENTNAME, studentname);
    }


    private static int problemsColumn = -1;

    public static String[] getProblems(Cursor cursor) {
        if (problemsColumn < 0) {
            problemsColumn =  cursor.getColumnIndexOrThrow(PROBLEMS);
        }
        return readStringArray(cursor.getString(problemsColumn));
    }
    public static void putProblems(ContentValues values, String problems) {values.put(PROBLEMS, problems);}


    private static int isfinisedColumn = -1;

    public static String getIsFinished(Cursor cursor) {
        if (isfinisedColumn < 0) {
            isfinisedColumn =  cursor.getColumnIndexOrThrow(ISFINISHED);
        }
        return  cursor.getString(isfinisedColumn);
    }
    public static void putIsFinished(ContentValues values, String isfinished) {
        values.put(ISFINISHED, isfinished);
    }

    private static int timelimitColumn = -1;

    public static long getTimelimit(Cursor cursor) {
        if (timelimitColumn < 0) {
            timelimitColumn =  cursor.getColumnIndexOrThrow(TIMELIMIT);
        }
        return  cursor.getLong(timelimitColumn);
    }
    public static void putTimelimit(ContentValues values, long timelimit) {
        values.put(TIMELIMIT, timelimit);
    }





}

package com.zhaohengsun.learnmath.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class ChildrenContract {

    public static final String AUTHORITY = "com.zhSun.learnMath";

    public static final Uri CONTENT_URI(String authority, String path) {
        return new Uri.Builder().scheme("content")
                .authority(authority)
                .path(path)
                .build();
    }

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Children");

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

    public static final String NAME = "name";

    public static final String SCHOOLTYPE = "schooltype";

    public static final String POINTS = "points";


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


    private static int nameColumn = -1;

    public static String getName(Cursor cursor) {
        if (nameColumn < 0) {
            nameColumn =  cursor.getColumnIndexOrThrow(NAME);
        }
        return  cursor.getString(nameColumn);
    }
    public static void putName(ContentValues values, String name) {
        values.put(NAME, name);
    }


    private static int schooltypeColumn = -1;

    public static String getSchooltype(Cursor cursor) {
        if (schooltypeColumn < 0) {
            schooltypeColumn =  cursor.getColumnIndexOrThrow(SCHOOLTYPE);
        }
        return  cursor.getString(schooltypeColumn);
    }
    public static void putSchooltype(ContentValues values, String schooltype) {
        values.put(SCHOOLTYPE, schooltype);
    }

    private static int pointsColumn = -1;

    public static long getPoints(Cursor cursor) {
        if (pointsColumn < 0) {
            pointsColumn =  cursor.getColumnIndexOrThrow(POINTS);
        }
        return  cursor.getLong(pointsColumn);
    }
    public static void putPoints(ContentValues values, long points) {
        values.put(POINTS, points);
    }






}

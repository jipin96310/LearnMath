package com.zhaohengsun.learnmath.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Zhaoheng Sun on 2018/2/24.
 */

public class ProblemContract {

    public static final String AUTHORITY = "com.zhSun.learnMath";

    public static final Uri CONTENT_URI(String authority, String path) {
        return new Uri.Builder().scheme("content")
                .authority(authority)
                .path(path)
                .build();
    }

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Problem");

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

    public static final String HEAD = "head";

    public static final String BODY = "body";

    public static final String SCORE = "score";

    public static final String DIFFICULTY = "difficulty";

    public static final String CATEGORY = "category";

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


    private static int headColumn = -1;

    public static String getHead(Cursor cursor) {
        if (headColumn < 0) {
            headColumn =  cursor.getColumnIndexOrThrow(HEAD);
        }
        return  cursor.getString(headColumn);
    }
    public static void putHead(ContentValues values, String head) {
        values.put(HEAD, head);
    }

    private static int bodyColumn = -1;

    public static String getBody(Cursor cursor) {
        if (bodyColumn < 0) {
            bodyColumn =  cursor.getColumnIndexOrThrow(BODY);
        }
        return  cursor.getString(bodyColumn);
    }
    public static void putBody(ContentValues values, String body) {
        values.put(BODY, body);
    }



    private static int scoreColumn = -1;

    public static long getScore(Cursor cursor) {
        if (scoreColumn < 0) {
            scoreColumn =  cursor.getColumnIndexOrThrow(SCORE);
        }
        return  cursor.getLong(scoreColumn);
    }
    public static void putScore(ContentValues values, long score) {
        values.put(SCORE, score);
    }



    private static int difficultyColumn = -1;

    public static long getDifficulty(Cursor cursor) {
        if (difficultyColumn < 0) {
            difficultyColumn =  cursor.getColumnIndexOrThrow(DIFFICULTY);
        }
        return  cursor.getLong(difficultyColumn);
    }
    public static void putDifficulty(ContentValues values, long difficulty) {
        values.put(DIFFICULTY,difficulty );
    }

    private static int categotyColumn = -1;

    public static String getCategory(Cursor cursor) {
        if (categotyColumn < 0) {
            categotyColumn =  cursor.getColumnIndexOrThrow(CATEGORY);
        }
        return  cursor.getString(headColumn);
    }
    public static void putCategory(ContentValues values, String category) {
        values.put(CATEGORY, category);
    }





}

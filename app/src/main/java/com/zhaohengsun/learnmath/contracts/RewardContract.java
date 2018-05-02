package com.zhaohengsun.learnmath.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.regex.Pattern;

/**
 * Created by Zhaoheng Sun on 2018/4/24.
 */

public class RewardContract {


    public static final String AUTHORITY = "com.zhSun.learnMath";

    public static final Uri CONTENT_URI(String authority, String path) {
        return new Uri.Builder().scheme("content")
                .authority(authority)
                .path(path)
                .build();
    }

    public static final Uri CONTENT_URI = CONTENT_URI(AUTHORITY, "Reward");

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
    public static final String REWARD_NAME = "reward_name";
    public static final String REWARD_POINTS = "reward_points";
    public static final String RECIPIENT_NAME = "recipient_name";

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



    private static int rewardnameColumn = -1;

    public static String getRewardName(Cursor cursor) {
        if (rewardnameColumn < 0) {
            rewardnameColumn =  cursor.getColumnIndexOrThrow(REWARD_NAME);
        }
        return  cursor.getString(rewardnameColumn);
    }
    public static void putRewardName(ContentValues values, String rewardname) {
        values.put(REWARD_NAME, rewardname);
    }

    private static int rewardpointsColumn = -1;

    public static long getRewardPoints(Cursor cursor) {
        if (rewardpointsColumn < 0) {
            rewardpointsColumn =  cursor.getColumnIndexOrThrow(REWARD_POINTS);
        }
        return  cursor.getLong(rewardpointsColumn);
    }
    public static void putRewardPoints(ContentValues values, long rewardpoints) {
        values.put(REWARD_POINTS, rewardpoints);
    }


    private static int recipientnameColumn = -1;
    public static String getRecipientName(Cursor cursor) {
        if (recipientnameColumn < 0) {
            recipientnameColumn =  cursor.getColumnIndexOrThrow(RECIPIENT_NAME);
        }
        return  cursor.getString(recipientnameColumn);
    }
    public static void putRecipientName(ContentValues values, String recipientname) {
        values.put(RECIPIENT_NAME, recipientname);
    }




}

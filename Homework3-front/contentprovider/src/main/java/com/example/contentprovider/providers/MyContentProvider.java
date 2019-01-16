package com.example.contentprovider.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.contentprovider.data.MyContract;

public class MyContentProvider extends ContentProvider {

    public static final int LIST = 100;
    public static final int LIST_WITH_ID = 101;

    public static final UriMatcher myUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MyContract.AUTHORITY, MyContract.PATH_NAME, LIST);
        uriMatcher.addURI(MyContract.AUTHORITY, MyContract.PATH_NAME + "/#", LIST_WITH_ID);

        return uriMatcher;
    }

    private MyDbOpenHelper MyDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        MyDbHelper = new MyDbOpenHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = MyDbHelper.getReadableDatabase();
        int match = myUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {
            case LIST:
                returnCursor = db.query(MyContract.WaitlistEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = MyDbHelper.getWritableDatabase();

        int match = myUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case LIST:
                long id = db.insert(MyContract.WaitlistEntry.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = ContentUris.withAppendedId(MyContract.WaitlistEntry.CONTENT_URI, id);
                else
                    throw new android.database.SQLException("Fail to insert " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknow uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = MyDbHelper.getWritableDatabase();

        int status; // starts as 0

        String id = uri.getPathSegments().get(1);
        status = db.delete(MyContract.WaitlistEntry.TABLE_NAME, MyContract.WaitlistEntry._ID + "=?", new String[]{id});

        if (status != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return status;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

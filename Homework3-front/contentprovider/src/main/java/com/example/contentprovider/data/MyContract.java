package com.example.contentprovider.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MyContract {
    public static final String AUTHORITY = "com.example.contentprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_NAME = "list";

    public static final class WaitlistEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_NAME).build();

        public static final String TABLE_NAME = "waitList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PEOPLE = "people";

    }
}
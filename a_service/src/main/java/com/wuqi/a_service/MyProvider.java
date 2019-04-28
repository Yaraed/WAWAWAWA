package com.wuqi.a_service;

import android.app.SearchManager;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.Telephony.Sms;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;

/**
 * @author wuqi by 2019/4/23.
 */
public class MyProvider extends SearchRecentSuggestionsProvider {
    //指定查询的authority
    public final static String AUTHORITY = "com.wuqi.a_service.MyProvider";
    //指定数据库的操作是查询的方式
    public final static int MODE = DATABASE_MODE_QUERIES;
    public MyProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    //然后重写SearchRecentSuggestionsProvider中的方法，有增删改查四个方法
    private final static String[] sms_projection = new String[]{Sms._ID,Sms.ADDRESS,Sms.BODY};

    private final static String[] columnNames = new String[]{BaseColumns._ID,
            SearchManager.SUGGEST_COLUMN_TEXT_1,   //指定搜索自动提示的框的样式
            SearchManager.SUGGEST_COLUMN_TEXT_2,
            SearchManager.SUGGEST_COLUMN_QUERY};    //这个参数能够让点击某个搜索提示的时候自动让搜索内容变成点击的条目的内容

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(selectionArgs != null){
            String query = selectionArgs[0];
            if(TextUtils.isEmpty(query)){
                return null;
            }

            Uri uri1 = Sms.CONTENT_URI;
            String where = Sms.BODY + " like '%" + query + "%'";
            Cursor cursor = getContext().getContentResolver().query(uri1, sms_projection, where, null, Sms.DATE + " desc ");
            return changeCursor(cursor);
        }
        return null;
    }

    private Cursor changeCursor(Cursor cursor){
        MatrixCursor result = new MatrixCursor(columnNames);
        if(cursor != null){
            while(cursor.moveToNext()){
                Object[] columnValues = new Object[]{cursor.getString(cursor.getColumnIndex(Sms._ID)),
                        cursor.getString(cursor.getColumnIndex(Sms.ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(Sms.BODY)),
                        cursor.getString(cursor.getColumnIndex(Sms.BODY))};  //点击时候让内容变为短信内容
                result.addRow(columnValues);
            }
        }
        return result;
    }
}

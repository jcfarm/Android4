package com.example.zhangnan.myfarm.DBCache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangnan on 17/6/21.
 */

public class FieldsBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "fieldsBase.db";

    public FieldsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + FieldsDbSchema.FieldsTable.NAME + "(" +
                FieldsDbSchema.FieldsTable.Cols.ID + " PRIMARY KEY UNIQUE," +
                FieldsDbSchema.FieldsTable.Cols.DATE + "," +
                FieldsDbSchema.FieldsTable.Cols.JSON +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

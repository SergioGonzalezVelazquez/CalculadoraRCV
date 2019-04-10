package ipo2.es.calculadorarcv.persistencia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DB_SQLiteHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "rcv_bbdd";
    private static final int DATABASE_VERSION = 1;

    public DB_SQLiteHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(),
                null, DATABASE_VERSION);
        //super(context, name, factory, version);
    }

}
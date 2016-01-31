package za.co.moneymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import za.co.moneymanager.util.DBConstants;

/**
 * Created by michaeljacobs on 9/9/2015.
 */
public class MoneyManSQLiteHelper extends SQLiteOpenHelper{

    public MoneyManSQLiteHelper(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstants.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MoneyManSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_NEW_SMS_TRANSACTION);
        onCreate(db);
    }
}

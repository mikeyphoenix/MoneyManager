package za.co.moneymanager.util;

/**
 * Created by michaeljacobs on 9/9/2015.
 */
public abstract class DBConstants {
    public static final String DATABASE_NAME = "commments.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NEW_SMS_TRANSACTION = "newsmstransaction";
    public static final String TABLE_NEW_SMS_TRANSACTION_COLUMN_ID = "_id";
    public static final String TABLE_NEW_SMS_TRANSACTION_COLUMN_COMMENT = "comment";


    // Database creation sql statement
    public static final String DATABASE_CREATE = "create table "
            + TABLE_NEW_SMS_TRANSACTION + "(" + TABLE_NEW_SMS_TRANSACTION_COLUMN_ID
            + " integer primary key autoincrement, " + TABLE_NEW_SMS_TRANSACTION_COLUMN_COMMENT
            + " text not null);";
}

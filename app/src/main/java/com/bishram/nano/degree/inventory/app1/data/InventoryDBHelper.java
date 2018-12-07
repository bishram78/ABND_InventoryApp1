package com.bishram.nano.degree.inventory.app1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bishram.nano.degree.inventory.app1.data.InventoryContract.InventoryEntry;
/**
 * Created By Bishram Munda;
 * Created on Fri 07/12/2018 04:18H;
 */
public class InventoryDBHelper extends SQLiteOpenHelper {

    /* NAME OF THE DATABASE FILE */
    private static final String DATABASE_NAME = "inventory.db";

    /**
     * DATABASE VERSION. If you change the database schema, you must increment the database version;
     */
    private static final int DATABASE_VERSION = 1;

    public InventoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Create TABLE inventory */
        // Create a String that contains the SQL statement to create the inventory table;
        String SQL_CREATE_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + "("
                + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT DEFAULT 'PRODUCT_NAME', "
                + InventoryEntry.COLUMN_PRICE + " REAL DEFAULT 0.00, "
                + InventoryEntry.COLUMN_QUANTITY + " INTEGER DEFAULT 0, "
                + InventoryEntry.COLUMN_SUPPLIER + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_SUPPLIER_CONTACT + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}

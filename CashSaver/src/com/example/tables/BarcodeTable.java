package com.example.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BarcodeTable
{
	// Database table
	public static final String TABLE_BARCODE = "barcode_table";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PRODUCT_ID = "product_id";
	public static final String COLUMN_BARCODE = "barcode";

	public static String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_PRODUCT_ID, COLUMN_BARCODE};

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " + TABLE_BARCODE
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_PRODUCT_ID + " iteger,"
			+ COLUMN_BARCODE+ " text"
			+ ");";

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(PriceTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODE);
		onCreate(database);
	}
}

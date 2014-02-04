package com.example.tables;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProductTable
{
	// Database table
	public static final String TABLE_PRODUCT = "product";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CATEGORY_ID = "category_id";
	public static final String COLUMN_BARCODE = "barcode";
		

	// Database creation SQL statement
	private static final String DATABASE_CREATE ="create table " + TABLE_PRODUCT
			+"(" 
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text,"
			+ COLUMN_CATEGORY_ID + " integer,"
			+ COLUMN_BARCODE + " text"
			+ ");"
			;

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(ProductTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		onCreate(database);
	}
}

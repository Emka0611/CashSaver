package com.example.tables;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PriceTable
{
	// Database table
	public static final String TABLE_PRICE = "price";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PRODUCT_ID = "product_id";
	public static final String COLUMN_PRICE_VALUE = "price_value";
	public static final String COLUMN_QUANTITY = "quantity";
	public static final String COLUMN_UNIT_ID = "unit_id";
	public static final String COLUMN_DATE = "created_at";
	
	public static String[] ALL_COLUMNS = {
			COLUMN_ID,
			COLUMN_PRODUCT_ID,
			COLUMN_PRICE_VALUE,
			COLUMN_QUANTITY,
			COLUMN_UNIT_ID,
			COLUMN_DATE
			};

	// Database creation SQL statement
	private static final String DATABASE_CREATE ="create table " + TABLE_PRICE
			+"(" 
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_PRODUCT_ID + " iteger,"
			+ COLUMN_PRICE_VALUE + " double,"
			+ COLUMN_QUANTITY + " double,"
			+ COLUMN_UNIT_ID + " integer,"
			+ COLUMN_DATE + " datetime"
			+ ");"
			;

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(PriceTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PRICE);
		onCreate(database);
	}
}


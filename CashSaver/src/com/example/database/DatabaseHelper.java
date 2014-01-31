package com.example.database;

import java.text.SimpleDateFormat;
import java.util.*;

import android.content.Context;
import android.database.sqlite.*;

import com.example.tables.*;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static Context context;
	private static final String DATABASE_NAME = "productdatabase.db";
	private static final int DATABASE_VERSION = 1;
	

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		DatabaseHelper.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		ProductTable.onCreate(database);
		CategoryTable.onCreate(database);
		UnitTable.onCreate(database);
		PriceTable.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		ProductTable.onUpgrade(database, oldVersion, newVersion);
		CategoryTable.onUpgrade(database, oldVersion, newVersion);
		UnitTable.onUpgrade(database, oldVersion, newVersion);
		PriceTable.onUpgrade(database, oldVersion, newVersion);
	}

	public static String getDateTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void dropDatabase()
	{
		DatabaseHelper.context.deleteDatabase("productdatabase.db");
	}

}

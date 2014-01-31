package com.example.database;

import android.content.Context;
import com.example.tables.*;
import android.database.sqlite.*;

public class DatabaseHelper extends SQLiteOpenHelper
{
	
	private static final String DATABASE_NAME = "productdatabase.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
	
}

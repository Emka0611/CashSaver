package com.example.database;

import com.example.tables.CategoryTable;
import com.example.tables.ProductTable;
import com.example.tables.UnitTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
	CategoriesDataSource categoriesDataSource;
	ProductsDataSource productsDataSource;
	UnitsDataSource unitsDataSource;
	
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
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		ProductTable.onUpgrade(database, oldVersion, newVersion);
		CategoryTable.onUpgrade(database, oldVersion, newVersion);
		UnitTable.onUpgrade(database, oldVersion, newVersion);
	}
	
	

}

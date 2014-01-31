package com.example.database.datasource;

import java.util.*;

import com.example.database.DatabaseDataSources;
import com.example.database.DatabaseHelper;
import com.example.products.*;
import com.example.tables.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;

public class PricesDataSource
{
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public PricesDataSource(Context context)
	{
		dbHelper = new DatabaseHelper(context);
		
		Float[] prices = new Float[] { (float) 1.2 , (float) 1.4, (float) 3.5 };
		open();
		createPrice(prices[0], 1);
		createPrice(prices[1], 2);
		createPrice(prices[2], 3);
		close();
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}

	public Price createPrice(float price_value, long unitId)
	{
		ContentValues values = new ContentValues();
		values.put(PriceTable.COLUMN_PRICE_VALUE, price_value);
		values.put(PriceTable.COLUMN_UNIT_ID, unitId);
		values.put(PriceTable.COLUMN_DATE, DatabaseHelper.getDateTime());

		long insertId = database.insert(PriceTable.TABLE_PRICE, null, values);
		Cursor cursor = database.query(PriceTable.TABLE_PRICE, PriceTable.ALL_COLUMNS, PriceTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();

		Price newPrice = convertCursorToPrice(cursor);
		cursor.close();

		return newPrice;
	}

	public void deletePrice(Price price)
	{
		long id = price.getId();
		System.out.println("Price deleted with id: " + id);
		database.delete(PriceTable.TABLE_PRICE, PriceTable.COLUMN_ID + " = " + id, null);
	}

	public List<Price> getAllPrices()
	{
		List<Price> categories = new ArrayList<Price>();

		Cursor cursor = database.query(PriceTable.TABLE_PRICE, PriceTable.ALL_COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Price price = convertCursorToPrice(cursor);
			categories.add(price);
			cursor.moveToNext();
		}

		cursor.close();
		return categories;
	}

	private Price convertCursorToPrice(Cursor cursor)
	{
		long priceId = cursor.getLong(0);
		float priceValue = cursor.getFloat(1);
		long unitId = cursor.getLong(2);
		String created_at = cursor.getString(3);
		
		DatabaseDataSources.unitsDataSource.open();
		Unit unit = DatabaseDataSources.unitsDataSource.getUnit(unitId);
		DatabaseDataSources.unitsDataSource.close();

		Price price = new Price(priceId, priceValue, unit, created_at);
		return price;
	}

}

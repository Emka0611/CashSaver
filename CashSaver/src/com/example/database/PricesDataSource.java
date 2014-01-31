package com.example.database;

import java.util.*;

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
	private String[] allColumns = { PriceTable.COLUMN_ID, PriceTable.COLUMN_PRICE_VALUE };

	public PricesDataSource(Context context)
	{
		dbHelper = new DatabaseHelper(context);
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

		long insertId = database.insert(PriceTable.TABLE_PRICE, null, values);
		Cursor cursor = database.query(PriceTable.TABLE_PRICE, allColumns, PriceTable.COLUMN_ID + " = " + insertId, null, null, null, null);
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

		Cursor cursor = database.query(PriceTable.TABLE_PRICE, allColumns, null, null, null, null, null);

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
		
		Unit unit = dbHelper.unitsDataSource.getUnit(unitId);

		Price price = new Price(priceId, priceValue, unit);
		return price;
	}

}

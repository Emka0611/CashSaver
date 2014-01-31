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
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}
	
	public void addExamples()
	{
		Float[] prices = new Float[] { (float) 1.2 , (float) 1.4, (float) 3.5 };
		open();
		//for the first product -> 3 price entries
		createPrice(1, prices[0], 1);
		createPrice(1, prices[1], 2);
		createPrice(1, prices[2], 3);
		close();
	}

	public Price createPrice(long product_id, float price_value, long unitId)
	{
		ContentValues values = new ContentValues();
		values.put(PriceTable.COLUMN_PRODUCT_ID, product_id);
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
		List<Price> prices = new ArrayList<Price>();

		Cursor cursor = database.query(PriceTable.TABLE_PRICE, PriceTable.ALL_COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Price price = convertCursorToPrice(cursor);
			prices.add(price);
			cursor.moveToNext();
		}

		cursor.close();
		return prices;
	}
	
	public List<Price> getAllPrices(long productId)
	{
		List<Price> prices= new ArrayList<Price>();

		Cursor cursor = database.query(PriceTable.TABLE_PRICE, PriceTable.ALL_COLUMNS, PriceTable.COLUMN_PRODUCT_ID + " = " + productId, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			Price price = convertCursorToPrice(cursor);
			prices.add(price);
			cursor.moveToNext();
		}

		cursor.close();
		return prices;
	}

	private Price convertCursorToPrice(Cursor cursor)
	{
		long priceId = cursor.getLong(0);
		long productId = cursor.getLong(1);
		float priceValue = cursor.getFloat(2);
		long unitId = cursor.getLong(3);
		String created_at = cursor.getString(4);
		
		DatabaseDataSources.unitsDataSource.open();
		Unit unit = DatabaseDataSources.unitsDataSource.getUnit(unitId);
		DatabaseDataSources.unitsDataSource.close();

		Price price = new Price(priceId, productId, priceValue, unit, created_at);
		return price;
	}

}

package com.example.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.products.Price;
import com.example.products.ProductSpecific;
import com.example.tables.ProductTable;

public class ProductsDataSource
{
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { ProductTable.COLUMN_ID, ProductTable.COLUMN_NAME, ProductTable.COLUMN_CATEGORY_ID };

	public ProductsDataSource(Context context)
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

	public ProductSpecific createProductSpecific(String name)
	{
		ContentValues values = new ContentValues();
		values.put(ProductTable.COLUMN_NAME, name);
		
		long insertId = database.insert(ProductTable.TABLE_PRODUCT, null, values);
		Cursor cursor = database.query(ProductTable.TABLE_PRODUCT, allColumns, ProductTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		
		ProductSpecific newProductSpecific = cursorToProductSpecific(cursor);
		cursor.close();
		
		return newProductSpecific;
	}

	public void deleteProductSpecific(ProductSpecific product)
	{
		long id = product.getId();
		System.out.println("ProductSpecific deleted with id: " + id);
		database.delete(ProductTable.TABLE_PRODUCT,ProductTable.COLUMN_ID + " = " + id, null);
	}

	public List<ProductSpecific> getAllProducts()
	{
		List<ProductSpecific> products = new ArrayList<ProductSpecific>();

		Cursor cursor = database.query(ProductTable.TABLE_PRODUCT, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			ProductSpecific produt = cursorToProductSpecific(cursor);
			products.add(produt);
			cursor.moveToNext();
		}
		
		cursor.close();
		return products;
	}

	private ProductSpecific cursorToProductSpecific(Cursor cursor)
	{
		ProductSpecific product = new ProductSpecific(cursor.getLong(0), cursor.getString(1), "", new Price(0, "kg"), "cat", 0);
		return product;
	}

}

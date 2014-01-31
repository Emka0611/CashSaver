package com.example.database.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import com.example.database.DatabaseDataSources;
import com.example.database.DatabaseHelper;
import com.example.products.*;
import com.example.tables.*;

public class ProductsDataSource
{
	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { 
			ProductTable.COLUMN_ID, 
			ProductTable.COLUMN_NAME, 
			ProductTable.COLUMN_NAME_DETAILED, 
			ProductTable.COLUMN_CATEGORY_ID 
			};

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

	public ProductSpecific createProductSpecific(String name, String detailedName, long categoryId)
	{
		ContentValues values = new ContentValues();
		values.put(ProductTable.COLUMN_NAME, name);
		values.put(ProductTable.COLUMN_NAME_DETAILED, detailedName);
		values.put(ProductTable.COLUMN_CATEGORY_ID, categoryId);
		values.put(ProductTable.COLUMN_PRICE_HISTORY_ID, 0);

		long insertId = database.insert(ProductTable.TABLE_PRODUCT, null, values);
		Cursor cursor = database.query(ProductTable.TABLE_PRODUCT, allColumns, ProductTable.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();

		ProductSpecific newProductSpecific = convertCursorToProductSpecific(cursor);
		cursor.close();

		return newProductSpecific;
	}

	public void deleteProductSpecific(ProductSpecific product)
	{
		long id = product.getId();
		System.out.println("ProductSpecific deleted with id: " + id);
		database.delete(ProductTable.TABLE_PRODUCT, ProductTable.COLUMN_ID + " = " + id, null);
	}

	public List<ProductSpecific> getAllProducts()
	{
		List<ProductSpecific> products = new ArrayList<ProductSpecific>();

		Cursor cursor = database.query(ProductTable.TABLE_PRODUCT, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			ProductSpecific produt = convertCursorToProductSpecific(cursor);
			products.add(produt);
			cursor.moveToNext();
		}

		cursor.close();
		return products;
	}

	private ProductSpecific convertCursorToProductSpecific(Cursor cursor)
	{
		long productId = cursor.getLong(0);
		String productName = cursor.getString(1);
		String productDetailedName = cursor.getString(2);
		long categoryId = cursor.getLong(3);
		int barcode = 0;
		// long priceHistoryId = cursor.getLong(4);

		DatabaseDataSources.categoriesDataSource.open();
		Category category = DatabaseDataSources.categoriesDataSource.getCategory(categoryId);
		DatabaseDataSources.categoriesDataSource.close();

		ProductSpecific product = new ProductSpecific(productId, productName, productDetailedName, category, barcode);
		return product;

	}

}
